package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/10/11.
//
// 参考: https://tushare.pro/document/2?doc_id=25

class StockBasic : RecordBase() {

    @Mark("TS代码")
    var ts_code: String = ""

    @Mark("股票代码")
    var symbol: String = ""

    @Mark("股票名称")
    var name: String = ""

    @Mark("所在地域")
    var area: String = ""

    @Mark("所属行业")
    var industry: String = ""

    @Mark("股票全称")
    var fullname: String = ""

    @Mark("英文全称")
    var enname: String = ""

    @Mark("市场类型 (主板/中小板/创业板)")
    var market: String = ""

    @Mark("交易所代码")
    var exchange: String = ""

    @Mark("交易货币")
    var curr_type: String = ""

    @Mark("上市状态: L-上市 D-退市 P-暂停上市")
    var list_status: String = ""

    @Mark("上市日期")
    var list_date: String = ""

    @Mark("退市日期")
    var delist_date: String = ""

    @Mark("是否沪深港通标的: N-否 H-沪股通 S-深股通")
    var is_hs: String = ""
}