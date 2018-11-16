package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import jodd.io.findfile.FindFile
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.HsConst
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/15.
//
class HSConstDB(val dbOptions: TuDbOptions)  {

    fun update() {
        updateFor("SH")
        updateFor("SZ")
    }

    private fun updateFor(exchange: String) {
        val lastFile = latestFile(exchange)
        if (lastFile != null) {
            // 文件不为空, 判断文件的更新时间和当前的时间间隔天数是否小于 7 天, 小于等于7天则不需要更新, 直接退出
            val today = JDateTime()
            val lastFileDate = JDateTime(lastFile.name.take(10), "YYYY-MM-DD")
            if (today.daysBetween(lastFileDate) <= 7) {
                return
            }
        }

        // 其他情况, 没有数据, 或者数据文件已经超过 7 天了, 则需要更新最新的数据
        val dataFile = this.csvFile(exchange)

        if (dataFile.exists()) {
            return
        } else {
            dbOptions.executor.execute {
                val datas = TushareApi.hsConst(exchange)
                TsRecord.saveToFile(dataFile, datas, append = false)
                Logger.debug("$exchange 成份股数据下载完成. ${dataFile.absolutePath}")

                val finder = FindFile.createWildcardFF()
                        .include("*.$exchange.hs_const.csv")
                        .matchOnlyFileName()
                        .searchPath(folder())

                // 清除旧的数据文件
                finder.findAll()
                        .filter { it.name != dataFile.name }
                        .forEach { it.deleteOnExit() }
            }
        }
    }

    /**
     * 目录: hs_const
     * 文件名模式: YYYY-MM-DD.SH.hs_const.csv and YYYY-MM-DD.SZ.hs_const.csv
     */
    private fun csvFile(exchange: String): File {
        FileUtil.mkdirs(folder())
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(exchange)))
    }

    private fun csvFileName(exchange: String): String {
        return "${JDateTime().toString("YYYY-MM-DD")}.$exchange.hs_const.csv"
    }

    private fun latestFile(exchange: String): File? {
        val finder = FindFile.createWildcardFF()
                .include("*.$exchange.hs_const.csv")
                .matchOnlyFileName()
                .searchPath(folder())
        return finder.firstOrNull()
    }

    private fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "hs_const")
        return File(folderPath)
    }

    fun hsConstLst() : List<HsConst> {
        this.update()
        val shCsvFile = latestFile("SH")!!
        val szCsvFile = latestFile("SZ")!!

        val shList = TsRecord.loadFromFile<HsConst>(shCsvFile)
        val szList = TsRecord.loadFromFile<HsConst>(szCsvFile)
        val total = ArrayList<HsConst>(shList.size + szList.size)
        total.addAll(shList)
        total.addAll(szList)
        return total
    }
}