package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//
class FundDiv : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("分红实施公告日")
    var imp_anndate: String = ""

    @Comment("分配收益基准日")
    var base_date: String = ""

    @Comment("方案进度")
    var div_proc: String = ""

    @Comment("权益登记日")
    var record_date: String = ""

    @Comment("除息日")
    var ex_date: String = ""

    @Comment("派息日")
    var pay_date: String = ""

    @Comment("收益支付日")
    var earpay_date: String = ""

    @Comment("净值除权日")
    var net_ex_date: String = ""

    @Comment("每股派息(元)")
    var div_cash: Double = Double.NaN

    @Comment("基准基金份额(万份)")
    var base_unit: Double = Double.NaN

    @Comment("可分配收益(元)")
    var ear_distr: Double = Double.NaN

    @Comment("收益分配金额(元)")
    var ear_amount: Double = Double.NaN

    @Comment("红利再投资到账日")
    var account_date: String = ""

    @Comment("份额基准年度")
    var base_year: String = ""
}