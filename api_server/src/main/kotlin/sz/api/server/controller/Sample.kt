package sz.api.server.controller

import jodd.datetime.JDateTime
import jodd.datetime.ext.firstDayOfYear
import jodd.datetime.ext.lastDayOfYear
import jodd.io.FileUtil
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import sz.api.server.controller.reply.HelloReply
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.controller.reply.ReplyBase
import sz.scaffold.tools.BizLogicException
import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.TushareExecutor
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

    @Comment("临时测试")
    fun test(): ReplyBase {

        for (x in 0..200) {
            TushareExecutor.Singleton.execute {
                Logger.debug("Task $x run...", AnsiColor.CYAN)
            }
        }


        return ReplyBase()
    }

    @Comment("生成代码")
    fun genCode(): String {
        val txt = """trade_date	str	Y	交易日期
ts_code	str	Y	TS代码
exalter	str	Y	营业部名称
buy	float	Y	买入额（万）
buy_rate	float	Y	买入占总成交比例
sell	float	Y	卖出额（万）
sell_rate	float	Y	卖出占总成交比例
net_buy	float	Y	净成交额（万）"""

        val tmplateTxt = """|    @Comment("{{comment}}")
                            |    var {{fieldName}}: {{fieldType}} = {{fieldInitValue}}
                            |    """.trimMargin()
        val template = JtwigTemplate.inlineTemplate(tmplateTxt)


        return txt.split("\n").map { line ->
            val parts = line.trim().split("""\s""".toRegex(), 3)
            val model = JtwigModel.newModel()
            model.with("fieldName", parts[0])
            model.with("comment", parts[2])
            when (parts[1]) {
                "str" -> model.with("fieldType", "String").with("fieldInitValue", "\"\"")
                "int" -> model.with("fieldType", "Int").with("fieldInitValue", "-1")
                "float" -> model.with("fieldType", "Double").with("fieldInitValue", "Double.NaN")
                else -> throw BizLogicException("不支持的类型: ${parts[1]}")
            }

            template.render(model)
        }.joinToString("\n")
    }

    fun genCode4() : String {
        val txt = """ts_code	str	Y	TS代码
trade_date	str	Y	交易日期
open	float	Y	开盘价(元)
high	float	Y	最高价(元)
low	float	Y	最低价(元)
close	float	Y	收盘价(元)
pre_close	float	Y	昨收盘价(元)
change	float	Y	涨跌额(元)
pct_change	float	Y	涨跌幅(%)
vol	float	Y	成交量(手)
amount	float	Y	成交额(千元)"""

        val tmplateTxt = """|    @Comment("{{comment}}")
                            |    var {{fieldName}}: {{fieldType}} = {{fieldInitValue}}
                            |    """.trimMargin()
        val template = JtwigTemplate.inlineTemplate(tmplateTxt)


        return txt.split("\n").map { line ->
            val parts = line.trim().split("""\s""".toRegex(), 4)
            val model = JtwigModel.newModel()
            model.with("fieldName", parts[0])
            model.with("comment", parts[3])
            when (parts[1]) {
                "str" -> model.with("fieldType", "String").with("fieldInitValue", "\"\"")
                "int" -> model.with("fieldType", "Int").with("fieldInitValue", "-1")
                "float" -> model.with("fieldType", "Double").with("fieldInitValue", "Double.NaN")
                else -> throw BizLogicException("不支持的类型: ${parts[1]}")
            }

            template.render(model)
        }.joinToString("\n")
    }

}