package sz.tushare.data.subdbs

import jodd.http.HttpRequest
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import sz.scaffold.tools.logger.Logger
import sz.tushare.local.StockBasicDF
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

//
// Created by kk on 2019/9/13.
//
class MinuteBarDB(val dbPath: String, val freq: MinuteFreq) : IDbFolder {

    // 分钟线数据从 2016年开始下载, 以后根据需要再做调整
    // 分钟级别的数据, 按照1个月为时间段, 进行分段下载
    val from_date: String = "20160101"

    val logger = Logger.of("tushare")

    init {
        FileUtil.mkdirs(folder())
    }

    override fun folder(): File {
        val folderPath = FileNameUtil.concat(dbPath, "pro_bar/${freq.vName}")
        return File(folderPath)
    }

    /**
     * 根据 ts_code 下载指定的股票, 从 20160101 到 当前自然月底的分钟级数据
     */
    fun update(ts_code: String) {
        val dateFmt = DateTimeFormatter.ofPattern("yyyyMMdd")
        val fromDate = LocalDate.parse(from_date, dateFmt)

        val stockBasicDf = StockBasicDF.create(dbPath)

        val listDate = LocalDate.parse(stockBasicDf.listDateOf(ts_code), dateFmt)

        var from = fromDate

        if (listDate.isAfter(fromDate)) {
            from = LocalDate.of(listDate.year, listDate.month, 1)
        }

        var to = from.plusMonths(freq.monthInterval)
        val today = LocalDate.now()

        val destFolder: String = FileNameUtil.concat(folder().absolutePath, ts_code)
        FileUtil.mkdirs(destFolder)

        val interval: Long = 15
        var csvFilePath = ""
        var start_date = ""
        var end_date = ""

        while (from.isBefore(today)) {
            start_date = from.format(dateFmt)
            end_date = to.format(dateFmt)

            // include start_date, exclude end_date
            csvFilePath = FileNameUtil.concat(destFolder, "${start_date}_${end_date}.csv")
            val destFile = File(csvFilePath)

            if (destFile.exists().not()) {
                // 数据文件不存在
                if (downloadAndSave(ts_code, start_date, end_date, destFile)) {
                    logger.info("下载 $ts_code ${freq.vName} 数据: ${destFile.name} 成功")
                    Thread.sleep(interval * 1000)
                } else {
                    logger.info("下载 $ts_code ${freq.vName} 数据: ${destFile.name} 失败, sleep $interval 秒后重试")
                    Thread.sleep(interval * 1000)
                    continue
                }
            }

            from = from.plusMonths(freq.monthInterval)
            to = to.plusMonths(freq.monthInterval)
        }

        // 对下载的最后一个数据文件, 再进行更新时间的判断, 如果当日已经更新过, 则无需下载, 否则重新下载
        var finished = false
        val lastFile = File(csvFilePath)
        while (finished.not()) {
            if (lastFile.lastModifiedTime().toLocalDate() == today) {
                logger.info("${lastFile.name} , 今天已经更新过, 无须下载")
                finished = true
            } else {
                if (downloadAndSave(ts_code, start_date, end_date, lastFile)) {
                    finished = true
                    Thread.sleep(interval * 1000)
                } else {
                    logger.info("下载: ${lastFile.name} 失败, sleep $interval 秒后重试")
                    Thread.sleep(interval * 1000)
                }
            }
        }
    }

    private fun downloadAndSave(ts_code: String, start_date: String, end_date: String, destFile: File): Boolean {
        try {
            val req = HttpRequest.get("http://localhost:5000/tushare/stock_csv")
                .query("ts_code", ts_code)
                .query("start_date", start_date)
                .query("end_date", end_date)
                .query("freq", freq.vName)

            val response = req.send()

            if (response.statusCode() == 200) {
                destFile.writeText(response.bodyText())
            } else {
                logger.warn("status code: ${response.statusCode()}")
                throw RuntimeException("下载数据出错, ${req.url()}")
            }
            return true
        } catch (ex: Exception) {
            return false
        }

    }

    fun LocalDate.sameYearMonth(other: LocalDate): Boolean {
        return this.year == other.year && this.month == other.month
    }

    fun File.lastModifiedTime(): LocalDateTime {
        return LocalDateTime.ofEpochSecond(this.lastModified() / 1000, 0, OffsetDateTime.now().offset)
    }
}

enum class MinuteFreq(val vName: String, val monthInterval: Long) {
    Min_1("1min", 1),
    Min_5("5min", 6),
    Min_15("15min", 12),
    Min_30("30min", 12),
    Min_60("60min", 12);

    override fun toString(): String {
        return this.vName
    }
}