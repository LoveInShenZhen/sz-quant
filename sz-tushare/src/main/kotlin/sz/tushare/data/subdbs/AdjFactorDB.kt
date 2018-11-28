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
// Created by kk on 2018/11/15.
//
class AdjFactorDB(val dbOptions: TuDbOptions, val ts_code: String, val forceUpdate: Boolean = false) : IDbFolder {

    val logger = Logger.of("tushare")

    fun update() {
        FileUtil.mkdirs(folder())
        updateFor(ts_code)
    }

    private fun updateFor(ts_code: String) {
        if (this.forceUpdate.not()) {
            val lastFile = latestFile(ts_code)
            if (lastFile != null) {
                // 文件不为空, 判断文件的更新时间和当前的时间间隔天数是否小于 7 天, 小于等于7天则不需要更新, 直接退出
                val today = JDateTime()
                val lastFileDate = JDateTime(lastFile.name.take(10), "YYYY-MM-DD")
                if (today.daysBetween(lastFileDate) <= 7) {
                    return
                }
            }
        }

        // 其他情况, 没有数据, 或者数据文件已经超过 7 天了, 则需要更新最新的数据
        val dataFile = this.csvFile(ts_code)

        if (dataFile.exists()) {
            return
        } else {
            dbOptions.executor.execute {
                val datas = TushareApi.adjFactor(ts_code)
                TsRecord.saveToFile(dataFile, datas, append = false)
                logger.colorDebug("$ts_code 复权因子数据下载完成. ${dataFile.absolutePath}")

                val finder = FindFile.createWildcardFF()
                        .include("*.$ts_code.adj_factor.csv")
                        .matchOnlyFileName()
                        .searchPath(folder())

                // 清除旧的数据文件
                finder.findAll()
                        .filter { it.name != dataFile.name }
                        .forEach { it.deleteOnExit() }
            }
        }
    }

    private fun csvFile(ts_code: String): File {
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(ts_code)))
    }

    // YYYY-MM-DD.ts_code.adj_factor.csv
    private fun csvFileName(ts_code: String): String {
        return "${JDateTime().toString("YYYY-MM-DD")}.$ts_code.adj_factor.csv"
    }

    private fun latestFile(ts_code: String): File? {
        val finder = FindFile.createWildcardFF()
                .include("*.$ts_code.adj_factor.csv")
                .matchOnlyFileName()
                .searchPath(folder())
        return finder.firstOrNull()
    }

    override fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "adj_factor")
        return File(folderPath)
    }
}