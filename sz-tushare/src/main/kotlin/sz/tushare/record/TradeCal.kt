package sz.tushare.record

import com.fasterxml.jackson.annotation.JsonProperty
import sz.annotations.Mark

//
// Created by kk on 2018/10/12.
//

/**
 * 交易日历
 * 参考: https://tushare.pro/document/2?doc_id=26
 */
class TradeCal : RecordBase() {

    @Mark("交易所: SSE-上交所 SZSE-深交所")
    var exchange = ""

    @Mark("日历日期")
    var cal_date = ""

    @Mark("是否交易 0-休市 1-交易")
    @JsonProperty(value = "is_open")
    var is_open: Int = -1  // -1 表示未定义

    @Mark("上一个交易日")
    var pretrade_date = ""
}