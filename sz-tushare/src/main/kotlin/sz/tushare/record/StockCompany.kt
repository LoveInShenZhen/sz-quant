package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/15.
//
class StockCompany : RecordBase() {

    @Comment("股票代码")
    var ts_code: String = ""

    @Comment("交易所代码 ，SSE上交所 SZSE深交所")
    var exchange: String = ""

    @Comment("法人代表")
    var chairman: String = ""

    @Comment("总经理")
    var manager: String = ""

    @Comment("董秘")
    var secretary: String = ""

    @Comment("注册资本")
    var reg_capital: Double = Double.NaN

    @Comment("注册日期")
    var setup_date: String = ""

    @Comment("所在省份")
    var province: String = ""

    @Comment("所在城市")
    var city: String = ""

    @Comment("公司介绍")
    var introduction: String = ""

    @Comment("公司主页")
    var website: String = ""

    @Comment("电子邮件")
    var email: String = ""

    @Comment("办公室")
    var office: String = ""

    @Comment("员工人数")
    var employees: Int? = null

    @Comment("主要业务及产品")
    var main_business: String = ""

    @Comment("经营范围")
    var business_scope: String = ""
}