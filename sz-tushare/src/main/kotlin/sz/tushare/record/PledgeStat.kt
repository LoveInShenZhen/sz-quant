package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//

/**
 * 股权质押统计数据
 * 参考: https://tushare.pro/document/2?doc_id=110
 */
class PledgeStat : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("截至日期")
    var end_date: String = ""

    @Comment("质押次数")
    var pledge_count: Int = -1

    @Comment("无限售股质押数量（万）")
    var unrest_pledge: Double = Double.NaN

    @Comment("限售股份质押数量（万）")
    var rest_pledge: Double = Double.NaN

    @Comment("总股本")
    var total_share: Double = Double.NaN

    @Comment("质押比例")
    var pledge_ratio: Double = Double.NaN

}