package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import jodd.io.findfile.FindFile
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorDebug
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/16.
//

/**
 * 市场说明(market):
 * * MSCI	 MSCI指数
 * * CSI	 中证指数
 * * SSE	 上交所指数
 * * SZSE	 深交所指数
 * * CICC	 中金所指数
 * * SW	     申万指数
 * * CNI	 国证指数
 * * OTH	 其他指数
 */
class IndexBasicDB(val dbOptions: TuDbOptions) {

    val logger = Logger.of("tushare")

    fun update() {
        val marketList = listOf("MSCI", "CSI", "SSE", "SZSE", "CICC", "SW", "CNI", "OTH")
        marketList.forEach {
            updateFor(it)
        }
    }

    private fun updateFor(market: String) {
        if (needUpdate(market)) {
            dbOptions.executor.execute {
                val dataFile = this.csvFile(market)
                val datas = TushareApi.indexBasic(market)
                TsRecord.saveToFile(dataFile, datas)
                logger.colorDebug("$market 指数基本信息数据下载完成. ${dataFile.absolutePath}")

                val finder = FindFile.createWildcardFF()
                        .include("*.$market.index_basic.csv")
                        .matchOnlyFileName()
                        .searchPath(folder())

                // 清除旧的数据文件
                finder.findAll()
                        .filter { it.name != dataFile.name }
                        .forEach { it.deleteOnExit() }
            }
        }
    }

    private fun needUpdate(market: String): Boolean {
        val lastFile = latestFile(market) ?: return true

        val today = JDateTime()
        val lastFileDate = JDateTime(lastFile.name.take(10), "YYYY-MM-DD")
        // 更新时间距离今天大于 30 天, 需要更新
        return today.daysBetween(lastFileDate) > 30
    }

    private fun csvFile(market: String): File {
        FileUtil.mkdirs(folder())
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(market)))
    }

    private fun csvFileName(market: String): String {
        return "${JDateTime().toString("YYYY-MM-DD")}.$market.index_basic.csv"
    }

    private fun latestFile(market: String): File? {
        val finder = FindFile.createWildcardFF()
                .include("*.$market.index_basic.csv")
                .matchOnlyFileName()
                .searchPath(folder())
        return finder.firstOrNull()
    }

    private fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "index_basic")
        return File(folderPath)
    }
}