package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/29.
//
class MarginDetail : RecordBase() {

    @Mark("交易日期")
    var trade_date: String = ""

    @Mark("交易所代码（SSE上交所SZSE深交所）")
    var exchange_id: String = ""

    @Mark("融资余额(元)")
    var rzye: Double = Double.NaN

    @Mark("融资买入额(元)")
    var rzmre: Double = Double.NaN

    @Mark("融资偿还额(元)")
    var rzche: Double = Double.NaN

    @Mark("融券余额(元)")
    var rqye: Double = Double.NaN

    @Mark("融券卖出量(股,份,手)")
    var rqmcl: Double = Double.NaN

    @Mark("融资融券余额(元)")
    var rzrqye: Double = Double.NaN

}