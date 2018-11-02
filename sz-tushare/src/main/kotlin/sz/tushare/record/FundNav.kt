package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//

/**
 * 公募基金净值
 * 参考: https://tushare.pro/document/2?doc_id=119
 */
class FundNav : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("截止日期")
    var end_date: String = ""

    @Comment("单位净值")
    var unit_nav: Double = Double.NaN

    @Comment("累计净值")
    var accum_nav: Double = Double.NaN

    @Comment("累计分红")
    var accum_div: Double = Double.NaN

    @Comment("资产净值")
    var net_asset: Double = Double.NaN

    @Comment("合计资产净值")
    var total_netasset: Double = Double.NaN

    @Comment("复权单位净值")
    var adj_nav: Double = Double.NaN

}