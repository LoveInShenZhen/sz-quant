package sz.tushare.local

import com.zavtech.morpheus.frame.DataFrame
import jodd.io.FileNameUtil
import jodd.io.findfile.FindFile

//
// Created by kk on 2019/9/13.
//
class StockBasicDF(val folder: String) {

    val dataFrame by lazy {
        loadAsDataframe()
    }

    /**
     * 从本地数据加载交易日历的数据, 返回 DataFrame
     */
    private fun loadAsDataframe(): DataFrame<String, String> {
        val finder = FindFile.createWildcardFF()
            .include("*.stock_basic.csv")
            .matchOnlyFileName()
            .searchPath(folder)

        val dfList = finder.findAll().map { csvFile ->
            DataFrame.read().csv<String>() { options ->
                options.setFile(csvFile)
                options.setRowKeyParser(String::class.java) { row ->
                    row.last()
                }
                options.setParser("symbol", String::class.java)
                options.setParser("list_date", String::class.java)
            }
        }

        return DataFrame.concatRows(dfList).rows().sort(true)
    }

    /**
     * 查询 ts_code 指定的股票的上市日期
     */
    fun listDateOf(ts_code: String): String {
        val resultDf = dataFrame.rows().select { row ->
            row.getValue<String>("ts_code") == ts_code
        }
        return resultDf.rows().first().get().getValue("list_date")
    }

    companion object {

        fun create(tushare_data_folder: String): StockBasicDF {
            val folder = FileNameUtil.concat(tushare_data_folder, "stock_basic")
            return StockBasicDF(folder)
        }
    }
}