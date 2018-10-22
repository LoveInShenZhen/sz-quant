package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/22.
//

/**
 * 停复牌信息
 * 参考: https://tushare.pro/document/2?doc_id=31
 */
class Suspend : RecordBase() {

    @Comment("股票代码")
    var ts_code = ""

    @Comment("停牌日期, YYYYMMDD")
    var suspend_date = ""

    @Comment("复牌日期, YYYYMMDD")
    var resume_date = ""

    @Comment("公告日期, YYYYMMDD")
    var ann_date = ""

    @Comment("停牌原因")
    var suspend_reason = ""

    @Comment("停牌原因类别")
    var reason_type = ""

}