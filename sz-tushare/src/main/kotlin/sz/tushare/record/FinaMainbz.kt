package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/28.
//

/**
 * 主营业务构成
 * 参考: https://tushare.pro/document/2?doc_id=81
 */
class FinaMainbz : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("主营业务来源")
    var bz_item: String = ""

    @Mark("主营业务收入(元)")
    var bz_sales: Double = Double.NaN

    @Mark("主营业务利润(元)")
    var bz_profit: Double = Double.NaN

    @Mark("主营业务成本(元)")
    var bz_cost: Double = Double.NaN

    @Mark("货币代码")
    var curr_type: String = ""

    @Mark("是否更新")
    var update_flag: String = ""
}