package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//
class FundBasic : RecordBase() {

    @Mark("基金代码")
    var ts_code: String = ""

    @Mark("简称")
    var name: String = ""

    @Mark("管理人")
    var management: String = ""

    @Mark("托管人")
    var custodian: String = ""

    @Mark("投资类型")
    var fund_type: String = ""

    @Mark("成立日期")
    var found_date: String = ""

    @Mark("到期日期")
    var due_date: String = ""

    @Mark("上市时间")
    var list_date: String = ""

    @Mark("发行日期")
    var issue_date: String = ""

    @Mark("退市日期")
    var delist_date: String = ""

    @Mark("发行份额(亿)")
    var issue_amount: Double = Double.NaN

    @Mark("管理费")
    var m_fee: Double = Double.NaN

    @Mark("托管费")
    var c_fee: Double = Double.NaN

    @Mark("存续期")
    var duration_year: Double = Double.NaN

    @Mark("面值")
    var p_value: Double = Double.NaN

    @Mark("起点金额(万元)")
    var min_amount: Double = Double.NaN

    @Mark("预期收益率")
    var exp_return: Double = Double.NaN

    @Mark("业绩比较基准")
    var benchmark: String = ""

    @Mark("存续状态D摘牌 I发行 L已上市")
    var status: String = ""

    @Mark("投资风格")
    var invest_type: String = ""

    @Mark("基金类型")
    var type: String = ""

    @Mark("受托人")
    var trustee: String = ""

    @Mark("日常申购起始日")
    var purc_startdate: String = ""

    @Mark("日常赎回起始日")
    var redm_startdate: String = ""

    @Mark("E场内O场外")
    var market: String = ""
}