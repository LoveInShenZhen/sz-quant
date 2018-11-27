package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.TradeCalRecord
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/15.
//
class TradeCalDB(val dbOptions: TuDbOptions) {

    fun update() {

        // 从 2000年开始查询, 之前的年份, 太过久远, 就不用了
        val nowYear = JDateTime().year
        for (year in 2000..nowYear) {
            val dataFile = csvFile(year)
            if (dataFile.exists().not()) {
                dbOptions.executor.execute {
                    val result = TushareApi.tradeCal(start_date = "${year}0101",
                            end_date = "${year}1231")
                    TsRecord.saveToFile(dataFile, result)
                    Logger.debug("$year 年交易日历下载完毕. ${dataFile.absolutePath}")
                }
            }
        }
    }

    /**
     * 目录: trade_cal
     * 文件名模式: YYYY.trade_cal.csv
     */
    private fun csvFile(year: Int): File {
        FileUtil.mkdirs(folder())
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(year)))
    }

    private fun csvFileName(year: Int): String {
        return "$year.trade_cal.csv"
    }

    private fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "trade_cal")
        return File(folderPath)
    }

    fun load(): List<TradeCalRecord> {
        val nowYear = JDateTime().year
        return (2000..nowYear).map { year ->
            val csvFile = csvFile(year)
            TsRecord.loadFromFile<TradeCalRecord>(csvFile)
        }.flatMap { list ->
            list.asIterable()
        }.toList()
    }
}