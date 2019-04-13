package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//

/**
 * 股权质押统计数据
 * 参考: https://tushare.pro/document/2?doc_id=110
 */
class PledgeStat : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("截至日期")
    var end_date: String = ""

    @Mark("质押次数")
    var pledge_count: Int = -1

    @Mark("无限售股质押数量（万）")
    var unrest_pledge: Double = Double.NaN

    @Mark("限售股份质押数量（万）")
    var rest_pledge: Double = Double.NaN

    @Mark("总股本")
    var total_share: Double = Double.NaN

    @Mark("质押比例")
    var pledge_ratio: Double = Double.NaN

}