package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/28.
//

/**
 * 主营业务构成
 * 参考: https://tushare.pro/document/2?doc_id=81
 */
class FinaMainbz : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("报告期")
    var end_date: String = ""

    @Comment("主营业务来源")
    var bz_item: String = ""

    @Comment("主营业务收入(元)")
    var bz_sales: Double = Double.NaN

    @Comment("主营业务利润(元)")
    var bz_profit: Double = Double.NaN

    @Comment("主营业务成本(元)")
    var bz_cost: Double = Double.NaN

    @Comment("货币代码")
    var curr_type: String = ""

    @Comment("是否更新")
    var update_flag: String = ""
}