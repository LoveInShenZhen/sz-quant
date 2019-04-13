package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/30.
//
class Top10FloatHolders : RecordBase() {

    @Mark("TS股票代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("股东名称")
    var holder_name: String = ""

    @Mark("持有数量（股）")
    var hold_amount: Double = Double.NaN
}