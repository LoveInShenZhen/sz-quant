package sz.api.server.controller

import jodd.datetime.JDateTime
import jodd.datetime.ext.firstDayOfYear
import jodd.datetime.ext.lastDayOfYear
import jodd.io.FileUtil
import sz.api.server.controller.reply.HelloReply
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.tushare.TushareApi
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

        val ts_code = "000001.SZ"
        val date = JDateTime()

        val records = TushareApi.daily(ts_code = ts_code,
                start_date = date.firstDayOfYear().toString("YYYYMMDD"),
                end_date = date.lastDayOfYear().toString("YYYYMMDD"))


        val dataDir = "/Users/kk/work/tmp/tushare_data/daily/$ts_code/${date.toString("YYYY")}"
        FileUtil.mkdirs(dataDir)

        TsRecord.saveToFile("$dataDir/${JDateTime().toString("YYYY_MM_DD-hh_mm_ss")}.csv", records)

//        val prop = Daily::class.memberProperties.first { it.name == "open" }
//        Logger.debug("prop.returnType: ${prop.returnType}")
//        Logger.debug("prop type is match: ${ClassUtil.isTypeOf(BigDecimal::class.java, prop.returnType.jvmErasure.java)}")

        return reply
    }

}