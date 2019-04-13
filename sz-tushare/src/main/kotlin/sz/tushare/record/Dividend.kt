package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/28.
//

/**
 * 分红送股数据
 * 参考: https://tushare.pro/document/2?doc_id=103
 */
class Dividend : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("分红年度")
    var end_date: String = ""

    @Mark("预案公告日")
    var ann_date: String = ""

    @Mark("实施进度")
    var div_proc: String = ""

    @Mark("每股送转")
    var stk_div: Double = Double.NaN

    @Mark("每股送股比例")
    var stk_bo_rate: Double = Double.NaN

    @Mark("每股转增比例")
    var stk_co_rate: Double = Double.NaN

    @Mark("每股分红（税后）")
    var cash_div: Double = Double.NaN

    @Mark("每股分红（税前）")
    var cash_div_tax: Double = Double.NaN

    @Mark("股权登记日")
    var record_date: String = ""

    @Mark("除权除息日")
    var ex_date: String = ""

    @Mark("派息日")
    var pay_date: String = ""

    @Mark("红股上市日")
    var div_listdate: String = ""

    @Mark("实施公告日")
    var imp_ann_date: String = ""

    @Mark("基准日")
    var base_date: String = ""

    @Mark("基准股本（万）")
    var base_share: Double = Double.NaN
}