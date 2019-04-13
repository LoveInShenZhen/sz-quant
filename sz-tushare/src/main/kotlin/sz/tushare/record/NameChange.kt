package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/14.
//

/**
 * 历史名称变更记录
 * 参考: https://tushare.pro/document/2?doc_id=100
 */
class NameChange : RecordBase() {

    @Mark("TS代码")
    var ts_code = ""

    @Mark("证券名称")
    var name = ""

    @Mark("开始日期")
    var start_date = ""

    @Mark("结束日期")
    var end_date = ""

    @Mark("公告日期")
    var ann_date = ""

    @Mark("变更原因")
    var change_reason = ""

}