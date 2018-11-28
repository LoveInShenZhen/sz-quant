package sz.tushare.data.subdbs

import jodd.datetime.JDateTime
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.data.TuDbOptions
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2018/11/26.
//
class TopListDB(val dbOptions: TuDbOptions) : IDbFolder {

    private val logger = Logger.of("tushare")

    fun update() {
        FileUtil.mkdirs(folder())
        // 按日获取, 获取从 2018-01-01 至今的所有交易日的龙虎榜每日交易明细
        val tradeDateList = TradeCalDB(dbOptions)
                .load()
                .filter { it.is_open == 1 && it.cal_date >= "20180101" && it.cal_date < JDateTime().toString("YYYYMMDD") }

        tradeDateList.forEach { cal ->
            val dataFile = csvFile(cal.cal_date)
            if (dataFile.exists().not()) {
                dbOptions.executor.execute {
                    val datas = TushareApi.topList(trade_date = cal.cal_date)
                    TsRecord.saveToFile(dataFile, datas)
                    logger.debug("龙虎榜每日交易明细: ${cal.cal_date} 日数据下载完成")
                }
            }
        }

    }

    fun csvFile(date: String): File {
        return File(FileNameUtil.concat(folder().absolutePath, csvFileName(date)))
    }

    override fun folder(): File {
        val folderPath = FileNameUtil.concat(dbOptions.dbPath, "top_list")
        return File(folderPath)
    }

    private fun csvFileName(tradeDate: String): String {
        return "$tradeDate.top_list.csv"
    }
}