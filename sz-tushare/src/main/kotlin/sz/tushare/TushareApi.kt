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

    //<editor-fold desc="行情数据">

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

    /**
     * 复权因子(异步方式)
     * 获取股票复权因子，可提取单只股票全部历史复权因子，也可以提取单日全部股票的复权因子
     * 参考: https://tushare.pro/document/2?doc_id=28
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun adjFactorAsync(ts_code: String, trade_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<AdjFactor>> {
        val api = ApiPayload()
        api.api_name = "adj_factor"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = AdjFactor().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<AdjFactor>(payload)
        }
    }

    /**
     * 复权因子
     * 获取股票复权因子，可提取单只股票全部历史复权因子，也可以提取单日全部股票的复权因子
     * 参考: https://tushare.pro/document/2?doc_id=28
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun adjFactor(ts_code: String, trade_date: String = "", start_date: String = "", end_date: String = ""): List<AdjFactor> {
        return adjFactorAsync(ts_code, trade_date, start_date, end_date).get()
    }

    /**
     * 停复牌信息(异步方式)
     * 获取股票每日停复牌信息
     * 参考: https://tushare.pro/document/2?doc_id=31
     *
     * @param ts_code 股票代码 (三选一)
     * @param suspend_date 停牌日期 (三选一)
     * @param resume_date 复牌日期 (三选一)
     */
    fun suspendAsync(ts_code: String = "", suspend_date: String = "", resume_date: String = ""): CompletableFuture<List<Suspend>> {
        val api = ApiPayload()
        api.api_name = "suspend"
        api.addParam("ts_code", ts_code)
                .addParam("suspend_date", suspend_date)
                .addParam("resume_date", resume_date)

        api.fields = Suspend().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Suspend>(payload)
        }
    }

    /**
     * 停复牌信息
     * 获取股票每日停复牌信息
     * 参考: https://tushare.pro/document/2?doc_id=31
     *
     * @param ts_code 股票代码 (三选一)
     * @param suspend_date 停牌日期 (三选一)
     * @param resume_date 复牌日期 (三选一)
     */
    fun suspend(ts_code: String = "", suspend_date: String = "", resume_date: String = ""): List<Suspend> {
        return suspendAsync(ts_code, suspend_date, resume_date).get()
    }

    /**
     * 每日指标(异步方式)
     * 获取全部股票每日重要的基本面指标，可用于选股分析、报表展示等。
     * 参考: https://tushare.pro/document/2?doc_id=32
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun dailyBasicAsync(ts_code: String, trade_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<DailyBasic>> {
        val api = ApiPayload()
        api.api_name = "daily_basic"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = DailyBasic().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<DailyBasic>(payload)
        }
    }

    /**
     * 每日指标
     * 获取全部股票每日重要的基本面指标，可用于选股分析、报表展示等。
     * 参考: https://tushare.pro/document/2?doc_id=32
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun dailyBasic(ts_code: String, trade_date: String = "", start_date: String = "", end_date: String = ""): List<DailyBasic> {
        return dailyBasicAsync(ts_code, trade_date, start_date, end_date).get()
    }

    /**
     * 通用行情接口
     * 更新时间：股票和指数通常在15点～17点之间，数字货币实时更新，具体请参考各接口文档明细。
     * 描述：目前整合了股票（未复权、前复权、后复权）、指数、数字货币的行情数据，未来还将整合包括期货期权、基金、外汇在内的所有交易行情数据，同时提供分钟数据。
     * 参考: https://tushare.pro/document/2?doc_id=109
     *
     * @param ts_code 证券代码
     * @param pro_api pro版api对象
     * @param start_date 开始日期 (格式：YYYYMMDD)
     * @param end_date 结束日期 (格式：YYYYMMDD)
     */
    fun stockBarAsync(ts_code: String, pro_api: String = "", start_date: String = "", end_date: String = "", asset: String, adj: String, freq: String, ma: List<Int>) {
        val api = ApiPayload()
        api.api_name = "pro_bar"
        api.addParam("ts_code", ts_code)
                .addParam("pro_api", pro_api)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("asset", asset)
                .addParam("adj", adj)
                .addParam("freq", freq)
                .addParam("ma", ma)

    }

    //</editor-fold>

    //<editor-fold desc="指数数据">

    /**
     * 获取指数基础信息(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=94
     *
     * @param market 交易所或服务商
     * @param publisher 发布商
     * @param category 指数类别
     */
    fun indexBasicAsync(market: String, publisher: String = "", category: String = ""): CompletableFuture<List<IndexBasic>> {
        val api = ApiPayload()
        api.api_name = "index_basic"
        api.addParam("market", market)
                .addParam("publisher", publisher)
                .addParam("category", category)

        api.fields = IndexBasic().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<IndexBasic>(payload)
        }
    }

    /**
     * 获取指数基础信息
     * 参考: https://tushare.pro/document/2?doc_id=94
     *
     * @param market 交易所或服务商
     * @param publisher 发布商
     * @param category 指数类别
     */
    fun indexBasic(market: String, publisher: String = "", category: String = ""): List<IndexBasic> {
        return indexBasicAsync(market, publisher, category).get()
    }

    /**
     * 指数日线行情(异步方式)
     * 获取指数每日行情，还可以通过bar接口获取。由于服务器压力，目前规则是单次调取最多取2800行记录，可以设置start和end日期补全。指数行情也可以通过通用行情接口获取数据．
     *
     * @param ts_code 指数代码
     * @param trade_date 交易日期, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun indexDailyAsync(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<IndexDaily>> {
        val api = ApiPayload()
        api.api_name = "index_daily"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = IndexDaily().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<IndexDaily>(payload)
        }
    }

    /**
     * 指数日线行情
     * 获取指数每日行情，还可以通过bar接口获取。由于服务器压力，目前规则是单次调取最多取2800行记录，可以设置start和end日期补全。指数行情也可以通过通用行情接口获取数据．
     *
     * @param ts_code 指数代码
     * @param trade_date 交易日期, YYYYMMDD
     * @param start_date 开始日期, YYYYMMDD
     * @param end_date 结束日期, YYYYMMDD
     */
    fun indexDaily(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = ""): List<IndexDaily> {
        return indexDailyAsync(ts_code, trade_date, start_date, end_date).get()
    }

    /**
     * 获取各类指数成分和权重，月度数据 (异步方式)
     * 指数公司网站公开数据
     *
     * 参考: https://tushare.pro/document/2?doc_id=96
     *
     * @param index_code 指数代码 (二选一)
     * @param trade_date 交易日期 （二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     */
    fun indexWeightAsync(index_code: String, trade_date: String, start_date: String = "", end_date: String = ""): CompletableFuture<List<IndexWeight>> {
        val api = ApiPayload()
        api.api_name = "index_weight"
        api.addParam("index_code", index_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = IndexWeight().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<IndexWeight>(payload)
        }
    }


    /**
     * 获取各类指数成分和权重，月度数据
     * 指数公司网站公开数据
     *
     * 参考: https://tushare.pro/document/2?doc_id=96
     *
     * @param index_code 指数代码 (二选一)
     * @param trade_date 交易日期 （二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     */
    fun indexWeight(index_code: String, trade_date: String, start_date: String = "", end_date: String = ""): List<IndexWeight> {
        return indexWeightAsync(index_code, trade_date, start_date, end_date).get()
    }

    //</editor-fold>
}