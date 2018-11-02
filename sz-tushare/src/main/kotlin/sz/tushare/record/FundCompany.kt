package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//

/**
 * 公募基金公司
 * 参考: https://tushare.pro/document/2?doc_id=118
 */
class FundCompany : RecordBase() {

    @Comment("基金公司名称")
    var name: String = ""

    @Comment("简称")
    var shortname: String = ""

    @Comment("英文缩写")
    var short_enname: String = ""

    @Comment("省份")
    var province: String = ""

    @Comment("城市")
    var city: String = ""

    @Comment("注册地址")
    var address: String = ""

    @Comment("电话")
    var phone: String = ""

    @Comment("办公地址")
    var office: String = ""

    @Comment("公司网址")
    var website: String = ""

    @Comment("法人代表")
    var chairman: String = ""

    @Comment("总经理")
    var manager: String = ""

    @Comment("注册资本")
    var reg_capital: Double = Double.NaN

    @Comment("成立日期")
    var setup_date: String = ""

    @Comment("公司终止日期")
    var end_date: String = ""

    @Comment("员工总数")
    var employees: Double = Double.NaN

    @Comment("主要产品及业务")
    var main_business: String = ""

    @Comment("组织机构代码")
    var org_code: String = ""

    @Comment("统一社会信用代码")
    var credit_code: String = ""
}