package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//

/**
 * 公募基金公司
 * 参考: https://tushare.pro/document/2?doc_id=118
 */
class FundCompany : RecordBase() {

    @Mark("基金公司名称")
    var name: String = ""

    @Mark("简称")
    var shortname: String = ""

    @Mark("英文缩写")
    var short_enname: String = ""

    @Mark("省份")
    var province: String = ""

    @Mark("城市")
    var city: String = ""

    @Mark("注册地址")
    var address: String = ""

    @Mark("电话")
    var phone: String = ""

    @Mark("办公地址")
    var office: String = ""

    @Mark("公司网址")
    var website: String = ""

    @Mark("法人代表")
    var chairman: String = ""

    @Mark("总经理")
    var manager: String = ""

    @Mark("注册资本")
    var reg_capital: Double = Double.NaN

    @Mark("成立日期")
    var setup_date: String = ""

    @Mark("公司终止日期")
    var end_date: String = ""

    @Mark("员工总数")
    var employees: Double = Double.NaN

    @Mark("主要产品及业务")
    var main_business: String = ""

    @Mark("组织机构代码")
    var org_code: String = ""

    @Mark("统一社会信用代码")
    var credit_code: String = ""
}