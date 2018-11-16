package sz.tushare.data

import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorDebug
import sz.scaffold.tools.logger.colorWarn
import sz.tushare.data.subdbs.*

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

        // 下载更新沪深成分股行情数据
//        updateAdjFactor()
//        updateDailyBasic()

        // 下载更新指数数据
        updateIndexBasic()
        updateIndexWeight()

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
        val hsConstDb = HSConstDB(options)
        val totalList = hsConstDb.hsConstLst()
        logger.colorDebug("下载复权因子数据, 总共 ${totalList.size} 只股票需要下载", AnsiColor.YELLOW)

        totalList.forEach {
            val subdb = AdjFactorDB(options, it.ts_code)
            subdb.update()
        }
    }

    fun updateDailyBasic() {
        val hsConstDb = HSConstDB(options)
        val totalList = hsConstDb.hsConstLst()
        logger.colorDebug("下载每日指标数据, 总共 ${totalList.size} 只股票需要下载", AnsiColor.YELLOW)

        totalList.forEach {
            val subdb = DailyBasicDB(options, it.ts_code)
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



}