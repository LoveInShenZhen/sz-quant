package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/25.
//
class Income : RecordBase() {

    @Mark("TS股票代码")
    var ts_code = ""

    @Mark("公告日期")
    var ann_date = ""

    @Mark("实际公告日期，即发生过数据变更的最终日期")
    var f_ann_date = ""

    @Mark("报告期")
    var end_date = ""

    @Mark("报告类型： 参考下表说明")
    var report_type = ""

    @Mark("公司类型：1一般工商业 2银行 3保险 4证券")
    var comp_type = ""

    @Mark("基本每股收益")
    var basic_eps = Double.NaN

    @Mark("稀释每股收益")
    var diluted_eps = Double.NaN

    @Mark("营业总收入 (元，下同)")
    var total_revenue = Double.NaN

    @Mark("营业收入")
    var revenue = Double.NaN

    @Mark("利息收入")
    var int_income = Double.NaN

    @Mark("已赚保费")
    var prem_earned = Double.NaN

    @Mark("手续费及佣金收入")
    var comm_income = Double.NaN

    @Mark("手续费及佣金净收入")
    var n_commis_income = Double.NaN

    @Mark("其他经营净收益")
    var n_oth_income = Double.NaN

    @Mark("加:其他业务净收益")
    var n_oth_b_income = Double.NaN

    @Mark("保险业务收入")
    var prem_income = Double.NaN

    @Mark("减:分出保费")
    var out_prem = Double.NaN

    @Mark("提取未到期责任准备金")
    var une_prem_reser = Double.NaN

    @Mark("其中:分保费收入")
    var reins_income = Double.NaN

    @Mark("代理买卖证券业务净收入")
    var n_sec_tb_income = Double.NaN

    @Mark("证券承销业务净收入")
    var n_sec_uw_income = Double.NaN

    @Mark("受托客户资产管理业务净收入")
    var n_asset_mg_income = Double.NaN

    @Mark("其他业务收入")
    var oth_b_income = Double.NaN

    @Mark("加:公允价值变动净收益")
    var fv_value_chg_gain = Double.NaN

    @Mark("加:投资净收益")
    var invest_income = Double.NaN

    @Mark("其中:对联营企业和合营企业的投资收益")
    var ass_invest_income = Double.NaN

    @Mark("加:汇兑净收益")
    var forex_gain = Double.NaN

    @Mark("营业总成本")
    var total_cogs = Double.NaN

    @Mark("减:营业成本")
    var oper_cost = Double.NaN

    @Mark("减:利息支出")
    var int_exp = Double.NaN

    @Mark("减:手续费及佣金支出")
    var comm_exp = Double.NaN

    @Mark("减:营业税金及附加")
    var biz_tax_surchg = Double.NaN

    @Mark("减:销售费用")
    var sell_exp = Double.NaN

    @Mark("减:管理费用")
    var admin_exp = Double.NaN

    @Mark("减:财务费用")
    var fin_exp = Double.NaN

    @Mark("减:资产减值损失")
    var assets_impair_loss = Double.NaN

    @Mark("退保金")
    var prem_refund = Double.NaN

    @Mark("赔付总支出")
    var compens_payout = Double.NaN

    @Mark("提取保险责任准备金")
    var reser_insur_liab = Double.NaN

    @Mark("保户红利支出")
    var div_payt = Double.NaN

    @Mark("分保费用")
    var reins_exp = Double.NaN

    @Mark("营业支出")
    var oper_exp = Double.NaN

    @Mark("减:摊回赔付支出")
    var compens_payout_refu = Double.NaN

    @Mark("减:摊回保险责任准备金")
    var insur_reser_refu = Double.NaN

    @Mark("减:摊回分保费用")
    var reins_cost_refund = Double.NaN

    @Mark("其他业务成本")
    var other_bus_cost = Double.NaN

    @Mark("营业利润")
    var operate_profit = Double.NaN

    @Mark("加:营业外收入")
    var non_oper_income = Double.NaN

    @Mark("减:营业外支出")
    var non_oper_exp = Double.NaN

    @Mark("其中:减:非流动资产处置净损失")
    var nca_disploss = Double.NaN

    @Mark("利润总额")
    var total_profit = Double.NaN

    @Mark("所得税费用")
    var income_tax = Double.NaN

    @Mark("净利润(含少数股东损益)")
    var n_income = Double.NaN

    @Mark("净利润(不含少数股东损益)")
    var n_income_attr_p = Double.NaN

    @Mark("少数股东损益")
    var minority_gain = Double.NaN

    @Mark("其他综合收益")
    var oth_compr_income = Double.NaN

    @Mark("综合收益总额")
    var t_compr_income = Double.NaN

    @Mark("归属于母公司(或股东)的综合收益总额")
    var compr_inc_attr_p = Double.NaN

    @Mark("归属于少数股东的综合收益总额")
    var compr_inc_attr_m_s = Double.NaN

    @Mark("息税前利润")
    var ebit = Double.NaN

    @Mark("息税折旧摊销前利润")
    var ebitda = Double.NaN

    @Mark("保险业务支出")
    var insurance_exp = Double.NaN

    @Mark("年初未分配利润")
    var undist_profit = Double.NaN

    @Mark("可分配利润")
    var distable_profit = Double.NaN
    

}