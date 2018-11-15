package sz.tushare.data

import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.Logger
import sz.tushare.data.subdbs.*

//
// Created by kk on 2018/11/15.
//

/**
 * TuShare 数据的管理器, 负责下载,存储,本地查询,按需异步下载,定时更新等等
 *
 */
class TuDb(val options: TuDbOptions) {

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

        // 下载更新成分股行情数据
        updateAdjFactor()

        // 下载更新指数数据

        return this
    }

    fun updateStockBasic() {
        val task = StockBasicDB(options)
        task.run()
    }

    fun updateTradeCal() {
        val task = TradeCalDB(options)
        task.run()
    }

    fun updateStockCompany() {
        val task = StockCompanyDB(options)
        task.run()
    }

    fun updateHSConst() {
        val task = HSConstDB(options)
        task.run()
    }

    fun updateAdjFactor() {
        val hsConstDb = HSConstDB(options)
        val totalList = hsConstDb.hsConstLst()
        var downloaded = 0
        Logger.debug("下载复权因子数据, 总共 ${totalList.size} 只股票需要下载", AnsiColor.YELLOW)

        totalList.forEach {
            val task = AdjFactorDB(options, it.ts_code)
            task.run()
            downloaded++
        }
    }

    //</editor-fold>


}