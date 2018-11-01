package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//

/**
 * 概念股列表
 * 参考: https://tushare.pro/document/2?doc_id=126
 */
class ConceptDetail : RecordBase() {

    @Comment("概念代码")
    var id: String = ""

    @Comment("股票代码")
    var ts_code: String = ""

    @Comment("股票名称")
    var name: String = ""

    @Comment("纳入日期")
    var in_date: String = ""

    @Comment("剔除日期")
    var out_date: String = ""

}