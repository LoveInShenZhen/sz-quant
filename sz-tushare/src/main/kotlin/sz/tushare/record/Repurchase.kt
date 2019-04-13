package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//
class Repurchase : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("截止日期")
    var end_date: String = ""

    @Mark("进度")
    var proc: String = ""

    @Mark("过期日期")
    var exp_date: String = ""

    @Mark("回购数量")
    var vol: Double = Double.NaN

    @Mark("回购金额")
    var amount: Double = Double.NaN

    @Mark("回购最高价")
    var high_limit: Double = Double.NaN

    @Mark("回购最低价")
    var low_limit: Double = Double.NaN
}