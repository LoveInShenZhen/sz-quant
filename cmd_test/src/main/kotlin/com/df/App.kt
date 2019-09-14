/*
 * Knowing the Name of Your Main Class
 * ref: https://stackoverflow.com/questions/14733566/how-to-run-kotlin-class-from-the-command-line?answertab=votes#tab-top
 */
package com.df

import com.zavtech.morpheus.frame.DataFrame
import com.zavtech.morpheus.util.text.parser.Parser
import com.zavtech.morpheus.util.text.printer.Printer
import jodd.datetime.JDateTime
import sz.morpheus.printSchema
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorInfo
import sz.tushare.TushareExecutor
import sz.tushare.data.TuDb
import sz.tushare.data.TuDbOptions
import sz.tushare.data.subdbs.MinuteBarDB
import sz.tushare.data.subdbs.MinuteFreq
import sz.tushare.local.TradeCalendarDF
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
//    println("OK")
    download_min_bar_data()
}

fun load_trade_cal() {
//    val df = DataFrame.read().csv<String>(File("/Users/kk/work/tushare_data/trade_cal/2019.trade_cal.csv"))


    val df = DataFrame.read().csv<LocalDate> { options ->
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
        options.apply {
            setFile(File("/Users/kk/work/tushare_data/trade_cal/2019.trade_cal.csv"))
            setFormats {
                it.setParser(LocalDate::class.java, Parser.ofLocalDate("yyyyMMdd"))

            }
            setRowKeyParser(LocalDate::class.java) { row ->
                LocalDate.parse(row[0], dateFormat)
            }

            setParser("cal_date", LocalDate::class.java)
            setParser("pretrade_date", LocalDate::class.java)
            setParser("is_open", Parser.forBoolean {
                it == "1"
            })
        }
    }

    df.out().print(10) {
        it.setPrinter(LocalDate::class.java, Printer.ofLocalDate("yyyy/MM/dd"))
    }

    println()
    println("=".repeat(80))

    df.cols().forEach { col ->
        println("column '${col.key()}' type info: ${col.typeInfo()}")
    }

}

fun test_combine() {
    val df_a = DataFrame.read().csv<JDateTime> { options ->
        options.apply {
            setFile(File("/Users/kk/work/tmp/test_csv/a.csv"))
            setRowKeyParser(JDateTime::class.java) { row ->
                JDateTime(row[0], "YYYYMMDD")
            }
            setFormats {
                it.setPrinter<JDateTime>(JDateTime::class.java, Printer.forObject { v ->
                    v.toString("YYYY-MM-DD")
                })
                it.setParser<JDateTime>(JDateTime::class.java, Parser.forObject(JDateTime::class.java) { v ->
                    JDateTime(v, "YYYYMMDD")
                })
            }

        }
    }

    val df_b = DataFrame.read().csv<JDateTime> { options ->
        options.apply {
            setFile(File("/Users/kk/work/tmp/test_csv/b.csv"))
            setRowKeyParser(JDateTime::class.java) { row ->
                JDateTime(row[0], "YYYYMMDD")
            }
            setFormats {
                it.setPrinter<JDateTime>(JDateTime::class.java, Printer.forObject { v ->
                    v.toString("YYYY-MM-DD")
                })
                it.setParser<JDateTime>(JDateTime::class.java, Parser.forObject(JDateTime::class.java) { v ->
                    JDateTime(v, "YYYYMMDD")
                })
            }

        }
    }


    val df_combined = DataFrame.concatRows(df_a, df_b)

    df_a.out().print(20)

    println()
    println("=".repeat(80))

    df_b.out().print(20)

    println()
    println("=".repeat(80))

    df_combined.printSchema()

    println()
    println("=".repeat(80))

    df_combined.rows().sort(true)

    df_combined.out().print(20) { fmt ->

        fmt.apply {
            setPrinter<JDateTime>(JDateTime::class.java, Printer.forObject { v ->
                v.toString("YYYY/MM/DD")
            })
            setParser<JDateTime>(JDateTime::class.java, Parser.forObject(JDateTime::class.java) { v ->
                JDateTime(v, "YYYYMMDD")
            })
        }

    }

    println()
    println("=".repeat(80))

    println("df_combined rows count: ${df_combined.rowCount()}")

    df_combined.write().csv { options ->
        options.apply {
            setFile(File("/Users/kk/work/tmp/test_csv/combined.csv"))
            setIncludeRowHeader(false)
            setIncludeColumnHeader(true)
            setFormats {
                it.setPrinter<JDateTime>(JDateTime::class.java, Printer.forObject {
                    it.toString("YYYY-MM-DD")
                })
            }
        }
    }
}

fun test_TradeCalendar() {
    val cal = TradeCalendarDF("/Users/kk/work/tushare_data/trade_cal")
    cal.dataFrame.printSchema()
    cal.dataFrame.out().print(10)
    println()
    println("20190901: ${cal.is_open("20190901")}")
    println("20190902: ${cal.is_open("20190902")}")
    println("preTradeDate of 20190902: ${cal.preTradeDate("20190902")}")

    val baseDate = "20180410"
    val days = 141
    val preTradeDate = cal.preTradeDateOf(baseDate, days)
    val nextTradeDateOf = cal.nextTradeDateOf(baseDate, days)
    println("$days days preTradeDateOf $baseDate: $preTradeDate")
    println("$days days nextTradeDateOf $baseDate: $nextTradeDateOf")

    println("$baseDate ~ $nextTradeDateOf days count: ${cal.tradeDaysBetween(baseDate, nextTradeDateOf)}")
    println("20180410 ~ 20181105 days count: ${cal.tradeDaysBetween("20180410", "20181105")}")

//    val result = cal.dataFrame.rows().select { row ->
//        row.getValue<String>("cal_date") < "20190902"
//            && row.getBoolean("is_open")
//    }
//
//    result.rows().sort(false).rows().iterator().asSequence().take(10).forEach {
//        println("row-> cal_date: ${it.getValue<String>("cal_date")} is_open: ${it.getBoolean("is_open")}")
//    }
}

fun find_diff() {
    val df1 = DataFrame.read().csv<String>() { options ->
        options.setFile(File("/Users/kk/work/tushare_data/pro_bar_cache/002001.SZ_20180410_20181106_5min_E_None_None_None.csv"))
        options.setParser("trade_date", String::class.java)
    }
    df1.printSchema()

    val df1TradeDate = df1.cols().select("trade_date")
    df1TradeDate.printSchema()

    println("total count: ${df1TradeDate.rowCount()}")
    val tradeDateSet = df1TradeDate.rows().map { row -> row.getValue<String>("trade_date") }.toSortedSet()
    println("count: ${tradeDateSet.size}, from: ${tradeDateSet.first()} to: ${tradeDateSet.last()}")

    val cal = TradeCalendarDF("/Users/kk/work/tushare_data/trade_cal")

    val df2 = cal.dataFrame.rows().select { row ->
        val cal_date = row.getValue<String>("cal_date")
        cal_date <= tradeDateSet.last() && cal_date >= tradeDateSet.first() && row.getBoolean("is_open")
    }

    val cal_date_set = df2.rows().map { row -> row.getValue<String>("cal_date") }.toSortedSet()
    println("cal_date_set count: ${cal_date_set.size}")


}

fun download_min_bar_data() {
    val logger = Logger.of("tushare")
    val dbPath = "/Volumes/USBDATA/tushare_data"
    val options = TuDbOptions(dbPath = dbPath,
        executor = TushareExecutor.Singleton)

    val tudb = TuDb(options)
//    tudb.updateLocalData()

    val freq = MinuteFreq.Min_5

    val stockPool = tudb.defaultStockPool()
    var finishedCount = 0
    tudb.defaultStockPool().forEach {
        logger.info("开始下载 ${it.name} [${it.ts_code}] 股票的 ${freq.vName} 数据")
        val min_bar = MinuteBarDB(dbPath = dbPath, freq = freq)
        min_bar.update(ts_code = it.ts_code)
        finishedCount++
        logger.info("${it.name} [${it.ts_code}] 股票的 ${freq.vName} 数据下载完成! 👍")
        logger.colorInfo("完成度: $finishedCount/${stockPool.size}, ${String.format("%.2f", finishedCount.toFloat()/stockPool.size * 100.0)} %")
    }

    options.executor.waitFinished()

}