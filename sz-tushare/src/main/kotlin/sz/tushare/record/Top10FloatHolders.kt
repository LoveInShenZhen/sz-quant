package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/30.
//
class Top10FloatHolders : RecordBase() {

    @Comment("TS股票代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("报告期")
    var end_date: String = ""

    @Comment("股东名称")
    var holder_name: String = ""

    @Comment("持有数量（股）")
    var hold_amount: Double = Double.NaN
}