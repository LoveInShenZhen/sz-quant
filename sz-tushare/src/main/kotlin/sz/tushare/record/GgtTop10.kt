package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/29.
//

/**
 * 港股通十大成交股
 * 参考: https://tushare.pro/document/2?doc_id=49
 */
class GgtTop10 : RecordBase() {

    @Mark("交易日期")
    var trade_date: String = ""

    @Mark("股票代码")
    var ts_code: String = ""

    @Mark("股票名称")
    var name: String = ""

    @Mark("收盘价")
    var close: Double = Double.NaN

    @Mark("涨跌幅")
    var p_change: Double = Double.NaN

    @Mark("资金排名")
    var rank: String = ""

    @Mark("市场类型 2：港股通（沪） 4：港股通（深）")
    var market_type: String = ""

    @Mark("累计成交金额（元）")
    var amount: Double = Double.NaN

    @Mark("净买入金额（元）")
    var net_amount: Double = Double.NaN

    @Mark("沪市成交金额（元）")
    var sh_amount: Double = Double.NaN

    @Mark("沪市净买入金额（元）")
    var sh_net_amount: Double = Double.NaN

    @Mark("沪市买入金额（元）")
    var sh_buy: Double = Double.NaN

    @Mark("沪市卖出金额")
    var sh_sell: Double = Double.NaN

    @Mark("深市成交金额（元）")
    var sz_amount: Double = Double.NaN

    @Mark("深市净买入金额（元）")
    var sz_net_amount: Double = Double.NaN

    @Mark("深市买入金额（元）")
    var sz_buy: Double = Double.NaN

    @Mark("深市卖出金额（元）")
    var sz_sell: Double = Double.NaN
}