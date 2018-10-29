package sz.tushare

import sz.scaffold.tools.json.Json
import sz.scaffold.tools.json.toJsonNode
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.Logger
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
     * @param adj 复权类型(只针对股票)：None未复权 qfq前复权 hfq后复权 , 默认None
     * @param freq 数据频度 ：1MIN表示1分钟（1/5/15/30/60分钟） D日线 ，默认D
     * @param ma 均线，支持任意合理int数值
     */
    fun stockBarAsync(ts_code: String, pro_api: String = "", start_date: String = "", end_date: String = "", adj: String, freq: String, ma: List<Int> = listOf()) {
        val api = ApiPayload()
        api.api_name = "pro_bar"
        api.addParam("ts_code", ts_code)
                .addParam("pro_api", pro_api)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("asset", "E")     // 资产类别：E股票 I沪深指数 C数字货币 F期货 O期权，默认E
                .addParam("adj", adj)
                .addParam("freq", freq)
                .addParam("ma", ma)

        val result = api.send().toJsonNode().toJsonPretty()

        Logger.debug(result, AnsiColor.GREEN)
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

    //<editor-fold desc="财务数据">

    /**
     * 获取上市公司财务利润表数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=33
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期，比如20171231表示年报)
     * @param report_type 报告类型： 参考下表说明
     * @param comp_type 公司类型：1一般工商业 2银行 3保险 4证券
     *
     */
    fun incomeAsync(ts_code: String,
                    ann_date: String = "",
                    start_date: String = "",
                    end_date: String = "",
                    period: String = "",
                    report_type: String = "",
                    comp_type: String = ""): CompletableFuture<List<Income>> {

        val api = ApiPayload()
        api.api_name = "income"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)
                .addParam("report_type", report_type)
                .addParam("comp_type", comp_type)

        api.fields = Income().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Income>(payload)
        }
    }

    /**
     * 获取上市公司财务利润表数据
     * 参考: https://tushare.pro/document/2?doc_id=33
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期，比如20171231表示年报)
     * @param report_type 报告类型： 参考下表说明
     * @param comp_type 公司类型：1一般工商业 2银行 3保险 4证券
     *
     */
    fun income(ts_code: String,
               ann_date: String = "",
               start_date: String = "",
               end_date: String = "",
               period: String = "",
               report_type: String = "",
               comp_type: String = ""): List<Income> {
        return incomeAsync(ts_code, ann_date, start_date, end_date, period, report_type, comp_type).get()
    }

    /**
     * 获取上市公司资产负债表(异步方法)
     * 参考: https://tushare.pro/document/2?doc_id=36
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期，比如20171231表示年报)
     * @param report_type 报告类型：见下方详细说明
     * @param comp_type 公司类型：1一般工商业 2银行 3保险 4证券
     */
    fun balanceSheetAsync(ts_code: String,
                          ann_date: String,
                          start_date: String,
                          end_date: String,
                          period: String,
                          report_type: String,
                          comp_type: String): CompletableFuture<List<BalanceSheet>> {

        val api = ApiPayload()
        api.api_name = "balancesheet"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)
                .addParam("report_type", report_type)
                .addParam("comp_type", comp_type)

        api.fields = BalanceSheet().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<BalanceSheet>(payload)
        }
    }

    /**
     * 获取上市公司资产负债表
     * 参考: https://tushare.pro/document/2?doc_id=36
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期，比如20171231表示年报)
     * @param report_type 报告类型：见下方详细说明
     * @param comp_type 公司类型：1一般工商业 2银行 3保险 4证券
     */
    fun balanceSheet(ts_code: String,
                     ann_date: String,
                     start_date: String,
                     end_date: String,
                     period: String,
                     report_type: String,
                     comp_type: String): List<BalanceSheet> {
        return balanceSheetAsync(ts_code, ann_date, start_date, end_date, period, report_type, comp_type).get()
    }

    /**
     * 获取上市公司现金流量表(异步方法)
     * 参考: https://tushare.pro/document/2?doc_id=44
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期，比如20171231表示年报)
     * @param report_type 报告类型：见下方详细说明
     * @param comp_type 公司类型：1一般工商业 2银行 3保险 4证券
     */
    fun cashflowAsync(ts_code: String,
                      ann_date: String,
                      start_date: String,
                      end_date: String,
                      period: String,
                      report_type: String,
                      comp_type: String): CompletableFuture<List<Cashflow>> {

        val api = ApiPayload()
        api.api_name = "cashflow"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)
                .addParam("report_type", report_type)
                .addParam("comp_type", comp_type)

        api.fields = BalanceSheet().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Cashflow>(payload)
        }
    }

    /**
     * 获取上市公司现金流量表
     * 参考: https://tushare.pro/document/2?doc_id=44
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期，比如20171231表示年报)
     * @param report_type 报告类型：见下方详细说明
     * @param comp_type 公司类型：1一般工商业 2银行 3保险 4证券
     */
    fun cashflow(ts_code: String,
                 ann_date: String,
                 start_date: String,
                 end_date: String,
                 period: String,
                 report_type: String,
                 comp_type: String): List<Cashflow> {
        return cashflowAsync(ts_code, ann_date, start_date, end_date, period, report_type, comp_type).get()
    }

    /**
     * 获取业绩预告数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=45
     *
     * @param ts_code 股票代码(二选一)
     * @param ann_date 公告日期
     * @param start_date 公告开始日期
     * @param end_date 公告结束日期
     * @param period 报告期 (二选一) (每个季度最后一天的日期，比如20171231表示年报)
     * @param type 预告类型(预增/预减/扭亏/首亏/续亏/续盈/略增/略减)
     */
    fun forecastAsync(ts_code: String = "",
                      ann_date: String = "",
                      start_date: String = "",
                      end_date: String = "",
                      period: String = "",
                      type: String = ""): CompletableFuture<List<Forecast>> {

        val api = ApiPayload()
        api.api_name = "forecast"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)
                .addParam("type", type)

        api.fields = Forecast().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Forecast>(payload)
        }
    }

    /**
     * 获取业绩预告数据
     * 参考: https://tushare.pro/document/2?doc_id=45
     *
     * @param ts_code 股票代码(二选一)
     * @param ann_date 公告日期
     * @param start_date 公告开始日期
     * @param end_date 公告结束日期
     * @param period 报告期 (二选一) (每个季度最后一天的日期，比如20171231表示年报)
     * @param type 预告类型(预增/预减/扭亏/首亏/续亏/续盈/略增/略减)
     */
    fun forecast(ts_code: String = "",
                 ann_date: String = "",
                 start_date: String = "",
                 end_date: String = "",
                 period: String = "",
                 type: String = ""): List<Forecast> {

        return forecastAsync(ts_code, ann_date, start_date, end_date, period, type).get()
    }

    /**
     * 获取上市公司业绩快报(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=46
     *
     * @param ts_code 股票代码(二选一)
     * @param ann_date 公告日期 (二选一)
     * @param start_date 公告开始日期
     * @param end_date 公告结束日期
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    fun expressAsync(ts_code: String = "",
                     ann_date: String = "",
                     start_date: String = "",
                     end_date: String = "",
                     period: String = ""): CompletableFuture<List<Express>> {

        val api = ApiPayload()
        api.api_name = "express"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)

        api.fields = Express().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Express>(payload)
        }
    }

    /**
     * 获取上市公司业绩快报
     * 参考: https://tushare.pro/document/2?doc_id=46
     *
     * @param ts_code 股票代码(二选一)
     * @param ann_date 公告日期 (二选一)
     * @param start_date 公告开始日期
     * @param end_date 公告结束日期
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    fun express(ts_code: String = "",
                ann_date: String = "",
                start_date: String = "",
                end_date: String = "",
                period: String = ""): List<Express> {

        return expressAsync(ts_code, ann_date, start_date, end_date, period).get()
    }

    /**
     * 分红送股数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=103
     *
     * @param ts_code TS代码
     * @param ann_date 公告日
     * @param record_date 股权登记日期
     * @param ex_date 除权除息日
     */
    fun dividendAsync(ts_code: String = "",
                      ann_date: String = "",
                      record_date: String = "",
                      ex_date: String = ""): CompletableFuture<List<Dividend>> {

        val api = ApiPayload()
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("record_date", record_date)
                .addParam("ex_date", ex_date)

        api.fields = Dividend().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Dividend>(payload)
        }
    }

    /**
     * 分红送股数据
     * 参考: https://tushare.pro/document/2?doc_id=103
     *
     * @param ts_code TS代码
     * @param ann_date 公告日
     * @param record_date 股权登记日期
     * @param ex_date 除权除息日
     */
    fun dividend(ts_code: String = "",
                 ann_date: String = "",
                 record_date: String = "",
                 ex_date: String = ""): List<Dividend> {

        return dividendAsync(ts_code, ann_date, record_date, ex_date).get()
    }

    /**
     * 获取上市公司财务指标数据，为避免服务器压力，先阶段每次请求最多返回30条记录，可通过设置日期多次请求获取更多数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=79
     *
     * @param ts_code TS股票代码,e.g. 600001.SH/000001.SZ
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    fun finaIndicatorAsync(ts_code: String,
                           ann_date: String = "",
                           start_date: String = "",
                           end_date: String = "",
                           period: String = ""): CompletableFuture<List<FinaIndicator>> {

        val api = ApiPayload()
        api.api_name = "fina_indicator"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)

        api.fields = FinaIndicator().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FinaIndicator>(payload)
        }
    }

    /**
     * 获取上市公司财务指标数据，为避免服务器压力，先阶段每次请求最多返回30条记录，可通过设置日期多次请求获取更多数据
     * 参考: https://tushare.pro/document/2?doc_id=79
     *
     * @param ts_code TS股票代码,e.g. 600001.SH/000001.SZ
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    fun finaIndicator(ts_code: String,
                      ann_date: String = "",
                      start_date: String = "",
                      end_date: String = "",
                      period: String = ""): List<FinaIndicator> {

        return finaIndicatorAsync(ts_code, ann_date, start_date, end_date, period).get()
    }

    /**
     * 获取上市公司定期财务审计意见数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=80
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 公告开始日期
     * @param end_date 公告结束日期
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    fun finaAuditAsync(ts_code: String,
                       ann_date: String = "",
                       start_date: String = "",
                       end_date: String = "",
                       period: String = ""): CompletableFuture<List<FinaAudit>> {

        val api = ApiPayload()
        api.api_name = "fina_audit"
        api.addParam("ts_code", ts_code)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("period", period)

        api.fields = FinaAudit().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FinaAudit>(payload)
        }
    }

    /**
     * 获取上市公司定期财务审计意见数据
     * 参考: https://tushare.pro/document/2?doc_id=80
     *
     * @param ts_code 股票代码
     * @param ann_date 公告日期
     * @param start_date 公告开始日期
     * @param end_date 公告结束日期
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     */
    fun finaAudit(ts_code: String,
                  ann_date: String = "",
                  start_date: String = "",
                  end_date: String = "",
                  period: String = ""): List<FinaAudit> {

        return finaAuditAsync(ts_code, ann_date, start_date, end_date, period).get()
    }

    /**
     * 获得上市公司主营业务构成，分地区和产品两种方式(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=81
     *
     * @param ts_code 股票代码
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     * @param type 类型：P按产品 D按地区（请输入大写字母P或者D）
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     */
    fun finaMainbzAsync(ts_code: String,
                        period: String = "",
                        type: String = "",
                        start_date: String = "",
                        end_date: String = ""): CompletableFuture<List<FinaMainbz>> {

        val api = ApiPayload()
        api.addParam("ts_code", ts_code)
                .addParam("period", period)
                .addParam("type", type)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = FinaMainbz().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FinaMainbz>(payload)
        }
    }

    /**
     * 获得上市公司主营业务构成，分地区和产品两种方式
     * 参考: https://tushare.pro/document/2?doc_id=81
     *
     * @param ts_code 股票代码
     * @param period 报告期(每个季度最后一天的日期,比如20171231表示年报)
     * @param type 类型：P按产品 D按地区（请输入大写字母P或者D）
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     */
    fun finaMainbz(ts_code: String,
                   period: String = "",
                   type: String = "",
                   start_date: String = "",
                   end_date: String = ""): List<FinaMainbz> {

        return finaMainbzAsync(ts_code, period, type, start_date, end_date).get()
    }
    //</editor-fold>


}