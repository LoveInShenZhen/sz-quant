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
class StockCompanyDB(val dbOptions: TuDbOptions)  {

    fun update() {
        updateFor("SSE")
        updateFor("SZSE")
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
                val datas = TushareApi.stockCompany(exchange)
                TsRecord.saveToFile(dataFile, datas, append = false)
                Logger.debug("$exchange 上市公司基本信息数据下载完成. ${dataFile.absolutePath}")

                val finder = FindFile.createWildcardFF()
                        .include("*.$exchange.stock_company.csv")
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
     * 目录: stock_company
     * 文件名模式: YYYY-MM-DD.SSE.stock_company.csv and YYYY-MM-DD.SZSE.stock_company.csv
     */
    private fun csvFile(exchange: String): File {
        FileUtil.mkdirs(folder())
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(exchange)))
    }

    private fun csvFileName(exchange: String): String {
        return "${JDateTime().toString("YYYY-MM-DD")}.$exchange.stock_company.csv"
    }

    private fun latestFile(exchange: String): File? {
        val finder = FindFile.createWildcardFF()
                .include("*.$exchange.stock_company.csv")
                .matchOnlyFileName()
                .searchPath(folder())
        return finder.firstOrNull()
    }

    private fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "stock_company")
        return File(folderPath)
    }
}