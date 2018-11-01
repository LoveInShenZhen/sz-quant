package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/29.
//

/**
 * 前十大股东
 * 参考: https://tushare.pro/document/2?doc_id=61
 */
class Top10Holders : RecordBase() {

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

    @Comment("持有比例")
    var hold_ratio: Double = Double.NaN
}