package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import jodd.io.findfile.FindFile
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/15.
//

/**
 * 股票列表,获取基础信息数据，包括股票代码、名称、上市日期、退市日期等
 * 每周更新一次
 */
class StockBasicDB(val dbOptions: TuDbOptions) : IDbFolder {

    fun update() {
        FileUtil.mkdirs(folder())
        val lastFile = latestFile()
        if (lastFile != null) {
            // 文件不为空, 判断文件的更新时间和当前的时间间隔天数是否小于 7 天, 小于等于7天则不需要更新, 直接退出
            val today = JDateTime()
            val lastFileDate = JDateTime(lastFile.name.take(10), "YYYY-MM-DD")
            if (today.daysBetween(lastFileDate) <= 7) {
                return
            }
        }

        // 其他情况, 没有数据, 或者数据文件已经超过 7 天了, 则需要更新最新的数据
        val dataFile = this.csvFile()
        if (dataFile.exists()) {
            return
        } else {
            dbOptions.executor.execute {
                val datas = TushareApi.stockBasic()
                TsRecord.saveToFile(dataFile, datas, append = false)
                Logger.debug("股票列表数据下载完成. ${dataFile.absolutePath}")

                // 删掉老文件
                folder().listFiles()
                        .filter { it.name != dataFile.name }
                        .forEach { it.deleteOnExit() }
            }

        }
    }

    /**
     * 目录: stock_basic
     * 文件名模式: YYYY-MM-DD.stock_basic.csv
     */
    private fun csvFile(): File {
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName()))
    }

    private fun csvFileName(): String {
        return "${JDateTime().toString("YYYY-MM-DD")}.stock_basic.csv"
    }

    private fun latestFile(): File? {
        val finder = FindFile.createWildcardFF()
                .include("*.stock_basic.csv")
                .matchOnlyFileName()
                .searchPath(folder())
        return finder.firstOrNull()
    }


    override fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "stock_basic")
        return File(folderPath)
    }
}