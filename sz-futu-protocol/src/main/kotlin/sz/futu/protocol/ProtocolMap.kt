package sz.futu.protocol

import com.google.protobuf.Parser
import kotlin.reflect.KClass

//
// Created by kk on 2018/11/10.
//
object ProtocolMap {

    fun responseParserById(protoId: Int): Parser<*> {
        return when (protoId) {
            1001 -> InitConnect.Response.parser()		//初始化连接

            1002 -> GetGlobalState.Response.parser()		//获取全局状态

            1003 -> Notify.Response.parser()		//系统通知推送

            1004 -> KeepAlive.Response.parser()		//保活心跳

            2001 -> TrdGetAccList.Response.parser()		//获取业务账户列表

            2005 -> TrdUnlockTrade.Response.parser()		//解锁或锁定交易

            2008 -> TrdSubAccPush.Response.parser()		//订阅业务账户的交易推送数据

            2101 -> TrdGetFunds.Response.parser()		//获取账户资金

            2102 -> TrdGetPositionList.Response.parser()		//获取账户持仓

            2111 -> TrdGetMaxTrdQtys.Response.parser()		//获取最大交易数量

            2201 -> TrdGetOrderList.Response.parser()		//获取订单列表

            2202 -> TrdPlaceOrder.Response.parser()		//下单

            2205 -> TrdModifyOrder.Response.parser()		//修改订单

            2208 -> TrdUpdateOrder.Response.parser()		//推送订单状态变动通知

            2211 -> TrdGetOrderFillList.Response.parser()		//获取成交列表

            2218 -> TrdUpdateOrderFill.Response.parser()		//推送成交通知

            2221 -> TrdGetHistoryOrderList.Response.parser()		//获取历史订单列表

            2222 -> TrdGetHistoryOrderFillList.Response.parser()		//获取历史成交列表

            3001 -> QotSub.Response.parser()		//订阅或者反订阅

            3002 -> QotRegQotPush.Response.parser()		//注册推送

            3003 -> QotGetSubInfo.Response.parser()		//获取订阅信息

            3004 -> QotGetBasicQot.Response.parser()		//获取股票基本行情

            3005 -> QotUpdateBasicQot.Response.parser()		//推送股票基本行情

            3006 -> QotGetKL.Response.parser()		//获取K线

            3007 -> QotUpdateKL.Response.parser()		//推送K线

            3008 -> QotGetRT.Response.parser()		//获取分时

            3009 -> QotUpdateRT.Response.parser()		//推送分时

            3010 -> QotGetTicker.Response.parser()		//获取逐笔

            3011 -> QotUpdateTicker.Response.parser()		//推送逐笔

            3012 -> QotGetOrderBook.Response.parser()		//获取买卖盘

            3013 -> QotUpdateOrderBook.Response.parser()		//推送买卖盘

            3014 -> QotGetBroker.Response.parser()		//获取经纪队列

            3015 -> QotUpdateBroker.Response.parser()		//推送经纪队列

//            3016 -> QotGetOrderDetail.Response.parser()		//获取委托明细
//
//            3017 -> QotUpdateOrderDetail.Response.parser()		//推送委托明细

            3100 -> QotGetHistoryKL.Response.parser()		//获取单只股票一段历史K线

            3101 -> QotGetHistoryKLPoints.Response.parser()		//获取多只股票多点历史K线

            3102 -> QotGetRehab.Response.parser()		//获取复权信息

            3103 -> QotRequestHistoryKL.Response.parser()		// 获取单只股票一段历史K线

            3200 -> QotGetTradeDate.Response.parser()		//获取市场交易日

            3202 -> QotGetStaticInfo.Response.parser()		//获取股票静态信息

            3203 -> QotGetSecuritySnapshot.Response.parser()		//获取股票快照

            3204 -> QotGetPlateSet.Response.parser()		//获取板块集合下的板块

            3205 -> QotGetPlateSecurity.Response.parser()		//获取板块下的股票

            3206 -> QotGetReference.Response.parser()		//获取正股相关股票

            3207 -> QotGetOwnerPlate.Response.parser()		//获取股票所属板块

            3208 -> QotGetHoldingChangeList.Response.parser()		//获取持股变化列表

            3209 -> QotGetOptionChain.Response.parser()		//获取期权链

            else -> throw RuntimeException("不支持的 ProtoID: $protoId")
        }
    }

    fun protoIdOfMessage(msgKclass:KClass<*>) : Int {
        return when (msgKclass) {

            InitConnect.Request::class -> 1001
            InitConnect.Response::class -> 1001

            GetGlobalState.Request::class -> 1002
            GetGlobalState.Response::class -> 1002

            Notify.Response::class -> 1003

            KeepAlive.Request::class -> 1004
            KeepAlive.Response::class -> 1004

            TrdGetAccList.Request::class -> 2001
            TrdGetAccList.Response::class -> 2001

            TrdUnlockTrade.Request::class -> 2005
            TrdUnlockTrade.Response::class -> 2005

            TrdSubAccPush.Request::class -> 2008
            TrdSubAccPush.Response::class -> 2008

            TrdGetFunds.Request::class -> 2101
            TrdGetFunds.Response::class -> 2101

            TrdGetPositionList.Request::class -> 2102
            TrdGetPositionList.Response::class -> 2102

            TrdGetMaxTrdQtys.Request::class -> 2111
            TrdGetMaxTrdQtys.Response::class -> 2111

            TrdGetOrderList.Request::class -> 2201
            TrdGetOrderList.Response::class -> 2201

            TrdPlaceOrder.Request::class -> 2202
            TrdPlaceOrder.Response::class -> 2202

            TrdModifyOrder.Request::class -> 2205
            TrdModifyOrder.Response::class -> 2205

            TrdUpdateOrder.Response::class -> 2208

            TrdGetOrderFillList.Request::class -> 2211
            TrdGetOrderFillList.Response::class -> 2211

            TrdUpdateOrderFill.Response::class -> 2218

            TrdGetHistoryOrderList.Request::class -> 2221
            TrdGetHistoryOrderList.Response::class -> 2221

            TrdGetHistoryOrderFillList.Request::class -> 2222
            TrdGetHistoryOrderFillList.Response::class -> 2222

            QotSub.Request::class -> 3001
            QotSub.Response::class -> 3001

            QotRegQotPush.Request::class -> 3002
            QotRegQotPush.Response::class -> 3002

            QotGetSubInfo.Request::class -> 3003
            QotGetSubInfo.Response::class -> 3003

            QotGetBasicQot.Request::class -> 3004
            QotGetBasicQot.Response::class -> 3004

            QotUpdateBasicQot.Response::class -> 3005

            QotGetKL.Request::class -> 3006
            QotGetKL.Response::class -> 3006

            QotUpdateKL.Response::class -> 3007

            QotGetRT.Request::class -> 3008
            QotGetRT.Response::class -> 3008

            QotUpdateRT.Response::class -> 3009

            QotGetTicker.Request::class -> 3010
            QotGetTicker.Response::class -> 3010

            QotUpdateTicker.Response::class -> 3011

            QotGetOrderBook.Request::class -> 3012
            QotGetOrderBook.Response::class -> 3012

            QotUpdateOrderBook.Response::class -> 3013

            QotGetBroker.Request::class -> 3014
            QotGetBroker.Response::class -> 3014

            QotUpdateBroker.Response::class -> 3015

            QotGetHistoryKL.Request::class -> 3100
            QotGetHistoryKL.Response::class -> 3100

            QotGetHistoryKLPoints.Request::class -> 3101
            QotGetHistoryKLPoints.Response::class -> 3101

            QotGetRehab.Request::class -> 3102
            QotGetRehab.Response::class -> 3102

            QotRequestHistoryKL.Request::class -> 3103
            QotRequestHistoryKL.Response::class -> 3103

            QotGetTradeDate.Request::class -> 3200
            QotGetTradeDate.Response::class -> 3200

            QotGetStaticInfo.Request::class -> 3202
            QotGetStaticInfo.Response::class -> 3202

            QotGetSecuritySnapshot.Request::class -> 3203
            QotGetSecuritySnapshot.Response::class -> 3203

            QotGetPlateSet.Request::class -> 3204
            QotGetPlateSet.Response::class -> 3204

            QotGetPlateSecurity.Request::class -> 3205
            QotGetPlateSecurity.Response::class -> 3205

            QotGetReference.Request::class -> 3206
            QotGetReference.Response::class -> 3206

            QotGetOwnerPlate.Request::class -> 3207
            QotGetOwnerPlate.Response::class -> 3207

            QotGetHoldingChangeList.Request::class -> 3208
            QotGetHoldingChangeList.Response::class -> 3208

            QotGetOptionChain.Request::class -> 3209
            QotGetOptionChain.Response::class -> 3209

            else -> throw RuntimeException("不支持的Message类别: ${msgKclass.qualifiedName}")
        }
    }

}