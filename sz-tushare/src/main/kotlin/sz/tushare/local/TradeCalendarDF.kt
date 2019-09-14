package sz.tushare.local

import com.zavtech.morpheus.frame.DataFrame
import com.zavtech.morpheus.util.text.parser.Parser
import jodd.io.FileNameUtil
import jodd.io.findfile.FindFile

//
// Created by kk on 2019/9/12.
//
class TradeCalendarDF(val folder: String) {

    val dataFrame by lazy {
        loadAsDataframe()
    }


    /**
     * 从本地数据加载交易日历的数据, 返回 DataFrame
     */
    private fun loadAsDataframe(): DataFrame<String, String> {
        val finder = FindFile.createWildcardFF()
            .include("*.trade_cal.csv")
            .matchOnlyFileName()
            .searchPath(folder)

        val dfList = finder.findAll().map { csvFile ->
            DataFrame.read().csv<String>() { options ->
                options.setFile(csvFile)
                options.setRowKeyParser(String::class.java) { row ->
                    row[0]
                }
                options.setParser("is_open", Parser.forBoolean { it == "1" })
                options.setParser("cal_date", String::class.java)
                options.setParser("pretrade_date", String::class.java)
            }
        }

        return DataFrame.concatRows(dfList).rows().sort(true)
    }

    fun is_open(date: String): Boolean {
        val result = this.dataFrame.rows().first { row ->
            row.getValue<String>("cal_date") == date
        }
        if (result.isPresent) {
            return result.get().getBoolean("is_open")
        } else {
            throw RuntimeException("不是合法有效的日期字符串: $date")
        }
    }

    fun preTradeDate(date: String): String {
        val result = this.dataFrame.rows().first { row ->
            row.getValue<String>("cal_date") == date
        }
        if (result.isPresent) {
            return result.get().getValue("pretrade_date")
        } else {
            throw RuntimeException("不是合法有效的日期字符串: $date")
        }
    }

    fun preTradeDateOf(baseDate: String, days: Int): String {
        val resultDF = this.dataFrame.rows().select { row ->
            row.getValue<String>("cal_date") < baseDate
                && row.getBoolean("is_open")
        }.rows().sort(false).head(days)

        val row = resultDF.rows().last()
        if (row.isPresent) {
            return row.get().getValue<String>("cal_date")
        } else {
            return baseDate
        }
    }

    fun preNearestTradeDayOf(baseDate: String): String {
        val resultDF = this.dataFrame.rows().select { row ->
            row.getValue<String>("cal_date") == baseDate
        }
        val row = resultDF.rows().first().get()
        return if (row.getBoolean("is_open")) {
            baseDate
        } else {
            row.getValue<String>("pretrade_date")
        }
    }

    fun nextTradeDateOf(baseDate: String, days: Int): String {
        val resultDF = this.dataFrame.rows().select { row ->
            row.getValue<String>("cal_date") > baseDate
                && row.getBoolean("is_open")
        }.head(days)

        val row = resultDF.rows().last()
        if (row.isPresent) {
            return row.get().getValue<String>("cal_date")
        } else {
            return baseDate
        }
    }

    fun tradeDaysBetween(fromDate: String, toDate: String): Int {
        val resultDF = this.dataFrame.rows().select { row ->
            row.getValue<String>("cal_date") >= fromDate
                && row.getValue<String>("cal_date") <= toDate
                && row.getBoolean("is_open")
        }

        return resultDF.rowCount()
    }

    companion object {

        fun create(tushareDataFolder: String): TradeCalendarDF {
            return TradeCalendarDF(FileNameUtil.concat(tushareDataFolder, "trade_cal"))
        }
    }
}