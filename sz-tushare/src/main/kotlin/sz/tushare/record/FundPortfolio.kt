package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//
class FundPortfolio : RecordBase() {

    @Mark("TS基金代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("截止日期")
    var end_date: String = ""

    @Mark("股票代码")
    var symbol: String = ""

    @Mark("持有股票市值(元)")
    var mkv: Double = Double.NaN

    @Mark("持有股票数量（股）")
    var amount: Double = Double.NaN

    @Mark("占股票市值比")
    var stk_mkv_ratio: Double = Double.NaN

    @Mark("占流通股本比例")
    var stk_float_ratio: Double = Double.NaN

}