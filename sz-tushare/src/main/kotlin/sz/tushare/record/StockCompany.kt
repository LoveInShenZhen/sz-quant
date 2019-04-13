package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/15.
//
class StockCompany : RecordBase() {

    @Mark("股票代码")
    var ts_code: String = ""

    @Mark("交易所代码 ，SSE上交所 SZSE深交所")
    var exchange: String = ""

    @Mark("法人代表")
    var chairman: String = ""

    @Mark("总经理")
    var manager: String = ""

    @Mark("董秘")
    var secretary: String = ""

    @Mark("注册资本")
    var reg_capital: Double = Double.NaN

    @Mark("注册日期")
    var setup_date: String = ""

    @Mark("所在省份")
    var province: String = ""

    @Mark("所在城市")
    var city: String = ""

    @Mark("公司介绍")
    var introduction: String = ""

    @Mark("公司主页")
    var website: String = ""

    @Mark("电子邮件")
    var email: String = ""

    @Mark("办公室")
    var office: String = ""

    @Mark("员工人数")
    var employees: Int? = null

    @Mark("主要业务及产品")
    var main_business: String = ""

    @Mark("经营范围")
    var business_scope: String = ""
}