package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//

/**
 * 龙虎榜每日明细
 * 参考: https://tushare.pro/document/2?doc_id=106
 */
class TopList : RecordBase() {

    @Mark("Y	交易日期")
    var trade_date: String = ""

    @Mark("Y	TS代码")
    var ts_code: String = ""

    @Mark("Y	名称")
    var name: String = ""

    @Mark("Y	收盘价")
    var close: Double = Double.NaN

    @Mark("Y	涨跌幅")
    var pct_change: Double = Double.NaN

    @Mark("Y	换手率")
    var turnover_rate: Double = Double.NaN

    @Mark("Y	总成交额")
    var amount: Double = Double.NaN

    @Mark("Y	龙虎榜卖出额")
    var l_sell: Double = Double.NaN

    @Mark("Y	龙虎榜买入额")
    var l_buy: Double = Double.NaN

    @Mark("Y	龙虎榜成交额")
    var l_amount: Double = Double.NaN

    @Mark("Y	龙虎榜净买入额")
    var net_amount: Double = Double.NaN

    @Mark("Y	龙虎榜净买额占比")
    var net_rate: Double = Double.NaN

    @Mark("Y	龙虎榜成交额占比")
    var amount_rate: Double = Double.NaN

    @Mark("Y	当日流通市值")
    var float_values: Double = Double.NaN

    @Mark("Y	上榜理由")
    var reason: String = ""

}