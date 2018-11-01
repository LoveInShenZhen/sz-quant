package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/29.
//

/**
 * 沪深港通资金流向
 * 参考: https://tushare.pro/document/2?doc_id=47
 */
class MoneyflowHsgt : RecordBase() {

    @Comment("交易日期")
    var trade_date: String = ""

    @Comment("港股通（上海）")
    var ggt_ss: Double = Double.NaN

    @Comment("港股通（深圳）")
    var ggt_sz: Double = Double.NaN

    @Comment("沪股通（百万元）")
    var hgt: Double = Double.NaN

    @Comment("深股通（百万元）")
    var sgt: Double = Double.NaN

    @Comment("北向资金（百万元）")
    var north_money: Double = Double.NaN

    @Comment("南向资金（百万元）")
    var south_money: Double = Double.NaN

}