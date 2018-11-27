package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import sz.scaffold.tools.logger.Logger
import sz.scaffold.tools.logger.colorDebug
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/15.
//
class DailyBasicDB(val dbOptions: TuDbOptions, val ts_code: String) {

    val logger = Logger.of("tushare")

    fun update() {
        updateFor(ts_code)
    }

    private fun updateFor(ts_code: String) {
        val today = JDateTime()

        // 下载从 2000 年开始的数据, 更早的暂时就不下载了
        for (year in 2000..today.year) {
            val dataFile = csvFile(ts_code, year)
            if (needUpdate(dataFile, today)) {
                dbOptions.executor.execute {
                    val datas = TushareApi.daily(ts_code = ts_code,
                            start_date = "${year}0101",
                            end_date = "${year}1231")
                    TsRecord.saveToFile(dataFile, datas)
                    logger.colorDebug("股票 $ts_code $year 年的每日指标下载完毕. ${dataFile.absolutePath}")
                }
            }
        }
    }

    private fun needUpdate(csvFile: File, today: JDateTime): Boolean {
        if (csvFile.exists().not()) {
            return true
        }

        // 300732.SZ.2018.daily_basic.csv
        val yearPart = csvFile.name.split(".")[2].toInt()
        if (yearPart < today.year) {
            Logger.debug("$yearPart 年度的数据文件: ${csvFile.name} 已经下载完毕, 无须更新")
            return false
        } else {
            val csvFileMtime = JDateTime(csvFile.lastModified())
            return csvFileMtime.daysBetween(today) > 1
        }
    }

    private fun csvFile(ts_code: String, year: Int): File {
        FileUtil.mkdirs(folder())
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(ts_code, year)))
    }

    // eg: 300732.SZ.2018.daily_basic.csv
    private fun csvFileName(ts_code: String, year: Int): String {
        return "$ts_code.$year.daily_basic.csv"
    }

    private fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "daily_basic")
        return File(folderPath)
    }
}