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

//        TushareApi.stockBarAsync(ts_code = "000001.SH", start_date = "20181022", end_date = "20181022", adj = "None", freq = "5MIN")

//        val records = TushareApi.income(ts_code = "600000.SH")
//
//        val dataDir = "/Users/kk/work/tmp/tushare_data/income}"
//        FileUtil.mkdirs(dataDir)
//        TsRecord.saveToFile("$dataDir/${JDateTime().toString("YYYY_MM_DD-hh_mm_ss")}.csv", records)

        val line = """intan_assets	float	无形 资产"""
        line.split("""\s""".toRegex(), 3).forEach {
            Logger.debug(it, AnsiColor.GREEN)
        }


        return ReplyBase()
    }

    @Comment("生成代码")
    fun genCode(): String {
        val txt = """ts_code	str	TS代码
end_date	str	报告期
bz_item	str	主营业务来源
bz_sales	float	主营业务收入(元)
bz_profit	float	主营业务利润(元)
bz_cost	float	主营业务成本(元)
curr_type	str	货币代码
update_flag	str	是否更新"""

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
end_date	str	Y	分红年度
ann_date	str	Y	预案公告日
div_proc	str	Y	实施进度
stk_div	float	Y	每股送转
stk_bo_rate	float	Y	每股送股比例
stk_co_rate	float	Y	每股转增比例
cash_div	float	Y	每股分红（税后）
cash_div_tax	float	Y	每股分红（税前）
record_date	str	Y	股权登记日
ex_date	str	Y	除权除息日
pay_date	str	Y	派息日
div_listdate	str	Y	红股上市日
imp_ann_date	str	Y	实施公告日
base_date	str	N	基准日
base_share	float	N	基准股本（万）"""

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