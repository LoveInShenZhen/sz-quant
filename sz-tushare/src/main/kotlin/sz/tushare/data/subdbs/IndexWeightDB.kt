package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import jodd.io.findfile.FindFile
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorDebug
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.IndexWeight
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/16.
//
class IndexWeightDB(val dbOptions: TuDbOptions) : IDbFolder {

    private val logger = Logger.of("tushare")

    // 暂时只默认下载以下的指数的权重信息数据
    private val defaultIndexMap = mapOf("399300.SZ" to "沪深300")

    fun update() {
        FileUtil.mkdirs(folder())

        defaultIndexMap.forEach { ts_code, _ ->
            updateFor(ts_code)
        }

    }

    fun updateFor(ts_code: String) {
        if (needUpdate(ts_code)) {
            dbOptions.executor.execute {
                val dataFile = this.newCsvFile(ts_code)
                val datas = TushareApi.indexWeight(index_code = ts_code)
                TsRecord.saveToFile(dataFile, datas)
                logger.colorDebug("${defaultIndexMap.getOrElse(ts_code) { ts_code }} 指数成分和权重数据下载完成. ${dataFile.absolutePath}")

                val finder = FindFile.createWildcardFF()
                        .include("*.$ts_code.index_weight.csv")
                        .matchOnlyFileName()
                        .searchPath(folder())

                // 清除旧的数据文件
                finder.findAll()
                        .filter { it.name != dataFile.name }
                        .forEach { it.deleteOnExit() }
            }
        }
    }

    // 沪深300 指数股
    fun hs300Records(): List<IndexWeight> {
        return TsRecord.loadFromFile(latestFile("399300.SZ")!!)
    }

    private fun needUpdate(ts_code: String): Boolean {
        val lastFile = latestFile(ts_code) ?: return true

        val today = JDateTime()
        val lastFileDate = JDateTime(lastFile.name.take(10), "YYYY-MM-DD")
        return today.month != lastFileDate.month
    }

    private fun newCsvFile(ts_code: String): File {
        return File(FileNameUtil.concat(folder().absolutePath, newCsvFileName(ts_code)))
    }

    private fun newCsvFileName(ts_code: String): String {
        return "${JDateTime().toString("YYYY-MM-DD")}.$ts_code.index_weight.csv"
    }

    private fun latestFile(ts_code: String): File? {
        val finder = FindFile.createWildcardFF()
                .include("*.$ts_code.index_weight.csv")
                .matchOnlyFileName()
                .searchPath(folder())
        return finder.firstOrNull()
    }

    override fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "index_weight")
        return File(folderPath)
    }
}