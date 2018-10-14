package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/14.
//

/**
 * 历史名称变更记录
 * 参考: https://tushare.pro/document/2?doc_id=100
 */
class NameChange : RecordBase() {

    @Comment("TS代码")
    var ts_code = ""

    @Comment("证券名称")
    var name = ""

    @Comment("开始日期")
    var start_date = ""

    @Comment("结束日期")
    var end_date = ""

    @Comment("公告日期")
    var ann_date = ""

    @Comment("变更原因")
    var change_reason = ""

}