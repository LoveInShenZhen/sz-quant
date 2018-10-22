package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/22.
//

/**
 * 指数日线行情
 * 参考: https://tushare.pro/document/2?doc_id=95
 */
class IndexDaily : RecordBase() {

    @Comment("股票代码")
    var ts_code = ""

    @Comment("交易日期, YYYYMMDD")
    var trade_date = ""

    @Comment("收盘点位")
    var close = Double.NaN

    @Comment("开盘点位")
    var open = Double.NaN

    @Comment("最高点位")
    var high = Double.NaN

    @Comment("最低点位")
    var low = Double.NaN

    @Comment("昨日收盘点")
    var pre_close = Double.NaN

    @Comment("涨跌点")
    var change = Double.NaN

    @Comment("涨跌幅")
    var pct_change = Double.NaN

    @Comment("成交量（手）")
    var vol = Double.NaN

    @Comment("成交额（千元）")
    var amount = Double.NaN
}