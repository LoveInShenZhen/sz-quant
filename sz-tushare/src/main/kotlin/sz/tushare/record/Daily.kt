package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/14.
//

/**
 * 日线行情
 * 获取股票行情数据，或通过通用行情接口获取数据，包含了前后复权数据．
 */
class Daily : RecordBase() {

    @Mark("股票代码")
    var ts_code = ""

    @Mark("交易日期, YYYYMMDD")
    var trade_date = ""

    @Mark("开盘价")
    var open = Double.NaN

    @Mark("最高价")
    var high = Double.NaN

    @Mark("最低价")
    var low = Double.NaN

    @Mark("收盘价")
    var close = Double.NaN

    @Mark("昨收价")
    var pre_close = Double.NaN

    @Mark("涨跌额")
    var change = Double.NaN

    @Mark("涨跌幅")
    var pct_chg = Double.NaN

    @Mark("成交量 (手)")
    var vol = Double.NaN

    @Mark("成交额 (千元)")
    var amount = Double.NaN
}