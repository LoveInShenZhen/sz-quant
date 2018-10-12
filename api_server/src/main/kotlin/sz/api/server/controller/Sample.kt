package sz.api.server.controller

import jodd.datetime.JDateTime
import sz.api.server.controller.reply.HelloReply
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.tools.csv.CSV
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.record.StockBasicRecord
import sz.tushare.record.TsRecord

//
// Created by kk on 2017/12/13.
//

@Comment("测试代码")
class Sample : ApiController() {

    @Comment("测试接口")
    fun hello(): HelloReply {
        val reply = HelloReply()

        reply.msg = "Hello, today is ${JDateTime().toString("YYYY-MM-DD")}."

//        StockBasicRecord::class.memberProperties.forEach {
//            Logger.debug("property name: ${it.name}, field_name: ${it.javaField?.name}")
//        }

//        val bean = StockBasicRecord()
//
//        StockBasicRecord::class.memberProperties.forEach {
//            if (it is KMutableProperty<*>) {
//                Logger.debug("MutableProperty name: ${it.name}")
//                it.setter.call(bean, "OK")
//            }
//        }
//

        val records = TushareApi.tradeCal(exchange_id = "SZSE")

        Logger.debug(records.first().toJsonPretty())

        TsRecord.saveToFile("/Users/kk/work/tmp/tushare_data/trade_cal/${JDateTime().toString("YYYY_MM_DD-hh_mm_ss")}.csv", records)


//        val path = "/Users/kk/work/tmp/tushare_data/stock_basic/2018_10_12-00_16_01.csv"
//
//        val datas = TsRecord.loadFromFile<StockBasicRecord>(path)
//
//        datas.forEach {
//            Logger.debug("\n${it.toJsonPretty()}")
//        }

        return reply
    }

}