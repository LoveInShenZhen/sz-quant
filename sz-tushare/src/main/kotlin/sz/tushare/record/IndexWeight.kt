package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/22.
//

/**
 * 指数成分和权重
 */
class IndexWeight : RecordBase() {

    @Mark("指数代码")
    var index_code = ""

    @Mark("成分代码")
    var con_code = ""

    @Mark("交易日期, YYYYMMDD")
    var trade_date = ""

    @Mark("权重")
    var weight = Double.NaN

}