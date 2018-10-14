package sz.tushare

import sz.scaffold.tools.json.Json
import sz.tushare.record.*
import java.util.concurrent.CompletableFuture

//
// Created by kk on 2018/10/11.
//
object TushareApi {


    //<editor-fold desc="基础数据">

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

    /**
     * 沪深股通成份股(异步方式)
     * 获取沪股通、深股通成分数据
     * 参考: https://tushare.pro/document/2?doc_id=104
     *
     * @param hs_type 类型: SH-沪股通 SZ-深股通
     * @param is_new 是否最新 1 是 0 否 (默认1)
     *
     * @return CompletableFuture<List<HsConst>> 调用者需要在future调用连上增加处理异常的代码
     */
    fun hsConstAsync(hs_type: String, is_new: String = "1"): CompletableFuture<List<HsConst>> {
        val api = ApiPayload()
        api.api_name = "hs_const"
        api.addParam("hs_type", hs_type)
                .addParam("is_new", is_new)

        api.fields = HsConst().apiFields()
        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<HsConst>(payload)
        }
    }

    /**
     * 沪深股通成份股
     * 获取沪股通、深股通成分数据
     * 参考: https://tushare.pro/document/2?doc_id=104
     *
     * @param hs_type 类型: SH-沪股通 SZ-深股通
     * @param is_new 是否最新 1 是 0 否 (默认1)
     */
    fun hsConst(hs_type: String, is_new: String = "1"): List<HsConst> {
        return hsConstAsync(hs_type, is_new).get()
    }

    /**
     * 股票曾用名(异步方式)
     * 历史名称变更记录
     * 限量：单次最大10000
     * 参考: https://tushare.pro/document/2?doc_id=100
     *
     * @param ts_code TS代码
     * @param start_date 公告开始日期, YYYYMMDD
     * @param end_date 公告结束日期, YYYYMMDD
     *
     * @return CompletableFuture<List<NameChange>> 调用者需要在future调用连上增加处理异常的代码
     */
    fun nameChangeAsync(ts_code: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<NameChange>> {
        val api = ApiPayload()
        api.api_name = "namechange"
        api.addParam("ts_code", ts_code)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = NameChange().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<NameChange>(payload)
        }
    }

    /**
     * 股票曾用名
     * 历史名称变更记录
     * 限量：单次最大10000
     * 参考: https://tushare.pro/document/2?doc_id=100
     *
     * @param ts_code TS代码
     * @param start_date 公告开始日期, YYYYMMDD
     * @param end_date 公告结束日期, YYYYMMDD
     *
     * @return CompletableFuture<List<NameChange>> 调用者需要在future调用连上增加处理异常的代码
     */
    fun nameChange(ts_code: String = "", start_date: String = "", end_date: String = ""): List<NameChange> {
        return nameChangeAsync(ts_code, start_date, end_date).get()
    }

    //</editor-fold>

    /**
     * 日线行情(异步方式)
     * 获取股票行情数据，或通过通用行情接口获取数据，包含了前后复权数据．
     * 参考: https://tushare.pro/document/2?doc_id=27
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun dailyAsync(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<Daily>> {
        val api = ApiPayload()
        api.api_name = "daily"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = Daily().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Daily>(payload)
        }
    }

    /**
     * 日线行情
     * 获取股票行情数据，或通过通用行情接口获取数据，包含了前后复权数据．
     * 参考: https://tushare.pro/document/2?doc_id=27
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun daily(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = ""): List<Daily> {
        return dailyAsync(ts_code, trade_date, start_date, end_date).get()
    }
}