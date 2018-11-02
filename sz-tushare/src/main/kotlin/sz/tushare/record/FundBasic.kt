package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//
class FundBasic : RecordBase() {

    @Comment("基金代码")
    var ts_code: String = ""

    @Comment("简称")
    var name: String = ""

    @Comment("管理人")
    var management: String = ""

    @Comment("托管人")
    var custodian: String = ""

    @Comment("投资类型")
    var fund_type: String = ""

    @Comment("成立日期")
    var found_date: String = ""

    @Comment("到期日期")
    var due_date: String = ""

    @Comment("上市时间")
    var list_date: String = ""

    @Comment("发行日期")
    var issue_date: String = ""

    @Comment("退市日期")
    var delist_date: String = ""

    @Comment("发行份额(亿)")
    var issue_amount: Double = Double.NaN

    @Comment("管理费")
    var m_fee: Double = Double.NaN

    @Comment("托管费")
    var c_fee: Double = Double.NaN

    @Comment("存续期")
    var duration_year: Double = Double.NaN

    @Comment("面值")
    var p_value: Double = Double.NaN

    @Comment("起点金额(万元)")
    var min_amount: Double = Double.NaN

    @Comment("预期收益率")
    var exp_return: Double = Double.NaN

    @Comment("业绩比较基准")
    var benchmark: String = ""

    @Comment("存续状态D摘牌 I发行 L已上市")
    var status: String = ""

    @Comment("投资风格")
    var invest_type: String = ""

    @Comment("基金类型")
    var type: String = ""

    @Comment("受托人")
    var trustee: String = ""

    @Comment("日常申购起始日")
    var purc_startdate: String = ""

    @Comment("日常赎回起始日")
    var redm_startdate: String = ""

    @Comment("E场内O场外")
    var market: String = ""
}