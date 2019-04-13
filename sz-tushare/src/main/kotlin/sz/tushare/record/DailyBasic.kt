package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/22.
//

/**
 * 每日指标
 * 获取全部股票每日重要的基本面指标，可用于选股分析、报表展示等。
 * 参考: https://tushare.pro/document/2?doc_id=32
 */
class DailyBasic : RecordBase() {

    @Mark("股票代码")
    var ts_code = ""

    @Mark("交易日期, YYYYMMDD")
    var trade_date = ""

    @Mark("当日收盘价")
    var close = Double.NaN

    @Mark("换手率")
    var turnover_rate = Double.NaN

    @Mark("换手率（自由流通股）")
    var turnover_rate_f = Double.NaN

    @Mark("量比")
    var volume_ratio = Double.NaN

    @Mark("市盈率（总市值/净利润）")
    var pe = Double.NaN

    @Mark("市盈率（TTM）")
    var pe_ttm = Double.NaN

    @Mark("市净率（总市值/净资产）")
    var pb = Double.NaN

    @Mark("市销率")
    var ps = Double.NaN

    @Mark("市销率（TTM）")
    var ps_ttm = Double.NaN

    @Mark("总股本 （万）")
    var total_share = Double.NaN

    @Mark("流通股本 （万）")
    var float_share = Double.NaN

    @Mark("自由流通股本 （万）")
    var free_share = Double.NaN

    @Mark("总市值 （万元）")
    var total_mv = Double.NaN

    @Mark("流通市值（万元）")
    var circ_mv = Double.NaN
}