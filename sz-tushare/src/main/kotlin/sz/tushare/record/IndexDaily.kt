package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/22.
//

/**
 * 指数日线行情
 * 参考: https://tushare.pro/document/2?doc_id=95
 */
class IndexDaily : RecordBase() {

    @Mark("股票代码")
    var ts_code = ""

    @Mark("交易日期, YYYYMMDD")
    var trade_date = ""

    @Mark("收盘点位")
    var close = Double.NaN

    @Mark("开盘点位")
    var open = Double.NaN

    @Mark("最高点位")
    var high = Double.NaN

    @Mark("最低点位")
    var low = Double.NaN

    @Mark("昨日收盘点")
    var pre_close = Double.NaN

    @Mark("涨跌点")
    var change = Double.NaN

    @Mark("涨跌幅")
    var pct_change = Double.NaN

    @Mark("成交量（手）")
    var vol = Double.NaN

    @Mark("成交额（千元）")
    var amount = Double.NaN
}