package sz.tushare.record

import sz.annotations.Mark


//
// Created by kk on 2018/10/27.
//

/**
 * 财务指标数据
 * 参考: https://tushare.pro/document/2?doc_id=79
 */
class FinaIndicator : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("基本每股收益")
    var eps: Double = Double.NaN

    @Mark("稀释每股收益")
    var dt_eps: Double = Double.NaN

    @Mark("每股营业总收入")
    var total_revenue_ps: Double = Double.NaN

    @Mark("每股营业收入")
    var revenue_ps: Double = Double.NaN

    @Mark("每股资本公积")
    var capital_rese_ps: Double = Double.NaN

    @Mark("每股盈余公积")
    var surplus_rese_ps: Double = Double.NaN

    @Mark("每股未分配利润")
    var undist_profit_ps: Double = Double.NaN

    @Mark("非经常性损益")
    var extra_item: Double = Double.NaN

    @Mark("扣除非经常性损益后的净利润")
    var profit_dedt: Double = Double.NaN

    @Mark("毛利")
    var gross_margin: Double = Double.NaN

    @Mark("流动比率")
    var current_ratio: Double = Double.NaN

    @Mark("速动比率")
    var quick_ratio: Double = Double.NaN

    @Mark("保守速动比率")
    var cash_ratio: Double = Double.NaN

    @Mark("存货周转天数")
    var invturn_days: Double = Double.NaN

    @Mark("应收账款周转天数")
    var arturn_days: Double = Double.NaN

    @Mark("存货周转率")
    var inv_turn: Double = Double.NaN

    @Mark("应收账款周转率")
    var ar_turn: Double = Double.NaN

    @Mark("流动资产周转率")
    var ca_turn: Double = Double.NaN

    @Mark("固定资产周转率")
    var fa_turn: Double = Double.NaN

    @Mark("总资产周转率")
    var assets_turn: Double = Double.NaN

    @Mark("经营活动净收益")
    var op_income: Double = Double.NaN

    @Mark("价值变动净收益")
    var valuechange_income: Double = Double.NaN

    @Mark("利息费用")
    var interst_income: Double = Double.NaN

    @Mark("折旧与摊销")
    var daa: Double = Double.NaN

    @Mark("息税前利润")
    var ebit: Double = Double.NaN

    @Mark("息税折旧摊销前利润")
    var ebitda: Double = Double.NaN

    @Mark("企业自由现金流量")
    var fcff: Double = Double.NaN

    @Mark("股权自由现金流量")
    var fcfe: Double = Double.NaN

    @Mark("无息流动负债")
    var current_exint: Double = Double.NaN

    @Mark("无息非流动负债")
    var noncurrent_exint: Double = Double.NaN

    @Mark("带息债务")
    var interestdebt: Double = Double.NaN

    @Mark("净债务")
    var netdebt: Double = Double.NaN

    @Mark("有形资产")
    var tangible_asset: Double = Double.NaN

    @Mark("营运资金")
    var working_capital: Double = Double.NaN

    @Mark("营运流动资本")
    var networking_capital: Double = Double.NaN

    @Mark("全部投入资本")
    var invest_capital: Double = Double.NaN

    @Mark("留存收益")
    var retained_earnings: Double = Double.NaN

    @Mark("期末摊薄每股收益")
    var diluted2_eps: Double = Double.NaN

    @Mark("每股净资产")
    var bps: Double = Double.NaN

    @Mark("每股经营活动产生的现金流量净额")
    var ocfps: Double = Double.NaN

    @Mark("每股留存收益")
    var retainedps: Double = Double.NaN

    @Mark("每股现金流量净额")
    var cfps: Double = Double.NaN

    @Mark("每股息税前利润")
    var ebit_ps: Double = Double.NaN

    @Mark("每股企业自由现金流量")
    var fcff_ps: Double = Double.NaN

    @Mark("每股股东自由现金流量")
    var fcfe_ps: Double = Double.NaN

    @Mark("销售净利率")
    var netprofit_margin: Double = Double.NaN

    @Mark("销售毛利率")
    var grossprofit_margin: Double = Double.NaN

    @Mark("销售成本率")
    var cogs_of_sales: Double = Double.NaN

    @Mark("销售期间费用率")
    var expense_of_sales: Double = Double.NaN

    @Mark("净利润/营业总收入")
    var profit_to_gr: Double = Double.NaN

    @Mark("销售费用/营业总收入")
    var saleexp_to_gr: Double = Double.NaN

    @Mark("管理费用/营业总收入")
    var adminexp_of_gr: Double = Double.NaN

    @Mark("财务费用/营业总收入")
    var finaexp_of_gr: Double = Double.NaN

    @Mark("资产减值损失/营业总收入")
    var impai_ttm: Double = Double.NaN

    @Mark("营业总成本/营业总收入")
    var gc_of_gr: Double = Double.NaN

    @Mark("营业利润/营业总收入")
    var op_of_gr: Double = Double.NaN

    @Mark("息税前利润/营业总收入")
    var ebit_of_gr: Double = Double.NaN

    @Mark("净资产收益率")
    var roe: Double = Double.NaN

    @Mark("加权平均净资产收益率")
    var roe_waa: Double = Double.NaN

    @Mark("净资产收益率(扣除非经常损益)")
    var roe_dt: Double = Double.NaN

    @Mark("总资产报酬率")
    var roa: Double = Double.NaN

    @Mark("总资产净利润")
    var npta: Double = Double.NaN

    @Mark("投入资本回报率")
    var roic: Double = Double.NaN

    @Mark("年化净资产收益率")
    var roe_yearly: Double = Double.NaN

    @Mark("年化总资产报酬率")
    var roa2_yearly: Double = Double.NaN

    @Mark("平均净资产收益率(增发条件)")
    var roe_avg: Double = Double.NaN

    @Mark("经营活动净收益/利润总额")
    var opincome_of_ebt: Double = Double.NaN

    @Mark("价值变动净收益/利润总额")
    var investincome_of_ebt: Double = Double.NaN

    @Mark("营业外收支净额/利润总额")
    var n_op_profit_of_ebt: Double = Double.NaN

    @Mark("所得税/利润总额")
    var tax_to_ebt: Double = Double.NaN

    @Mark("扣除非经常损益后的净利润/净利润")
    var dtprofit_to_profit: Double = Double.NaN

    @Mark("销售商品提供劳务收到的现金/营业收入")
    var salescash_to_or: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额/营业收入")
    var ocf_to_or: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额/经营活动净收益")
    var ocf_to_opincome: Double = Double.NaN

    @Mark("资本支出/折旧和摊销")
    var capitalized_to_da: Double = Double.NaN

    @Mark("资产负债率")
    var debt_to_assets: Double = Double.NaN

    @Mark("权益乘数")
    var assets_to_eqt: Double = Double.NaN

    @Mark("权益乘数(杜邦分析)")
    var dp_assets_to_eqt: Double = Double.NaN

    @Mark("流动资产/总资产")
    var ca_to_assets: Double = Double.NaN

    @Mark("非流动资产/总资产")
    var nca_to_assets: Double = Double.NaN

    @Mark("有形资产/总资产")
    var tbassets_to_totalassets: Double = Double.NaN

    @Mark("带息债务/全部投入资本")
    var int_to_talcap: Double = Double.NaN

    @Mark("归属于母公司的股东权益/全部投入资本")
    var eqt_to_talcapital: Double = Double.NaN

    @Mark("流动负债/负债合计")
    var currentdebt_to_debt: Double = Double.NaN

    @Mark("非流动负债/负债合计")
    var longdeb_to_debt: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额/流动负债")
    var ocf_to_shortdebt: Double = Double.NaN

    @Mark("产权比率")
    var debt_to_eqt: Double = Double.NaN

    @Mark("归属于母公司的股东权益/负债合计")
    var eqt_to_debt: Double = Double.NaN

    @Mark("归属于母公司的股东权益/带息债务")
    var eqt_to_interestdebt: Double = Double.NaN

    @Mark("有形资产/负债合计")
    var tangibleasset_to_debt: Double = Double.NaN

    @Mark("有形资产/带息债务")
    var tangasset_to_intdebt: Double = Double.NaN

    @Mark("有形资产/净债务")
    var tangibleasset_to_netdebt: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额/负债合计")
    var ocf_to_debt: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额/带息债务")
    var ocf_to_interestdebt: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额/净债务")
    var ocf_to_netdebt: Double = Double.NaN

    @Mark("已获利息倍数(EBIT/利息费用)")
    var ebit_to_interest: Double = Double.NaN

    @Mark("长期债务与营运资金比率")
    var longdebt_to_workingcapital: Double = Double.NaN

    @Mark("息税折旧摊销前利润/负债合计")
    var ebitda_to_debt: Double = Double.NaN

    @Mark("营业周期")
    var turn_days: Double = Double.NaN

    @Mark("年化总资产净利率")
    var roa_yearly: Double = Double.NaN

    @Mark("总资产净利率(杜邦分析)")
    var roa_dp: Double = Double.NaN

    @Mark("固定资产合计")
    var fixed_assets: Double = Double.NaN

    @Mark("扣除财务费用前营业利润")
    var profit_prefin_exp: Double = Double.NaN

    @Mark("非营业利润")
    var non_op_profit: Double = Double.NaN

    @Mark("营业利润／利润总额")
    var op_to_ebt: Double = Double.NaN

    @Mark("非营业利润／利润总额")
    var nop_to_ebt: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额／营业利润")
    var ocf_to_profit: Double = Double.NaN

    @Mark("货币资金／流动负债")
    var cash_to_liqdebt: Double = Double.NaN

    @Mark("货币资金／带息流动负债")
    var cash_to_liqdebt_withinterest: Double = Double.NaN

    @Mark("营业利润／流动负债")
    var op_to_liqdebt: Double = Double.NaN

    @Mark("营业利润／负债合计")
    var op_to_debt: Double = Double.NaN

    @Mark("年化投入资本回报率")
    var roic_yearly: Double = Double.NaN

    @Mark("固定资产合计周转率")
    var total_fa_trun: Double = Double.NaN

    @Mark("利润总额／营业收入")
    var profit_to_op: Double = Double.NaN

    @Mark("经营活动单季度净收益")
    var q_opincome: Double = Double.NaN

    @Mark("价值变动单季度净收益")
    var q_investincome: Double = Double.NaN

    @Mark("扣除非经常损益后的单季度净利润")
    var q_dtprofit: Double = Double.NaN

    @Mark("每股收益(单季度)")
    var q_eps: Double = Double.NaN

    @Mark("销售净利率(单季度)")
    var q_netprofit_margin: Double = Double.NaN

    @Mark("销售毛利率(单季度)")
    var q_gsprofit_margin: Double = Double.NaN

    @Mark("销售期间费用率(单季度)")
    var q_exp_to_sales: Double = Double.NaN

    @Mark("净利润／营业总收入(单季度)")
    var q_profit_to_gr: Double = Double.NaN

    @Mark("销售费用／营业总收入 (单季度)")
    var q_saleexp_to_gr: Double = Double.NaN

    @Mark("管理费用／营业总收入 (单季度)")
    var q_adminexp_to_gr: Double = Double.NaN

    @Mark("财务费用／营业总收入 (单季度)")
    var q_finaexp_to_gr: Double = Double.NaN

    @Mark("资产减值损失／营业总收入(单季度)")
    var q_impair_to_gr_ttm: Double = Double.NaN

    @Mark("营业总成本／营业总收入 (单季度)")
    var q_gc_to_gr: Double = Double.NaN

    @Mark("营业利润／营业总收入(单季度)")
    var q_op_to_gr: Double = Double.NaN

    @Mark("净资产收益率(单季度)")
    var q_roe: Double = Double.NaN

    @Mark("净资产单季度收益率(扣除非经常损益)")
    var q_dt_roe: Double = Double.NaN

    @Mark("总资产净利润(单季度)")
    var q_npta: Double = Double.NaN

    @Mark("经营活动净收益／利润总额(单季度)")
    var q_opincome_to_ebt: Double = Double.NaN

    @Mark("价值变动净收益／利润总额(单季度)")
    var q_investincome_to_ebt: Double = Double.NaN

    @Mark("扣除非经常损益后的净利润／净利润(单季度)")
    var q_dtprofit_to_profit: Double = Double.NaN

    @Mark("销售商品提供劳务收到的现金／营业收入(单季度)")
    var q_salescash_to_or: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额／营业收入(单季度)")
    var q_ocf_to_sales: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额／经营活动净收益(单季度)")
    var q_ocf_to_or: Double = Double.NaN

    @Mark("基本每股收益同比增长率(%)")
    var basic_eps_yoy: Double = Double.NaN

    @Mark("稀释每股收益同比增长率(%)")
    var dt_eps_yoy: Double = Double.NaN

    @Mark("每股经营活动产生的现金流量净额同比增长率(%)")
    var cfps_yoy: Double = Double.NaN

    @Mark("营业利润同比增长率(%)")
    var op_yoy: Double = Double.NaN

    @Mark("利润总额同比增长率(%)")
    var ebt_yoy: Double = Double.NaN

    @Mark("归属母公司股东的净利润同比增长率(%)")
    var netprofit_yoy: Double = Double.NaN

    @Mark("归属母公司股东的净利润-扣除非经常损益同比增长率(%)")
    var dt_netprofit_yoy: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额同比增长率(%)")
    var ocf_yoy: Double = Double.NaN

    @Mark("净资产收益率(摊薄)同比增长率(%)")
    var roe_yoy: Double = Double.NaN

    @Mark("每股净资产相对年初增长率(%)")
    var bps_yoy: Double = Double.NaN

    @Mark("资产总计相对年初增长率(%)")
    var assets_yoy: Double = Double.NaN

    @Mark("归属母公司的股东权益相对年初增长率(%)")
    var eqt_yoy: Double = Double.NaN

    @Mark("营业总收入同比增长率(%)")
    var tr_yoy: Double = Double.NaN

    @Mark("营业收入同比增长率(%)")
    var or_yoy: Double = Double.NaN

    @Mark("营业总收入同比增长率(%)(单季度)")
    var q_gr_yoy: Double = Double.NaN

    @Mark("营业总收入环比增长率(%)(单季度)")
    var q_gr_qoq: Double = Double.NaN

    @Mark("营业收入同比增长率(%)(单季度)")
    var q_sales_yoy: Double = Double.NaN

    @Mark("营业收入环比增长率(%)(单季度)")
    var q_sales_qoq: Double = Double.NaN

    @Mark("营业利润同比增长率(%)(单季度)")
    var q_op_yoy: Double = Double.NaN

    @Mark("营业利润环比增长率(%)(单季度)")
    var q_op_qoq: Double = Double.NaN

    @Mark("净利润同比增长率(%)(单季度)")
    var q_profit_yoy: Double = Double.NaN

    @Mark("净利润环比增长率(%)(单季度)")
    var q_profit_qoq: Double = Double.NaN

    @Mark("归属母公司股东的净利润同比增长率(%)(单季度)")
    var q_netprofit_yoy: Double = Double.NaN

    @Mark("归属母公司股东的净利润环比增长率(%)(单季度)")
    var q_netprofit_qoq: Double = Double.NaN

    @Mark("净资产同比增长率")
    var equity_yoy: Double = Double.NaN

    @Mark("研发费用")
    var rd_exp: Double = Double.NaN

}