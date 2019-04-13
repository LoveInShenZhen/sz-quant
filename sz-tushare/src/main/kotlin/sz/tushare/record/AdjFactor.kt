package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/22.
//

/**
 * 复权因子
 * 参考: https://tushare.pro/document/2?doc_id=28
 */
class AdjFactor : RecordBase() {

    @Mark("股票代码")
    var ts_code = ""

    @Mark("交易日期, YYYYMMDD")
    var trade_date = ""

    @Mark("复权因子")
    var adj_factor = Double.NaN
}