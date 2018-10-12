package sz.tushare

import sz.scaffold.tools.json.Json
import sz.tushare.record.RecordBase
import sz.tushare.record.StockBasicRecord
import sz.tushare.record.TradeCalRecord
import java.util.concurrent.CompletableFuture

//
// Created by kk on 2018/10/11.
//
object TushareApi {

    /**
     * 股票列表
     * 获取基础信息数据，包括股票代码、名称、上市日期、退市日期等
     * 参考: https://tushare.pro/document/2?doc_id=25
     *
     * @param is_hs 是否沪深港通标的, N-否 H-沪股通 S-深股通
     * @param list_status 上市状态: L-上市 D-退市 P-暂停上市
     * @param exchange_id 交易所 SSE上交所 SZSE深交所 HKEX港交所
     */
    fun stockBasic(is_hs: String = "", list_status: String = "", exchange_id: String = ""): List<StockBasicRecord> {
//        val api = ApiPayload()
//        api.api_name = "stock_basic"
//        api.addParam("is_hs", is_hs)
//                .addParam("list_status", list_status)
//                .addParam("exchange_id", exchange_id)
//
//        api.fields = StockBasicRecord().apiFields()
//        val resultBody = api.send()
//
//        val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
//        return RecordBase.buildFrom(payload)
        return stockBasicAsync(is_hs, list_status, exchange_id).get()
    }

    /**
     * 股票列表(异步方式)
     * 获取基础信息数据，包括股票代码、名称、上市日期、退市日期等
     * 参考: https://tushare.pro/document/2?doc_id=25
     *
     * @param is_hs 是否沪深港通标的, N-否 H-沪股通 S-深股通
     * @param list_status 上市状态: L-上市 D-退市 P-暂停上市
     * @param exchange_id 交易所 SSE上交所 SZSE深交所 HKEX港交所
     *
     * @return CompletableFuture<List<StockBasicRecord>> 调用者需要在future调用连上增加处理异常的代码
     */
    fun stockBasicAsync(is_hs: String = "", list_status: String = "", exchange_id: String = ""): CompletableFuture<List<StockBasicRecord>> {
        val api = ApiPayload()
        api.api_name = "stock_basic"
        api.addParam("is_hs", is_hs)
                .addParam("list_status", list_status)
                .addParam("exchange_id", exchange_id)

        api.fields = StockBasicRecord().apiFields()
        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<StockBasicRecord>(payload)
        }
    }

    /**
     * 交易日历
     * 获取各大交易所交易日历数据,默认提取的是上交所
     * 参考: https://tushare.pro/document/2?doc_id=26
     *
     * @param exchange_id 交易所: SSE-上交所 SZSE-深交所
     * @param start_date 开始日期,YYYYMMDD
     * @param end_date 结束日期,YYYYMMDD
     * @param is_open 是否交易 0-休市 1-交易
     */
    fun tradeCal(exchange_id: String = "", start_date: String = "", end_date: String = "", is_open: String = ""): List<TradeCalRecord> {
//        val api = ApiPayload()
//        api.api_name = "trade_cal"
//        api.addParam("exchange_id", exchange_id)
//                .addParam("start_date", start_date)
//                .addParam("end_date", end_date)
//                .addParam("is_open", is_open)
//
//        api.fields = TradeCalRecord().apiFields()
//        val resultBody = api.send()
//        val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
//        return RecordBase.buildFrom(payload)
        return tradeCalAsync(exchange_id, start_date, end_date, is_open).get()
    }

    /**
     * 交易日历(异步方式)
     * 获取各大交易所交易日历数据,默认提取的是上交所
     * 参考: https://tushare.pro/document/2?doc_id=26
     *
     * @param exchange_id 交易所: SSE-上交所 SZSE-深交所
     * @param start_date 开始日期,YYYYMMDD
     * @param end_date 结束日期,YYYYMMDD
     * @param is_open 是否交易 0-休市 1-交易
     *
     * @return CompletableFuture<List<TradeCalRecord>> 调用者需要在future调用连上增加处理异常的代码
     */
    fun tradeCalAsync(exchange_id: String = "", start_date: String = "", end_date: String = "", is_open: String = ""): CompletableFuture<List<TradeCalRecord>> {
        val api = ApiPayload()
        api.api_name = "trade_cal"
        api.addParam("exchange_id", exchange_id)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("is_open", is_open)

        api.fields = TradeCalRecord().apiFields()
        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<TradeCalRecord>(payload)
        }
    }
}