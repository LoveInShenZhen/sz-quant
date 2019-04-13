package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/28.
//

/**
 * 业绩预告
 * 参考: https://tushare.pro/document/2?doc_id=45
 */
class Forecast : RecordBase() {

    @Mark("TS股票代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("业绩预告类型(预增/预减/扭亏/首亏/续亏/续盈/略增/略减)")
    var type: String = ""

    @Mark("预告净利润变动幅度下限（%）")
    var p_change_min: Double = Double.NaN

    @Mark("预告净利润变动幅度上限（%）")
    var p_change_max: Double = Double.NaN

    @Mark("预告净利润下限（万元）")
    var net_profit_min: Double = Double.NaN

    @Mark("预告净利润上限（万元）")
    var net_profit_max: Double = Double.NaN

    @Mark("上年同期归属母公司净利润")
    var last_parent_net: Double = Double.NaN

    @Mark("首次公告日")
    var first_ann_date: String = ""

    @Mark("业绩预告摘要")
    var summary: String = ""

    @Mark("业绩变动原因")
    var change_reason: String = ""
}