package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//

/**
 * 公募基金净值
 * 参考: https://tushare.pro/document/2?doc_id=119
 */
class FundNav : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("截止日期")
    var end_date: String = ""

    @Mark("单位净值")
    var unit_nav: Double = Double.NaN

    @Mark("累计净值")
    var accum_nav: Double = Double.NaN

    @Mark("累计分红")
    var accum_div: Double = Double.NaN

    @Mark("资产净值")
    var net_asset: Double = Double.NaN

    @Mark("合计资产净值")
    var total_netasset: Double = Double.NaN

    @Mark("复权单位净值")
    var adj_nav: Double = Double.NaN

}