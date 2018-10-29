package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/28.
//

/**
 * 业绩快报
 * 参考: https://tushare.pro/document/2?doc_id=46
 */
class Express : RecordBase() {

    @Comment("TS股票代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("报告期")
    var end_date: String = ""

    @Comment("营业收入(元)")
    var revenue: Double = Double.NaN

    @Comment("营业利润(元)")
    var operate_profit: Double = Double.NaN

    @Comment("利润总额(元)")
    var total_profit: Double = Double.NaN

    @Comment("净利润(元)")
    var n_income: Double = Double.NaN

    @Comment("总资产(元)")
    var total_assets: Double = Double.NaN

    @Comment("股东权益合计(不含少数股东权益)(元)")
    var total_hldr_eqy_exc_min_int: Double = Double.NaN

    @Comment("每股收益(摊薄)(元)")
    var diluted_eps: Double = Double.NaN

    @Comment("净资产收益率(摊薄)(%)")
    var diluted_roe: Double = Double.NaN

    @Comment("去年同期修正后净利润")
    var yoy_net_profit: Double = Double.NaN

    @Comment("每股净资产")
    var bps: Double = Double.NaN

    @Comment("同比增长率:营业收入")
    var yoy_sales: Double = Double.NaN

    @Comment("同比增长率:营业利润")
    var yoy_op: Double = Double.NaN

    @Comment("同比增长率:利润总额")
    var yoy_tp: Double = Double.NaN

    @Comment("同比增长率:归属母公司股东的净利润")
    var yoy_dedu_np: Double = Double.NaN

    @Comment("同比增长率:基本每股收益")
    var yoy_eps: Double = Double.NaN

    @Comment("同比增减:加权平均净资产收益率")
    var yoy_roe: Double = Double.NaN

    @Comment("比年初增长率:总资产")
    var growth_assets: Double = Double.NaN

    @Comment("比年初增长率:归属母公司的股东权益")
    var yoy_equity: Double = Double.NaN

    @Comment("比年初增长率:归属于母公司股东的每股净资产")
    var growth_bps: Double = Double.NaN

    @Comment("去年同期营业收入")
    var or_last_year: Double = Double.NaN

    @Comment("去年同期营业利润")
    var op_last_year: Double = Double.NaN

    @Comment("去年同期利润总额")
    var tp_last_year: Double = Double.NaN

    @Comment("去年同期净利润")
    var np_last_year: Double = Double.NaN

    @Comment("去年同期每股收益")
    var eps_last_year: Double = Double.NaN

    @Comment("期初净资产")
    var open_net_assets: Double = Double.NaN

    @Comment("期初每股净资产")
    var open_bps: Double = Double.NaN

    @Comment("业绩简要说明")
    var perf_summary: String = ""

    @Comment("是否审计： 1是 0否")
    var is_audit: Int = -1

    @Comment("备注")
    var remark: String = ""
}