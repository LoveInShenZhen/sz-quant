package sz.api.server.controller

import com.google.common.base.CaseFormat
import io.vertx.core.net.NetClientOptions
import jodd.datetime.JDateTime
import jodd.exception.ExceptionUtil
import jodd.io.FileUtil
import me.escoffier.vertx.completablefuture.VertxCompletableFuture
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import sz.api.server.controller.reply.HelloReply
import sz.futu.client.FutuClient
import sz.futu.protocol.FutuMessage
import sz.futu.protocol.QotCommon
import sz.futu.protocol.QotGetHistoryKL
import sz.scaffold.Application
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.controller.reply.ReplyBase
import sz.scaffold.tools.BizLogicException
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareApi
import sz.tushare.TushareExecutor
import sz.tushare.data.TuDb
import sz.tushare.data.TuDbOptions
import sz.tushare.record.TsRecord
import java.io.File

//
// Created by kk on 2017/12/13.
//

@Comment("测试代码")
class Sample : ApiController() {

    @Comment("测试接口")
    fun hello(): HelloReply {
        val reply = HelloReply()

        val data = TushareApi.hsgtTop10(trade_date = "20180329")

        data.forEach {
            Logger.debug(it.toJsonPretty())
        }

        Logger.debug("count : ${data.size}")

        return reply
    }

    @Comment("下载tushare数据到本地")
    fun test(): ReplyBase {

        val options = TuDbOptions(dbPath = "/Volumes/USBDATA/tushare_data",
                executor = TushareExecutor.Singleton)

        val tudb = TuDb(options)

        VertxCompletableFuture.supplyAsync {
            tudb.updateLocalData()
        }.exceptionally {
            Logger.warn(ExceptionUtil.exceptionStackTraceToString(it))
            tudb
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

    fun genCode4(): String {
        val txt = """ts_code	str	Y	股票代码
exchange	str	Y	交易所代码 ，SSE上交所 SZSE深交所
chairman	str	Y	法人代表
manager	str	Y	总经理
secretary	str	Y	董秘
reg_capital	float	Y	注册资本
setup_date	str	Y	注册日期
province	str	Y	所在省份
city	str	Y	所在城市
introduction	str	N	公司介绍
website	str	Y	公司主页
email	str	Y	电子邮件
office	str	N	办公室
employees	int	Y	员工人数
main_business	str	N	主要业务及产品
business_scope	str	N	经营范围"""

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

    fun genParserMapCode(): String {
        val txt = """1001	InitConnect.proto	初始化连接
1002	GetGlobalState.proto	获取全局状态
1003	Notify.proto	系统通知推送
1004	KeepAlive.proto	保活心跳
2001	Trd_GetAccList.proto	获取业务账户列表
2005	Trd_UnlockTrade.proto	解锁或锁定交易
2008	Trd_SubAccPush.proto	订阅业务账户的交易推送数据
2101	Trd_GetFunds.proto	获取账户资金
2102	Trd_GetPositionList.proto	获取账户持仓
2111	Trd_GetMaxTrdQtys.proto	获取最大交易数量
2201	Trd_GetOrderList.proto	获取订单列表
2202	Trd_PlaceOrder.proto	下单
2205	Trd_ModifyOrder.proto	修改订单
2208	Trd_UpdateOrder.proto	推送订单状态变动通知
2211	Trd_GetOrderFillList.proto	获取成交列表
2218	Trd_UpdateOrderFill.proto	推送成交通知
2221	Trd_GetHistoryOrderList.proto	获取历史订单列表
2222	Trd_GetHistoryOrderFillList.proto	获取历史成交列表
3001	Qot_Sub.proto	订阅或者反订阅
3002	Qot_RegQotPush.proto	注册推送
3003	Qot_GetSubInfo.proto	获取订阅信息
3004	Qot_GetBasicQot.proto	获取股票基本行情
3005	Qot_UpdateBasicQot.proto	推送股票基本行情
3006	Qot_GetKL.proto	获取K线
3007	Qot_UpdateKL.proto	推送K线
3008	Qot_GetRT.proto	获取分时
3009	Qot_UpdateRT.proto	推送分时
3010	Qot_GetTicker.proto	获取逐笔
3011	Qot_UpdateTicker.proto	推送逐笔
3012	Qot_GetOrderBook.proto	获取买卖盘
3013	Qot_UpdateOrderBook.proto	推送买卖盘
3014	Qot_GetBroker.proto	获取经纪队列
3015	Qot_UpdateBroker.proto	推送经纪队列
3016	Qot_GetOrderDetail.proto	获取委托明细
3017	Qot_UpdateOrderDetail.proto	推送委托明细
3100	Qot_GetHistoryKL.proto	获取单只股票一段历史K线
3101	Qot_GetHistoryKLPoints.proto	获取多只股票多点历史K线
3102	Qot_GetRehab.proto	获取复权信息
3103	Qot_RequestHistoryKL.proto	 获取单只股票一段历史K线
3200	Qot_GetTradeDate.proto	获取市场交易日
3202	Qot_GetStaticInfo.proto	获取股票静态信息
3203	Qot_GetSecuritySnapshot.proto	获取股票快照
3204	Qot_GetPlateSet.proto	获取板块集合下的板块
3205	Qot_GetPlateSecurity.proto	获取板块下的股票
3206	Qot_GetReference.proto	获取正股相关股票
3207	Qot_GetOwnerPlate.proto	获取股票所属板块
3208	Qot_GetHoldingChangeList.proto	获取持股变化列表
3209	Qot_GetOptionChain.proto	获取期权链"""

        val tmplateTxt = "\t{{protoId}} -> {{type}}.Response.parser()\t\t//{{comments}}\n"
        val template = JtwigTemplate.inlineTemplate(tmplateTxt)

        val sb = StringBuilder()
        sb.appendln("    fun responseParserById(protoId: Int): Parser<*> {\n" +
                "        return when (protoId) {")

        sb.append(txt.split("\n").map { line ->
            val parts = line.trim().split("""\s""".toRegex(), 3)
            val model = JtwigModel.newModel()
            model.with("protoId", parts[0])
            model.with("type", parts[1].replace(".proto", "").replace("_", ""))
            if (parts.size >= 3) {
                model.with("comments", parts[2])
            } else {
                model.with("comments", "")
            }

            template.render(model)
        }.joinToString("\n"))

        sb.append("\n        else -> throw RuntimeException(\"不支持的 ProtoID: \$protoId\")\n" +
                "        }\n" +
                "    }")


        sb.appendln().appendln("/************************************************/").appendln()

        /**
         *      InitConnect.Request::class -> 1001
        InitConnect.Response::class -> 1001
         */

        val templateTxt2 = "\t{{type}}.Request::class -> {{protoId}}\n\t{{type}}.Response::class -> {{protoId}}\n"
        val template2 = JtwigTemplate.inlineTemplate(templateTxt2)

        sb.appendln("    fun protoIdOfMessage(msgKclass:KClass<*>) : Int {\n" +
                "        return when (msgKclass) {")

        sb.append(txt.split("\n").map { line ->
            val parts = line.trim().split("""\s""".toRegex(), 3)
            val model = JtwigModel.newModel()
            model.with("protoId", parts[0])
            model.with("type", parts[1].replace(".proto", "").replace("_", ""))

            template2.render(model)
        }.joinToString("\n"))

        sb.appendln("\n        else -> throw RuntimeException(\"不支持的Message类别: \${msgKclass.qualifiedName}\")\n" +
                "        }\n" +
                "    }")

        sb.appendln("/************************************************/").appendln()

        return sb.toString()
    }


    @Comment("处理futu的proto")
    fun futuProto(): ReplyBase {
        val futuProtoDir = "/Users/kk/work/Futu/futuquant/futuquant/common/pb"

        val destDir = "/Users/kk/ssdwork/github/sz-quant/sz-futu-protocol/src/main/proto"

        FileUtil.cleanDir(destDir)

        val caseConverter = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL)

        File(futuProtoDir).walk().filter { f -> f.extension == "proto" }
                .forEach { f ->
                    Logger.debug("==> ${f.name}")
                    val newProto = f.readLines().map { line ->
                        if (line.startsWith("package ")) {
                            return@map "$line\noption java_package = \"sz.futu.protocol\";"
                        } else {
                            return@map line
                        }
                    }.joinToString("\n")

                    val newFile = File("$destDir/${f.name}")
                    newFile.writeText(newProto)
                }

        return ReplyBase()
    }

    @Comment("ByteBuf 相关测试")
    fun byteBufTest(): ReplyBase {
        val client = FutuClient(vertx = Application.vertx, ip = "192.168.3.3", port = 11111, options = NetClientOptions())
        client.whenClosed {
            Logger.debug("Connection closed!")
        }.whenReceiveMessage { msg ->
            Logger.debug("======== 收到服务端消息 ========\n$msg")
        }.connect()

        val c2s = QotGetHistoryKL.C2S.newBuilder()
                .setRehabType(QotCommon.RehabType.RehabType_Forward_VALUE)
                .setKlType(QotCommon.KLType.KLType_1Min_VALUE)
                .setSecurity(QotCommon.Security.newBuilder()
                        .setMarket(QotCommon.QotMarket.QotMarket_CNSZ_Security_VALUE)
                        .setCode("300052"))
                .setBeginTime("2018-01-01")
                .setEndTime("2018-11-09")
                .build()

        val request = QotGetHistoryKL.Request.newBuilder()
                .setC2S(c2s)
                .build()

        val msg = FutuMessage.newMessage(request)

        val future = client.sendRequest(msg)

        val responseMsg = future.get().body as QotGetHistoryKL.Response

        Logger.debug("\n$responseMsg")

        Logger.debug("total records: ${responseMsg.s2C.klListCount}")

        return ReplyBase()
    }

}