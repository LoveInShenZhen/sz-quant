/*
 * Knowing the Name of Your Main Class
 * ref: https://stackoverflow.com/questions/14733566/how-to-run-kotlin-class-from-the-command-line?answertab=votes#tab-top
 */
package com.sample

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorInfo
import sz.tushare.TushareExecutor
import sz.tushare.data.TuDb
import sz.tushare.data.TuDbOptions
import sz.tushare.data.subdbs.MinuteBarDB
import sz.tushare.data.subdbs.MinuteFreq

fun main(args: Array<String>) = mainBody {
    ArgParser(args).parseInto(::AppArgs).run {
        val logger = Logger.of("tushare")
        val dbPath = "/Volumes/USBDATA/tushare_data"
//        val dbPath = "/Users/kk/work/tushare_data"

        val options = TuDbOptions(dbPath = dbPath,
                executor = TushareExecutor.Singleton)

        val tudb = TuDb(options)
        tudb.updateLocalData()

        options.executor.waitFinished()

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
    }
}
