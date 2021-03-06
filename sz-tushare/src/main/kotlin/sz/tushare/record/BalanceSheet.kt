package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/27.
//

/**
 * 资产负债表
 * 参考: https://tushare.pro/document/2?doc_id=36
 */
class BalanceSheet : RecordBase() {

    @Mark("TS股票代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("实际公告日期")
    var f_ann_date: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("报表类型：见下方详细说明")
    var report_type: String = ""

    @Mark("公司类型：1一般工商业 2银行 3保险 4证券")
    var comp_type: String = ""

    @Mark("期末总股本")
    var total_share: Double = Double.NaN

    @Mark("资本公积金 (元，下同)")
    var cap_rese: Double = Double.NaN

    @Mark("未分配利润")
    var undistr_porfit: Double = Double.NaN

    @Mark("盈余公积金")
    var surplus_rese: Double = Double.NaN

    @Mark("专项储备")
    var special_rese: Double = Double.NaN

    @Mark("货币资金")
    var money_cap: Double = Double.NaN

    @Mark("交易性金融资产")
    var trad_asset: Double = Double.NaN

    @Mark("应收票据")
    var notes_receiv: Double = Double.NaN

    @Mark("应收账款")
    var accounts_receiv: Double = Double.NaN

    @Mark("其他应收款")
    var oth_receiv: Double = Double.NaN

    @Mark("预付款项")
    var prepayment: Double = Double.NaN

    @Mark("应收股利")
    var div_receiv: Double = Double.NaN

    @Mark("应收利息")
    var int_receiv: Double = Double.NaN

    @Mark("存货")
    var inventories: Double = Double.NaN

    @Mark("长期待摊费用")
    var amor_exp: Double = Double.NaN

    @Mark("一年内到期的非流动资产")
    var nca_within_1y: Double = Double.NaN

    @Mark("结算备付金")
    var sett_rsrv: Double = Double.NaN

    @Mark("拆出资金")
    var loanto_oth_bank_fi: Double = Double.NaN

    @Mark("应收保费")
    var premium_receiv: Double = Double.NaN

    @Mark("应收分保账款")
    var reinsur_receiv: Double = Double.NaN

    @Mark("应收分保合同准备金")
    var reinsur_res_receiv: Double = Double.NaN

    @Mark("买入返售金融资产")
    var pur_resale_fa: Double = Double.NaN

    @Mark("其他流动资产")
    var oth_cur_assets: Double = Double.NaN

    @Mark("流动资产合计")
    var total_cur_assets: Double = Double.NaN

    @Mark("可供出售金融资产")
    var fa_avail_for_sale: Double = Double.NaN

    @Mark("持有至到期投资")
    var htm_invest: Double = Double.NaN

    @Mark("长期股权投资")
    var lt_eqt_invest: Double = Double.NaN

    @Mark("投资性房地产")
    var invest_real_estate: Double = Double.NaN

    @Mark("定期存款")
    var time_deposits: Double = Double.NaN

    @Mark("其他资产")
    var oth_assets: Double = Double.NaN

    @Mark("长期应收款")
    var lt_rec: Double = Double.NaN

    @Mark("固定资产")
    var fix_assets: Double = Double.NaN

    @Mark("在建工程")
    var cip: Double = Double.NaN

    @Mark("工程物资")
    var const_materials: Double = Double.NaN

    @Mark("固定资产清理")
    var fixed_assets_disp: Double = Double.NaN

    @Mark("生产性生物资产")
    var produc_bio_assets: Double = Double.NaN

    @Mark("油气资产")
    var oil_and_gas_assets: Double = Double.NaN

    @Mark("无形资产")
    var intan_assets: Double = Double.NaN

    @Mark("研发支出")
    var r_and_d: Double = Double.NaN

    @Mark("商誉")
    var goodwill: Double = Double.NaN

    @Mark("长期待摊费用")
    var lt_amor_exp: Double = Double.NaN

    @Mark("递延所得税资产")
    var defer_tax_assets: Double = Double.NaN

    @Mark("发放贷款及垫款")
    var decr_in_disbur: Double = Double.NaN

    @Mark("其他非流动资产")
    var oth_nca: Double = Double.NaN

    @Mark("非流动资产合计")
    var total_nca: Double = Double.NaN

    @Mark("现金及存放中央银行款项")
    var cash_reser_cb: Double = Double.NaN

    @Mark("存放同业和其它金融机构款项")
    var depos_in_oth_bfi: Double = Double.NaN

    @Mark("贵金属")
    var prec_metals: Double = Double.NaN

    @Mark("衍生金融资产")
    var deriv_assets: Double = Double.NaN

    @Mark("应收分保未到期责任准备金")
    var rr_reins_une_prem: Double = Double.NaN

    @Mark("应收分保未决赔款准备金")
    var rr_reins_outstd_cla: Double = Double.NaN

    @Mark("应收分保寿险责任准备金")
    var rr_reins_lins_liab: Double = Double.NaN

    @Mark("应收分保长期健康险责任准备金")
    var rr_reins_lthins_liab: Double = Double.NaN

    @Mark("存出保证金")
    var refund_depos: Double = Double.NaN

    @Mark("保户质押贷款")
    var ph_pledge_loans: Double = Double.NaN

    @Mark("存出资本保证金")
    var refund_cap_depos: Double = Double.NaN

    @Mark("独立账户资产")
    var indep_acct_assets: Double = Double.NaN

    @Mark("其中：客户资金存款")
    var client_depos: Double = Double.NaN

    @Mark("其中：客户备付金")
    var client_prov: Double = Double.NaN

    @Mark("其中:交易席位费")
    var transac_seat_fee: Double = Double.NaN

    @Mark("应收款项类投资")
    var invest_as_receiv: Double = Double.NaN

    @Mark("资产总计")
    var total_assets: Double = Double.NaN

    @Mark("长期借款")
    var lt_borr: Double = Double.NaN

    @Mark("短期借款")
    var st_borr: Double = Double.NaN

    @Mark("向中央银行借款")
    var cb_borr: Double = Double.NaN

    @Mark("吸收存款及同业存放")
    var depos_ib_deposits: Double = Double.NaN

    @Mark("拆入资金")
    var loan_oth_bank: Double = Double.NaN

    @Mark("交易性金融负债")
    var trading_fl: Double = Double.NaN

    @Mark("应付票据")
    var notes_payable: Double = Double.NaN

    @Mark("应付账款")
    var acct_payable: Double = Double.NaN

    @Mark("预收款项")
    var adv_receipts: Double = Double.NaN

    @Mark("卖出回购金融资产款")
    var sold_for_repur_fa: Double = Double.NaN

    @Mark("应付手续费及佣金")
    var comm_payable: Double = Double.NaN

    @Mark("应付职工薪酬")
    var payroll_payable: Double = Double.NaN

    @Mark("应交税费")
    var taxes_payable: Double = Double.NaN

    @Mark("应付利息")
    var int_payable: Double = Double.NaN

    @Mark("应付股利")
    var div_payable: Double = Double.NaN

    @Mark("其他应付款")
    var oth_payable: Double = Double.NaN

    @Mark("预提费用")
    var acc_exp: Double = Double.NaN

    @Mark("递延收益")
    var deferred_inc: Double = Double.NaN

    @Mark("应付短期债券")
    var st_bonds_payable: Double = Double.NaN

    @Mark("应付分保账款")
    var payable_to_reinsurer: Double = Double.NaN

    @Mark("保险合同准备金")
    var rsrv_insur_cont: Double = Double.NaN

    @Mark("代理买卖证券款")
    var acting_trading_sec: Double = Double.NaN

    @Mark("代理承销证券款")
    var acting_uw_sec: Double = Double.NaN

    @Mark("一年内到期的非流动负债")
    var non_cur_liab_due_1y: Double = Double.NaN

    @Mark("其他流动负债")
    var oth_cur_liab: Double = Double.NaN

    @Mark("流动负债合计")
    var total_cur_liab: Double = Double.NaN

    @Mark("应付债券")
    var bond_payable: Double = Double.NaN

    @Mark("长期应付款")
    var lt_payable: Double = Double.NaN

    @Mark("专项应付款")
    var specific_payables: Double = Double.NaN

    @Mark("预计负债")
    var estimated_liab: Double = Double.NaN

    @Mark("递延所得税负债")
    var defer_tax_liab: Double = Double.NaN

    @Mark("递延收益-非流动负债")
    var defer_inc_non_cur_liab: Double = Double.NaN

    @Mark("其他非流动负债")
    var oth_ncl: Double = Double.NaN

    @Mark("非流动负债合计")
    var total_ncl: Double = Double.NaN

    @Mark("同业和其它金融机构存放款项")
    var depos_oth_bfi: Double = Double.NaN

    @Mark("衍生金融负债")
    var deriv_liab: Double = Double.NaN

    @Mark("吸收存款")
    var depos: Double = Double.NaN

    @Mark("代理业务负债")
    var agency_bus_liab: Double = Double.NaN

    @Mark("其他负债")
    var oth_liab: Double = Double.NaN

    @Mark("预收保费")
    var prem_receiv_adva: Double = Double.NaN

    @Mark("存入保证金")
    var depos_received: Double = Double.NaN

    @Mark("保户储金及投资款")
    var ph_invest: Double = Double.NaN

    @Mark("未到期责任准备金")
    var reser_une_prem: Double = Double.NaN

    @Mark("未决赔款准备金")
    var reser_outstd_claims: Double = Double.NaN

    @Mark("寿险责任准备金")
    var reser_lins_liab: Double = Double.NaN

    @Mark("长期健康险责任准备金")
    var reser_lthins_liab: Double = Double.NaN

    @Mark("独立账户负债")
    var indept_acc_liab: Double = Double.NaN

    @Mark("其中:质押借款")
    var pledge_borr: Double = Double.NaN

    @Mark("应付赔付款")
    var indem_payable: Double = Double.NaN

    @Mark("应付保单红利")
    var policy_div_payable: Double = Double.NaN

    @Mark("负债合计")
    var total_liab: Double = Double.NaN

    @Mark("减:库存股")
    var treasury_share: Double = Double.NaN

    @Mark("一般风险准备")
    var ordin_risk_reser: Double = Double.NaN

    @Mark("外币报表折算差额")
    var forex_differ: Double = Double.NaN

    @Mark("未确认的投资损失")
    var invest_loss_unconf: Double = Double.NaN

    @Mark("少数股东权益")
    var minority_int: Double = Double.NaN

    @Mark("股东权益合计(不含少数股东权益)")
    var total_hldr_eqy_exc_min_int: Double = Double.NaN

    @Mark("股东权益合计(含少数股东权益)")
    var total_hldr_eqy_inc_min_int: Double = Double.NaN

    @Mark("负债及股东权益总计")
    var total_liab_hldr_eqy: Double = Double.NaN

    @Mark("长期应付职工薪酬")
    var lt_payroll_payable: Double = Double.NaN

    @Mark("其他综合收益")
    var oth_comp_income: Double = Double.NaN

    @Mark("其他权益工具")
    var oth_eqt_tools: Double = Double.NaN

    @Mark("其他权益工具(优先股)")
    var oth_eqt_tools_p_shr: Double = Double.NaN

    @Mark("融出资金")
    var lending_funds: Double = Double.NaN

    @Mark("应收款项")
    var acc_receivable: Double = Double.NaN

    @Mark("应付短期融资款")
    var st_fin_payable: Double = Double.NaN

    @Mark("应付款项")
    var payables: Double = Double.NaN

    @Mark("持有待售的资产")
    var hfs_assets: Double = Double.NaN

    @Mark("持有待售的负债")
    var hfs_sales: Double = Double.NaN

}