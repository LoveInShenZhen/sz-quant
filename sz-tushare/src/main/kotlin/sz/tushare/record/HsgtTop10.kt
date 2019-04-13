package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/29.
//

/**
 * 沪深股通十大成交股
 * 参考: https://tushare.pro/document/2?doc_id=48
 */
class HsgtTop10 : RecordBase() {

    @Mark("交易日期")
    var trade_date: String = ""

    @Mark("股票代码")
    var ts_code: String = ""

    @Mark("股票名称")
    var name: String = ""

    @Mark("收盘价")
    var close: Double = Double.NaN

    @Mark("涨跌额")
    var change: Double = Double.NaN

    @Mark("资金排名")
    var rank: Int = -1

    @Mark("市场类型（1：沪市 3：深市）")
    var market_type: String = ""

    @Mark("成交金额（元）")
    var amount: Double = Double.NaN

    @Mark("净成交金额（元）")
    var net_amount: Double = Double.NaN

    @Mark("买入金额（元）")
    var buy: Double = Double.NaN

    @Mark("卖出金额（元）")
    var sell: Double = Double.NaN

}