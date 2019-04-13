package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/28.
//

/**
 * 财务审计意见
 * 参考: https://tushare.pro/document/2?doc_id=80
 */
class FinaAudit : RecordBase() {

    @Mark("TS股票代码")
    var ts_code: String = ""

    @Mark("公告日期")
    var ann_date: String = ""

    @Mark("报告期")
    var end_date: String = ""

    @Mark("审计结果")
    var audit_result: String = ""

    @Mark("审计总费用（元）")
    var audit_fees: Double = Double.NaN

    @Mark("会计事务所")
    var audit_agency: String = ""

    @Mark("签字会计师")
    var audit_sign: String = ""

}