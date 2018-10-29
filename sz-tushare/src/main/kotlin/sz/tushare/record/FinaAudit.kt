package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/28.
//

/**
 * 财务审计意见
 * 参考: https://tushare.pro/document/2?doc_id=80
 */
class FinaAudit : RecordBase() {

    @Comment("TS股票代码")
    var ts_code: String = ""

    @Comment("公告日期")
    var ann_date: String = ""

    @Comment("报告期")
    var end_date: String = ""

    @Comment("审计结果")
    var audit_result: String = ""

    @Comment("审计总费用（元）")
    var audit_fees: Double = Double.NaN

    @Comment("会计事务所")
    var audit_agency: String = ""

    @Comment("签字会计师")
    var audit_sign: String = ""

}