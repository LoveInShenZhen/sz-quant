package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/22.
//

/**
 * 指数成分和权重
 */
class IndexWeight : RecordBase() {

    @Comment("指数代码")
    var index_code = ""

    @Comment("成分代码")
    var con_code = ""

    @Comment("交易日期, YYYYMMDD")
    var trade_date = ""

    @Comment("权重")
    var weight = Double.NaN

}