package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//
class FundDiv : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("分红实施公告日")
    var imp_anndate: String = ""

    @Mark("分配收益基准日")
    var base_date: String = ""

    @Mark("方案进度")
    var div_proc: String = ""

    @Mark("权益登记日")
    var record_date: String = ""

    @Mark("除息日")
    var ex_date: String = ""

    @Mark("派息日")
    var pay_date: String = ""

    @Mark("收益支付日")
    var earpay_date: String = ""

    @Mark("净值除权日")
    var net_ex_date: String = ""

    @Mark("每股派息(元)")
    var div_cash: Double = Double.NaN

    @Mark("基准基金份额(万份)")
    var base_unit: Double = Double.NaN

    @Mark("可分配收益(元)")
    var ear_distr: Double = Double.NaN

    @Mark("收益分配金额(元)")
    var ear_amount: Double = Double.NaN

    @Mark("红利再投资到账日")
    var account_date: String = ""

    @Mark("份额基准年度")
    var base_year: String = ""
}