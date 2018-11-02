package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//
class FundPortfolio : RecordBase() {

    @Comment("TS基金代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("截止日期")
    var end_date: String = ""

    @Comment("股票代码")
    var symbol: String = ""

    @Comment("持有股票市值(元)")
    var mkv: Double = Double.NaN

    @Comment("持有股票数量（股）")
    var amount: Double = Double.NaN

    @Comment("占股票市值比")
    var stk_mkv_ratio: Double = Double.NaN

    @Comment("占流通股本比例")
    var stk_float_ratio: Double = Double.NaN

}