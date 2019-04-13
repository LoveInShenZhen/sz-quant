package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/22.
//

/**
 * 指数基本信息
 * 参考: https://tushare.pro/document/2?doc_id=94
 */
class IndexBasic : RecordBase() {

    @Mark("TS代码")
    var ts_code = ""

    @Mark("简称")
    var name = ""

    @Mark("指数全称")
    var fullname = ""

    @Mark("市场")
    var market = ""

    @Mark("发布方")
    var publisher = ""

    @Mark("指数风格")
    var index_type = ""

    @Mark("指数类别")
    var category = ""

    @Mark("基期")
    var base_date = ""

    @Mark("基点")
    var base_point = Double.NaN

    @Mark("发布日期")
    var list_date = ""

    @Mark("加权方式")
    var weight_rule = ""

    @Mark("描述")
    var desc = ""

    @Mark("终止日期")
    var exp_date = ""

}