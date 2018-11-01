package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//

/**
 * 龙虎榜机构明细
 * 参考: https://tushare.pro/document/2?doc_id=107
 */
class TopInst : RecordBase() {

    @Comment("Y	交易日期")
    var trade_date: String = ""

    @Comment("Y	TS代码")
    var ts_code: String = ""

    @Comment("Y	营业部名称")
    var exalter: String = ""

    @Comment("Y	买入额（万）")
    var buy: Double = Double.NaN

    @Comment("Y	买入占总成交比例")
    var buy_rate: Double = Double.NaN

    @Comment("Y	卖出额（万）")
    var sell: Double = Double.NaN

    @Comment("Y	卖出占总成交比例")
    var sell_rate: Double = Double.NaN

    @Comment("Y	净成交额（万）")
    var net_buy: Double = Double.NaN

}