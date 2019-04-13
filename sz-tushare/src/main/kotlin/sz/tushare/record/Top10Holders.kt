package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/29.
//

/**
 * 前十大股东
 * 参考: https://tushare.pro/document/2?doc_id=61
 */
class Top10Holders : RecordBase() {

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

    @Mark("持有比例")
    var hold_ratio: Double = Double.NaN
}