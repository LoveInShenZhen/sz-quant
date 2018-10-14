package sz.tushare.record

import sz.scaffold.annotations.Comment
import java.math.BigDecimal

//
// Created by kk on 2018/10/14.
//

/**
 * 日线行情
 * 获取股票行情数据，或通过通用行情接口获取数据，包含了前后复权数据．
 */
class Daily : RecordBase() {

    @Comment("股票代码")
    var ts_code = ""

    @Comment("交易日期, YYYYMMDD")
    var trade_date = ""

    @Comment("开盘价")
    var open : BigDecimal? = null

    @Comment("最高价")
    var high : BigDecimal? = null

    @Comment("最低价")
    var low : BigDecimal? = null

    @Comment("收盘价")
    var close : BigDecimal? = null

    @Comment("昨收价")
    var pre_close : BigDecimal? = null

    @Comment("涨跌额")
    var change : BigDecimal? = null

    @Comment("涨跌幅")
    var pct_change : BigDecimal? = null

    @Comment("成交量 (手)")
    var vol : BigDecimal? = null

    @Comment("成交额 (千元)")
    var amount : BigDecimal? = null
}