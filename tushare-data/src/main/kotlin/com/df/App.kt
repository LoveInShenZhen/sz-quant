/*
 * Knowing the Name of Your Main Class
 * ref: https://stackoverflow.com/questions/14733566/how-to-run-kotlin-class-from-the-command-line?answertab=votes#tab-top
 */
package com.df

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import sz.tushare.TushareExecutor
import sz.tushare.data.TuDb
import sz.tushare.data.TuDbOptions

fun main(args: Array<String>) = mainBody {
    ArgParser(args).parseInto(::AppArgs).run {
//        val dbPath = "/Volumes/USBDATA/tushare_data"
        val dbPath = "/Users/kk/work/tushare_data"

        val options = TuDbOptions(dbPath = dbPath,
                executor = TushareExecutor.Singleton)

        val tudb = TuDb(options)
        tudb.updateLocalData()

        options.executor.waitFinished()
    }
}
