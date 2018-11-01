package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//
class Repurchase : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("截止日期")
    var end_date: String = ""

    @Comment("进度")
    var proc: String = ""

    @Comment("过期日期")
    var exp_date: String = ""

    @Comment("回购数量")
    var vol: Double = Double.NaN

    @Comment("回购金额")
    var amount: Double = Double.NaN

    @Comment("回购最高价")
    var high_limit: Double = Double.NaN

    @Comment("回购最低价")
    var low_limit: Double = Double.NaN
}