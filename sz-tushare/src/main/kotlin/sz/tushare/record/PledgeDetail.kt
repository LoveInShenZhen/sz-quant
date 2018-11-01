package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//
class PledgeDetail : RecordBase() {

    @Comment("TS股票代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("股东名称")
    var holder_name: String = ""

    @Comment("质押数量")
    var pledge_amount: Double = Double.NaN

    @Comment("质押开始日期")
    var start_date: String = ""

    @Comment("质押结束日期")
    var end_date: String = ""

    @Comment("是否已解押")
    var is_release: String = ""

    @Comment("解押日期")
    var release_date: String = ""

    @Comment("质押方")
    var pledgor: String = ""

    @Comment("持股总数")
    var holding_amount: Double = Double.NaN

    @Comment("质押总数")
    var pledged_amount: Double = Double.NaN

    @Comment("本次质押占总股本比例")
    var p_total_ratio: Double = Double.NaN

    @Comment("持股总数占总股本比例")
    var h_total_ratio: Double = Double.NaN

    @Comment("是否回购")
    var is_buyback: String = ""
}