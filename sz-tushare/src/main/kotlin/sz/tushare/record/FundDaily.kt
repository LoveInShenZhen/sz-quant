package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/2.
//

/**
 * 场内基金日线行情
 * 参考: https://tushare.pro/document/2?doc_id=127
 */
class FundDaily : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("交易日期")
    var trade_date: String = ""

    @Comment("开盘价(元)")
    var open: Double = Double.NaN

    @Comment("最高价(元)")
    var high: Double = Double.NaN

    @Comment("最低价(元)")
    var low: Double = Double.NaN

    @Comment("收盘价(元)")
    var close: Double = Double.NaN

    @Comment("昨收盘价(元)")
    var pre_close: Double = Double.NaN

    @Comment("涨跌额(元)")
    var change: Double = Double.NaN

    @Comment("涨跌幅(%)")
    var pct_change: Double = Double.NaN

    @Comment("成交量(手)")
    var vol: Double = Double.NaN

    @Comment("成交额(千元)")
    var amount: Double = Double.NaN

}