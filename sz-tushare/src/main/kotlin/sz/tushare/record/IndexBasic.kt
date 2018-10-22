package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/22.
//

/**
 * 指数基本信息
 * 参考: https://tushare.pro/document/2?doc_id=94
 */
class IndexBasic : RecordBase() {

    @Comment("TS代码")
    var ts_code = ""

    @Comment("简称")
    var name = ""

    @Comment("指数全称")
    var fullname = ""

    @Comment("市场")
    var market = ""

    @Comment("发布方")
    var publisher = ""

    @Comment("指数风格")
    var index_type = ""

    @Comment("指数类别")
    var category = ""

    @Comment("基期")
    var base_date = ""

    @Comment("基点")
    var base_point = Double.NaN

    @Comment("发布日期")
    var list_date = ""

    @Comment("加权方式")
    var weight_rule = ""

    @Comment("描述")
    var desc = ""

    @Comment("终止日期")
    var exp_date = ""

}