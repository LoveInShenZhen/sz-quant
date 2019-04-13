package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//
class PledgeDetail : RecordBase() {

    @Mark("TS股票代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("股东名称")
    var holder_name: String = ""

    @Mark("质押数量")
    var pledge_amount: Double = Double.NaN

    @Mark("质押开始日期")
    var start_date: String = ""

    @Mark("质押结束日期")
    var end_date: String = ""

    @Mark("是否已解押")
    var is_release: String = ""

    @Mark("解押日期")
    var release_date: String = ""

    @Mark("质押方")
    var pledgor: String = ""

    @Mark("持股总数")
    var holding_amount: Double = Double.NaN

    @Mark("质押总数")
    var pledged_amount: Double = Double.NaN

    @Mark("本次质押占总股本比例")
    var p_total_ratio: Double = Double.NaN

    @Mark("持股总数占总股本比例")
    var h_total_ratio: Double = Double.NaN

    @Mark("是否回购")
    var is_buyback: String = ""
}