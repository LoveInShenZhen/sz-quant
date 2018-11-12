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
        api.api_name = "dividend"
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
        api.api_name = "fina_mainbz"
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

    //<editor-fold desc="市场参考数据">

    /**
     * 获取沪股通、深股通、港股通每日资金流向数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=47
     *
     * @param trade_date 交易日期 (二选一)
     * @param start_date 开始日期 (二选一)
     * @param end_date 结束日期
     */
    fun moneyflowHsgtAsync(trade_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<MoneyflowHsgt>> {

        val api = ApiPayload()
        api.api_name = "moneyflow_hsgt"
        api.addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = MoneyflowHsgt().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<MoneyflowHsgt>(payload)
        }
    }

    /**
     * 获取沪股通、深股通、港股通每日资金流向数据
     * 参考: https://tushare.pro/document/2?doc_id=47
     *
     * @param trade_date 交易日期 (二选一)
     * @param start_date 开始日期 (二选一)
     * @param end_date 结束日期
     */
    fun moneyflowHsgt(trade_date: String = "", start_date: String = "", end_date: String = ""): List<MoneyflowHsgt> {
        return moneyflowHsgtAsync(trade_date, start_date, end_date).get()
    }

    /**
     * 获取沪股通、深股通每日前十大成交详细数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=48
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     * @param market_type 市场类型（1：沪市 3：深市）
     */
    fun hsgtTop10Async(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = "", market_type: String = ""): CompletableFuture<List<HsgtTop10>> {

        val api = ApiPayload()
        api.api_name = "hsgt_top10"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("market_type", market_type)

        api.fields = HsgtTop10().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<HsgtTop10>(payload)
        }
    }

    /**
     * 获取沪股通、深股通每日前十大成交详细数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=48
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     * @param market_type 市场类型（1：沪市 3：深市）
     */
    fun hsgtTop10(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = "", market_type: String = ""): List<HsgtTop10> {
        return hsgtTop10Async(ts_code, trade_date, start_date, end_date, market_type).get()
    }

    /**
     * 获取港股通每日成交数据，其中包括沪市、深市详细数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=49
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     * @param market_type 市场类型 2：港股通（沪） 4：港股通（深）
     */
    fun ggtTop10Async(ts_code: String = "",
                      trade_date: String = "",
                      start_date: String = "",
                      end_date: String = "",
                      market_type: String = ""): CompletableFuture<List<GgtTop10>> {

        val api = ApiPayload()
        api.api_name = "ggt_top10"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
                .addParam("market_type", market_type)

        api.fields = GgtTop10().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<GgtTop10>(payload)
        }
    }

    /**
     * 获取港股通每日成交数据，其中包括沪市、深市详细数据
     * 参考: https://tushare.pro/document/2?doc_id=49
     *
     * @param ts_code 股票代码（二选一）
     * @param trade_date 交易日期（二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     * @param market_type 市场类型 2：港股通（沪） 4：港股通（深）
     */
    fun ggtTop10(ts_code: String = "",
                 trade_date: String = "",
                 start_date: String = "",
                 end_date: String = "",
                 market_type: String = ""): List<GgtTop10> {

        return ggtTop10Async(ts_code, trade_date, start_date, end_date, market_type).get()
    }

    /**
     * 获取融资融券每日交易汇总数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=58
     *
     * @param trade_date 交易日期
     * @param exchange_id 交易所代码
     */
    fun marginAsync(trade_date: String, exchange_id: String = ""): CompletableFuture<List<Margin>> {
        val api = ApiPayload()
        api.api_name = "margin"
        api.addParam("trade_date", trade_date).addParam("exchange_id", exchange_id)

        api.fields = Margin().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Margin>(payload)
        }
    }

    /**
     * 获取融资融券每日交易汇总数据
     * 参考: https://tushare.pro/document/2?doc_id=58
     *
     * @param trade_date 交易日期
     * @param exchange_id 交易所代码
     */
    fun margin(trade_date: String, exchange_id: String = ""): List<Margin> {
        return marginAsync(trade_date, exchange_id).get()
    }

    /**
     * 获取沪深两市每日融资融券明细(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=59
     *
     * @param trade_date
     * @param ts_code
     */
    fun marginDetailAsync(trade_date: String, ts_code: String = ""): CompletableFuture<List<MarginDetail>> {

        val api = ApiPayload()
        api.api_name = "margin_detail"
        api.addParam("trade_date", trade_date).addParam("ts_code", ts_code)

        api.fields = MarginDetail().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<MarginDetail>(payload)
        }
    }

    /**
     * 获取沪深两市每日融资融券明细
     * 参考: https://tushare.pro/document/2?doc_id=59
     *
     * @param trade_date
     * @param ts_code
     */
    fun marginDetail(trade_date: String, ts_code: String = ""): List<MarginDetail> {
        return marginDetailAsync(trade_date, ts_code).get()
    }

    /**
     * 获取上市公司前十大股东数据，包括持有数量和比例等信息
     * 参考: https://tushare.pro/document/2?doc_id=61
     *
     * @param ts_code TS代码
     * @param period 报告期
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     */
    fun top10HoldersAsync(ts_code: String, period: String = "", ann_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<Top10Holders>> {

        val api = ApiPayload()
        api.api_name = "top10_holders"
        api.addParam("ts_code", ts_code)
                .addParam("period", period)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = Top10Holders().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Top10Holders>(payload)
        }
    }

    /**
     * 获取上市公司前十大股东数据，包括持有数量和比例等信息
     * 参考: https://tushare.pro/document/2?doc_id=61
     *
     * @param ts_code TS代码
     * @param period 报告期
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     */
    fun top10Holders(ts_code: String, period: String = "", ann_date: String = "", start_date: String = "", end_date: String = ""): List<Top10Holders> {
        return top10HoldersAsync(ts_code, period, ann_date, start_date, end_date).get()
    }

    /**
     * 获取上市公司前十大流通股东数据。(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=62
     *
     * @param ts_code TS代码
     * @param period 报告期
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     */
    fun top10FloatHoldersAsync(ts_code: String, period: String = "", ann_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<Top10FloatHolders>> {
        val api = ApiPayload()
        api.api_name = "top10_floatholders"
        api.addParam("ts_code", ts_code)
                .addParam("period", period)
                .addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = Top10FloatHolders().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Top10FloatHolders>(payload)
        }
    }

    /**
     * 获取上市公司前十大流通股东数据。
     * 参考: https://tushare.pro/document/2?doc_id=62
     *
     * @param ts_code TS代码
     * @param period 报告期
     * @param ann_date 公告日期
     * @param start_date 报告期开始日期
     * @param end_date 报告期结束日期
     */
    fun top10FloatHolders(ts_code: String, period: String = "", ann_date: String = "", start_date: String = "", end_date: String = ""): List<Top10FloatHolders> {
        return top10FloatHoldersAsync(ts_code, period, ann_date, start_date, end_date).get()
    }

    /**
     * 龙虎榜每日交易明细, 单次最大10000 (异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=106
     *
     * @param trade_date 交易日期
     * @param ts_code 股票代码
     */
    fun topListAsync(trade_date: String, ts_code: String = ""): CompletableFuture<List<TopList>> {
        val api = ApiPayload()
        api.api_name = "top_list"
        api.addParam("trade_date", trade_date).addParam("ts_code", ts_code)

        api.fields = TopList().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<TopList>(payload)
        }
    }

    /**
     * 龙虎榜每日交易明细, 单次最大10000
     * 参考: https://tushare.pro/document/2?doc_id=106
     *
     * @param trade_date 交易日期
     * @param ts_code 股票代码
     */
    fun topList(trade_date: String, ts_code: String = ""): List<TopList> {
        return topListAsync(trade_date, ts_code).get()
    }

    /**
     * 龙虎榜机构成交明细(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=107
     *
     * @param trade_date 交易日期
     * @param ts_code TS代码
     */
    fun topInstAsync(trade_date: String, ts_code: String = ""): CompletableFuture<List<TopInst>> {
        val api = ApiPayload()
        api.api_name = "top_inst"
        api.addParam("trade_date", trade_date)
                .addParam("ts_code", ts_code)

        api.fields = TopInst().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<TopInst>(payload)
        }
    }

    /**
     * 龙虎榜机构成交明细
     * 参考: https://tushare.pro/document/2?doc_id=107
     *
     * @param trade_date 交易日期
     * @param ts_code TS代码
     */
    fun topInst(trade_date: String, ts_code: String = ""): List<TopInst> {
        return topInstAsync(trade_date, ts_code).get()
    }

    /**
     * 股权质押统计数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=110
     *
     * @param ts_code 股票代码
     */
    fun pledgeStatAsync(ts_code: String): CompletableFuture<List<PledgeStat>> {
        val api = ApiPayload()
        api.api_name = "pledge_stat"
        api.addParam("ts_code", ts_code)
        api.fields = PledgeStat().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<PledgeStat>(payload)
        }
    }

    /**
     * 股权质押统计数据
     * 参考: https://tushare.pro/document/2?doc_id=110
     *
     * @param ts_code 股票代码
     */
    fun pledgeStat(ts_code: String): List<PledgeStat> {
        return pledgeStatAsync(ts_code).get()
    }

    /**
     * 获取股权质押明细数据, 限量：单次最大1000 (异步方式)
     *
     * @param ts_code 股票代码
     */
    fun pledgeDetailAsync(ts_code: String): CompletableFuture<List<PledgeDetail>> {
        val api = ApiPayload()
        api.api_name = "pledge_detail"
        api.addParam("ts_code", ts_code)
        api.fields = PledgeDetail().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<PledgeDetail>(payload)
        }
    }

    /**
     * 获取股权质押明细数据, 限量：单次最大1000 (异步方式)
     *
     * @param ts_code 股票代码
     */
    fun pledgeDetail(ts_code: String): List<PledgeDetail> {
        return pledgeDetailAsync(ts_code).get()
    }

    /**
     * 获取上市公司回购股票数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=124
     *
     * @param ann_date
     * @param start_date
     * @param end_date
     */
    fun repurchaseAsync(ann_date: String, start_date: String, end_date: String): CompletableFuture<List<Repurchase>> {
        val api = ApiPayload()
        api.api_name = "repurchase"
        api.addParam("ann_date", ann_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)

        api.fields = Repurchase().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Repurchase>(payload)
        }
    }

    /**
     * 获取上市公司回购股票数据
     * 参考: https://tushare.pro/document/2?doc_id=124
     *
     * @param ann_date
     * @param start_date
     * @param end_date
     */
    fun repurchase(ann_date: String, start_date: String, end_date: String): List<Repurchase> {
        return repurchaseAsync(ann_date, start_date, end_date).get()
    }

    /**
     * 获取概念股分类，目前只有ts一个来源，未来将逐步增加来源
     *
     * @param src 来源，默认为ts
     */
    fun conceptAsync(src: String = "ts"): CompletableFuture<List<Concept>> {
        val api = ApiPayload()
        api.api_name = "concept"
        api.addParam("src", src)
        api.fields = Concept().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<Concept>(payload)
        }
    }

    /**
     * 获取概念股分类，目前只有ts一个来源，未来将逐步增加来源(异步方式)
     *
     * @param src 来源，默认为ts
     */
    fun concept(src: String = "ts"): List<Concept> {
        return conceptAsync(src).get()
    }

    /**
     * 获取概念股分类明细数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=126
     *
     * @param id 概念分类ID （id来自概念股分类接口）
     */
    fun conceptDetailAsync(id: String): CompletableFuture<List<ConceptDetail>> {
        val api = ApiPayload()
        api.api_name = "concept_detail"
        api.addParam("id", id)
        api.fields = ConceptDetail().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<ConceptDetail>(payload)
        }
    }

    /**
     * 获取概念股分类明细数据
     * 参考: https://tushare.pro/document/2?doc_id=126
     *
     * @param id 概念分类ID （id来自概念股分类接口）
     */
    fun conceptDetail(id: String): List<ConceptDetail> {
        return conceptDetailAsync(id).get()
    }

    //</editor-fold>

    //<editor-fold desc="基金">

    /**
     * 获取公募基金数据列表，包括场内和场外基金(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=19
     *
     * @param market 交易市场: E场内 O场外（默认E）
     */
    fun fundBasicAsync(market: String = "E"): CompletableFuture<List<FundBasic>> {
        val api = ApiPayload()
        api.api_name = "fund_basic"
        api.addParam("market", market)
        api.fields = FundBasic().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FundBasic>(payload)
        }
    }

    /**
     * 获取公募基金数据列表，包括场内和场外基金
     * 参考: https://tushare.pro/document/2?doc_id=19
     *
     * @param market 交易市场: E场内 O场外（默认E）
     */
    fun fundBasic(market: String = "E"): List<FundBasic> {
        return fundBasicAsync(market).get()
    }

    /**
     * 获取公募基金管理人列表
     * 参考: https://tushare.pro/document/2?doc_id=118
     */
    fun fundCompanyAsync(): CompletableFuture<List<FundCompany>> {
        val api = ApiPayload()
        api.api_name = "fund_company"
        api.fields = FundCompany().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FundCompany>(payload)
        }
    }

    /**
     * 获取公募基金管理人列表
     * 参考: https://tushare.pro/document/2?doc_id=118
     */
    fun fundCompany(): List<FundCompany> {
        return fundCompanyAsync().get()
    }

    /**
     * 获取公募基金净值数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=119
     *
     * @param ts_code TS基金代码 （二选一）
     * @param end_date 净值日期 （二选一）
     */
    fun fundNavAsync(ts_code: String = "", end_date: String = ""): CompletableFuture<List<FundNav>> {
        val api = ApiPayload()
        api.api_name = "fund_nav"
        api.addParam("ts_code", ts_code).addParam("end_date", end_date)
        api.fields = FundNav().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FundNav>(payload)
        }
    }

    /**
     * 获取公募基金净值数据
     * 参考: https://tushare.pro/document/2?doc_id=119
     *
     * @param ts_code TS基金代码 （二选一）
     * @param end_date 净值日期 （二选一）
     */
    fun fundNav(ts_code: String = "", end_date: String = ""): List<FundNav> {
        return fundNavAsync(ts_code, end_date).get()
    }

    /**
     * 获取公募基金分红数据(异步方式)
     * 参考: https://tushare.pro/document/2?doc_id=120
     * @param ann_date 公告日期（以下参数四选一）
     * @param ex_date 除息日（以下参数四选一）
     * @param pay_date 派息日（以下参数四选一）
     * @param ts_code TS代码（以下参数四选一）
     */
    fun fundDivAsync(ann_date: String = "", ex_date: String = "", pay_date: String = "", ts_code: String = ""): CompletableFuture<List<FundDiv>> {
        val api = ApiPayload()
        api.api_name = "fund_div"
        api.addParam("ann_date", ann_date)
                .addParam("ex_date", ex_date)
                .addParam("pay_date", pay_date)
                .addParam("ts_code", ts_code)

        api.fields = FundDiv().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FundDiv>(payload)
        }
    }

    /**
     * 获取公募基金分红数据
     * 参考: https://tushare.pro/document/2?doc_id=120
     * @param ann_date 公告日期（以下参数四选一）
     * @param ex_date 除息日（以下参数四选一）
     * @param pay_date 派息日（以下参数四选一）
     * @param ts_code TS代码（以下参数四选一）
     */
    fun fundDiv(ann_date: String = "", ex_date: String = "", pay_date: String = "", ts_code: String = ""): List<FundDiv> {
        return fundDivAsync(ann_date, ex_date, pay_date, ts_code).get()
    }

    /**
     * 获取公募基金持仓数据，季度更新(异步方式)
     *
     * @param ts_code TS基金代码
     */
    fun fundPortfolioAsync(ts_code: String): CompletableFuture<List<FundPortfolio>> {
        val api = ApiPayload()
        api.api_name = "fund_portfolio"
        api.addParam("ts_code", ts_code)
        api.fields = FundPortfolio().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FundPortfolio>(payload)
        }
    }

    /**
     * 获取公募基金持仓数据，季度更新
     *
     * @param ts_code TS基金代码
     */
    fun fundPortfolio(ts_code: String): List<FundPortfolio> {
        return fundPortfolioAsync(ts_code).get()
    }

    /**
     * 获取场内基金日线行情，类似股票日行情(异步方式
     * 更新：每日收盘后2小时内
     * 参考: https://tushare.pro/document/2?doc_id=127
     *
     * @param ts_code 基金代码（二选一）
     * @param trade_date 交易日期（二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     */
    fun fundDailyAsync(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = ""): CompletableFuture<List<FundDaily>> {
        val api = ApiPayload()
        api.api_name = "fund_daily"
        api.addParam("ts_code", ts_code)
                .addParam("trade_date", trade_date)
                .addParam("start_date", start_date)
                .addParam("end_date", end_date)
        api.fields = FundDaily().apiFields()

        return api.sendAsync().thenApply { resultBody ->
            Logger.debug("\n$resultBody")
            val payload = Json.fromJsonString(resultBody, ResultPayload::class.java)
            RecordBase.buildFrom<FundDaily>(payload)
        }
    }

    /**
     * 获取场内基金日线行情，类似股票日行情
     * 更新：每日收盘后2小时内
     * 参考: https://tushare.pro/document/2?doc_id=127
     *
     * @param ts_code 基金代码（二选一）
     * @param trade_date 交易日期（二选一）
     * @param start_date 开始日期
     * @param end_date 结束日期
     */
    fun fundDaily(ts_code: String = "", trade_date: String = "", start_date: String = "", end_date: String = ""): List<FundDaily>   {
        return fundDailyAsync(ts_code, trade_date, start_date, end_date).get()
    }

    //</editor-fold>


}