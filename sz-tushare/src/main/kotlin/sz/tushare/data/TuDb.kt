package sz.tushare.data

import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorDebug
import sz.tushare.data.subdbs.*
import sz.tushare.record.StockBasic

//
// Created by kk on 2018/11/15.
//

/**
 * TuShare 数据的管理器, 负责下载,存储,本地查询,按需异步下载,定时更新等等
 *
 */
class TuDb(val options: TuDbOptions) {

    val logger = Logger.of("tushare")

    //<editor-fold desc="下载更新本地数据">

    /**
     * 更新下载本地的数据
     */
    fun updateLocalData(): TuDb {
        // 下载更新基础数据

        updateStockBasic()
        updateTradeCal()
        updateStockCompany()
        updateHSConst()
        updateIndexWeight()

        // 下载更新沪深成分股行情数据
        updateAdjFactor()
        updateDailyBasic()

        // 下载更新指数数据
        updateIndexBasic()

        // 下载榜单数据
        updateHsgtTop10()
        updateTopList()

        Logger.debug("更新完毕")

        return this
    }

    fun updateStockBasic() {
        val subdb = StockBasicDB(options)
        subdb.update()
    }

    fun updateTradeCal() {
        val subdb = TradeCalDB(options)
        subdb.update()
    }

    fun updateStockCompany() {
        val subdb = StockCompanyDB(options)
        subdb.update()
    }

    fun updateHSConst() {
        val subdb = HSConstDB(options)
        subdb.update()
    }

    fun updateAdjFactor() {
        val stockPool = defaultStockPool()
        logger.colorDebug("下载复权因子数据, 总计: ${stockPool.size} 只股票", AnsiColor.YELLOW)

        stockPool.forEach {
            val subdb = AdjFactorDB(options, it.ts_code)
            logger.colorDebug("下载复权因子数据 : ${it.ts_code} ${it.name}", AnsiColor.YELLOW)
            subdb.update()
        }
    }

    fun updateDailyBasic() {
        val stockPool = defaultStockPool()
        logger.colorDebug("下载每日指标数据, 总计: ${stockPool.size} 只股票", AnsiColor.YELLOW)

        stockPool.forEach {
            val subdb = DailyBasicDB(options, it.ts_code)
            logger.colorDebug("下载每日指标数据 : ${it.ts_code}", AnsiColor.YELLOW)
            subdb.update()
        }
    }

    //</editor-fold>

    //<editor-fold desc="指数相关数据">

    fun updateIndexBasic() {
        val subdb = IndexBasicDB(options)
        subdb.update()
    }

    fun updateIndexWeight() {
        val subdb = IndexWeightDB(options)
        subdb.update()
    }

    //</editor-fold>

    fun updateHsgtTop10() {
        val subdb = HsgtTop10DB(options)
        subdb.update()
    }

    fun updateTopList() {
        val subdb = TopListDB(options)
        subdb.update()
    }

    /**
     * 获得我们默认关注的股票列表, 作为后续策略的股票池
     * 目前只关注 沪深300 指数涵盖的股票
     * 几大指数股票包含关系,参考: https://xueqiu.com/1852961730/107999935
     */
    fun defaultStockPool(): List<StockBasic> {
        val hs300Stocks = IndexWeightDB(options).hs300Records().map { it.con_code }.toSet()
        val stockBasic = StockBasicDB(options).records()
        return stockBasic.filter { stock ->
            hs300Stocks.contains(stock.ts_code)
        }
    }


}