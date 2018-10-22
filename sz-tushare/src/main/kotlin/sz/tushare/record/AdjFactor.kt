package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/22.
//

/**
 * 复权因子
 * 参考: https://tushare.pro/document/2?doc_id=28
 */
class AdjFactor : RecordBase() {

    @Comment("股票代码")
    var ts_code = ""

    @Comment("交易日期, YYYYMMDD")
    var trade_date = ""

    @Comment("复权因子")
    var adj_factor = Double.NaN

    @Comment("开始日期, YYYYMMDD")
    var start_date = ""

    @Comment("结束日期, YYYYMMDD")
    var end_date = ""
}