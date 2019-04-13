package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/2.
//

/**
 * 场内基金日线行情
 * 参考: https://tushare.pro/document/2?doc_id=127
 */
class FundDaily : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("交易日期")
    var trade_date: String = ""

    @Mark("开盘价(元)")
    var open: Double = Double.NaN

    @Mark("最高价(元)")
    var high: Double = Double.NaN

    @Mark("最低价(元)")
    var low: Double = Double.NaN

    @Mark("收盘价(元)")
    var close: Double = Double.NaN

    @Mark("昨收盘价(元)")
    var pre_close: Double = Double.NaN

    @Mark("涨跌额(元)")
    var change: Double = Double.NaN

    @Mark("涨跌幅(%)")
    var pct_change: Double = Double.NaN

    @Mark("成交量(手)")
    var vol: Double = Double.NaN

    @Mark("成交额(千元)")
    var amount: Double = Double.NaN

}