package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/27.
//

/**
 * 现金流量表
 * 参考: https://tushare.pro/document/2?doc_id=44
 */
class Cashflow : RecordBase() {

    @Mark("TS股票代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("实际公告日期")
    var f_ann_date: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("公司类型：1一般工商业 2银行 3保险 4证券")
    var comp_type: String = ""

    @Mark("报表类型：见下方详细说明")
    var report_type: String = ""

    @Mark("净利润 (元，下同)")
    var net_profit: Double = Double.NaN

    @Mark("财务费用")
    var finan_exp: Double = Double.NaN

    @Mark("销售商品、提供劳务收到的现金")
    var c_fr_sale_sg: Double = Double.NaN

    @Mark("收到的税费返还")
    var recp_tax_rends: Double = Double.NaN

    @Mark("客户存款和同业存放款项净增加额")
    var n_depos_incr_fi: Double = Double.NaN

    @Mark("向中央银行借款净增加额")
    var n_incr_loans_cb: Double = Double.NaN

    @Mark("向其他金融机构拆入资金净增加额")
    var n_inc_borr_oth_fi: Double = Double.NaN

    @Mark("收到原保险合同保费取得的现金")
    var prem_fr_orig_contr: Double = Double.NaN

    @Mark("保户储金净增加额")
    var n_incr_insured_dep: Double = Double.NaN

    @Mark("收到再保业务现金净额")
    var n_reinsur_prem: Double = Double.NaN

    @Mark("处置交易性金融资产净增加额")
    var n_incr_disp_tfa: Double = Double.NaN

    @Mark("收取利息和手续费净增加额")
    var ifc_cash_incr: Double = Double.NaN

    @Mark("处置可供出售金融资产净增加额")
    var n_incr_disp_faas: Double = Double.NaN

    @Mark("拆入资金净增加额")
    var n_incr_loans_oth_bank: Double = Double.NaN

    @Mark("回购业务资金净增加额")
    var n_cap_incr_repur: Double = Double.NaN

    @Mark("收到其他与经营活动有关的现金")
    var c_fr_oth_operate_a: Double = Double.NaN

    @Mark("经营活动现金流入小计")
    var c_inf_fr_operate_a: Double = Double.NaN

    @Mark("购买商品、接受劳务支付的现金")
    var c_paid_goods_s: Double = Double.NaN

    @Mark("支付给职工以及为职工支付的现金")
    var c_paid_to_for_empl: Double = Double.NaN

    @Mark("支付的各项税费")
    var c_paid_for_taxes: Double = Double.NaN

    @Mark("客户贷款及垫款净增加额")
    var n_incr_clt_loan_adv: Double = Double.NaN

    @Mark("存放央行和同业款项净增加额")
    var n_incr_dep_cbob: Double = Double.NaN

    @Mark("支付原保险合同赔付款项的现金")
    var c_pay_claims_orig_inco: Double = Double.NaN

    @Mark("支付手续费的现金")
    var pay_handling_chrg: Double = Double.NaN

    @Mark("支付保单红利的现金")
    var pay_comm_insur_plcy: Double = Double.NaN

    @Mark("支付其他与经营活动有关的现金")
    var oth_cash_pay_oper_act: Double = Double.NaN

    @Mark("经营活动现金流出小计")
    var st_cash_out_act: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额")
    var n_cashflow_act: Double = Double.NaN

    @Mark("收到其他与投资活动有关的现金")
    var oth_recp_ral_inv_act: Double = Double.NaN

    @Mark("收回投资收到的现金")
    var c_disp_withdrwl_invest: Double = Double.NaN

    @Mark("取得投资收益收到的现金")
    var c_recp_return_invest: Double = Double.NaN

    @Mark("处置固定资产、无形资产和其他长期资产收回的现金净额")
    var n_recp_disp_fiolta: Double = Double.NaN

    @Mark("处置子公司及其他营业单位收到的现金净额")
    var n_recp_disp_sobu: Double = Double.NaN

    @Mark("投资活动现金流入小计")
    var stot_inflows_inv_act: Double = Double.NaN

    @Mark("购建固定资产、无形资产和其他长期资产支付的现金")
    var c_pay_acq_const_fiolta: Double = Double.NaN

    @Mark("投资支付的现金")
    var c_paid_invest: Double = Double.NaN

    @Mark("取得子公司及其他营业单位支付的现金净额")
    var n_disp_subs_oth_biz: Double = Double.NaN

    @Mark("支付其他与投资活动有关的现金")
    var oth_pay_ral_inv_act: Double = Double.NaN

    @Mark("质押贷款净增加额")
    var n_incr_pledge_loan: Double = Double.NaN

    @Mark("投资活动现金流出小计")
    var stot_out_inv_act: Double = Double.NaN

    @Mark("投资活动产生的现金流量净额")
    var n_cashflow_inv_act: Double = Double.NaN

    @Mark("取得借款收到的现金")
    var c_recp_borrow: Double = Double.NaN

    @Mark("发行债券收到的现金")
    var proc_issue_bonds: Double = Double.NaN

    @Mark("收到其他与筹资活动有关的现金")
    var oth_cash_recp_ral_fnc_act: Double = Double.NaN

    @Mark("筹资活动现金流入小计")
    var stot_cash_in_fnc_act: Double = Double.NaN

    @Mark("企业自由现金流量")
    var free_cashflow: Double = Double.NaN

    @Mark("偿还债务支付的现金")
    var c_prepay_amt_borr: Double = Double.NaN

    @Mark("分配股利、利润或偿付利息支付的现金")
    var c_pay_dist_dpcp_int_exp: Double = Double.NaN

    @Mark("其中:子公司支付给少数股东的股利、利润")
    var incl_dvd_profit_paid_sc_ms: Double = Double.NaN

    @Mark("支付其他与筹资活动有关的现金")
    var oth_cashpay_ral_fnc_act: Double = Double.NaN

    @Mark("筹资活动现金流出小计")
    var stot_cashout_fnc_act: Double = Double.NaN

    @Mark("筹资活动产生的现金流量净额")
    var n_cash_flows_fnc_act: Double = Double.NaN

    @Mark("汇率变动对现金的影响")
    var eff_fx_flu_cash: Double = Double.NaN

    @Mark("现金及现金等价物净增加额")
    var n_incr_cash_cash_equ: Double = Double.NaN

    @Mark("期初现金及现金等价物余额")
    var c_cash_equ_beg_period: Double = Double.NaN

    @Mark("期末现金及现金等价物余额")
    var c_cash_equ_end_period: Double = Double.NaN

    @Mark("吸收投资收到的现金")
    var c_recp_cap_contrib: Double = Double.NaN

    @Mark("其中:子公司吸收少数股东投资收到的现金")
    var incl_cash_rec_saims: Double = Double.NaN

    @Mark("未确认投资损失")
    var uncon_invest_loss: Double = Double.NaN

    @Mark("加:资产减值准备")
    var prov_depr_assets: Double = Double.NaN

    @Mark("固定资产折旧、油气资产折耗、生产性生物资产折旧")
    var depr_fa_coga_dpba: Double = Double.NaN

    @Mark("无形资产摊销")
    var amort_intang_assets: Double = Double.NaN

    @Mark("长期待摊费用摊销")
    var lt_amort_deferred_exp: Double = Double.NaN

    @Mark("待摊费用减少")
    var decr_deferred_exp: Double = Double.NaN

    @Mark("预提费用增加")
    var incr_acc_exp: Double = Double.NaN

    @Mark("处置固定、无形资产和其他长期资产的损失")
    var loss_disp_fiolta: Double = Double.NaN

    @Mark("固定资产报废损失")
    var loss_scr_fa: Double = Double.NaN

    @Mark("公允价值变动损失")
    var loss_fv_chg: Double = Double.NaN

    @Mark("投资损失")
    var invest_loss: Double = Double.NaN

    @Mark("递延所得税资产减少")
    var decr_def_inc_tax_assets: Double = Double.NaN

    @Mark("递延所得税负债增加")
    var incr_def_inc_tax_liab: Double = Double.NaN

    @Mark("存货的减少")
    var decr_inventories: Double = Double.NaN

    @Mark("经营性应收项目的减少")
    var decr_oper_payable: Double = Double.NaN

    @Mark("经营性应付项目的增加")
    var incr_oper_payable: Double = Double.NaN

    @Mark("其他")
    var others: Double = Double.NaN

    @Mark("经营活动产生的现金流量净额(间接法)")
    var im_net_cashflow_oper_act: Double = Double.NaN

    @Mark("债务转为资本")
    var conv_debt_into_cap: Double = Double.NaN

    @Mark("一年内到期的可转换公司债券")
    var conv_copbonds_due_within_1y: Double = Double.NaN

    @Mark("融资租入固定资产")
    var fa_fnc_leases: Double = Double.NaN

    @Mark("现金的期末余额")
    var end_bal_cash: Double = Double.NaN

    @Mark("减:现金的期初余额")
    var beg_bal_cash: Double = Double.NaN

    @Mark("加:现金等价物的期末余额")
    var end_bal_cash_equ: Double = Double.NaN

    @Mark("减:现金等价物的期初余额")
    var beg_bal_cash_equ: Double = Double.NaN

    @Mark("现金及现金等价物净增加额(间接法)")
    var im_n_incr_cash_equ: Double = Double.NaN

}