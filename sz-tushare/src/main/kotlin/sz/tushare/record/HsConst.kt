package sz.tushare.record

import com.fasterxml.jackson.annotation.JsonProperty
import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/14.
//

/**
 * 沪深股通成份股
 * 参考: https://tushare.pro/document/2?doc_id=104
 */
class HsConst : RecordBase() {

    @Comment("TS代码")
    var ts_code = ""

    @Comment("沪深港通类型: SH-沪 SZ-深")
    var hs_type = ""

    @Comment("纳入日期, YYYYMMDD")
    var in_date = ""

    @Comment("剔除日期, YYYYMMDD")
    var out_date = ""

    @Comment("是否最新 1是0否")
    @JsonProperty("is_new")
    var is_new = ""

}