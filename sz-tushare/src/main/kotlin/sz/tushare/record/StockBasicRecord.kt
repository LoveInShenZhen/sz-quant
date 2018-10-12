package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/10/11.
//
// 参考: https://tushare.pro/document/2?doc_id=25

class StockBasicRecord : RecordBase() {

    @Comment("TS代码")
    var ts_code: String = ""

    @Comment("股票代码")
    var symbol: String = ""

    @Comment("股票名称")
    var name: String = ""

    @Comment("所在地域")
    var area: String = ""

    @Comment("所属行业")
    var industry: String = ""

    @Comment("股票全称")
    var fullname: String = ""

    @Comment("英文全称")
    var enname: String = ""

    @Comment("市场类型 (主板/中小板/创业板)")
    var market: String = ""

    @Comment("交易所代码")
    var exchange_id: String = ""

    @Comment("交易货币")
    var curr_type: String = ""

    @Comment("上市状态: L-上市 D-退市 P-暂停上市")
    var list_status: String = ""

    @Comment("上市日期")
    var list_date: String = ""

    @Comment("退市日期")
    var delist_date: String = ""

    @Comment("是否沪深港通标的: N-否 H-沪股通 S-深股通")
    var is_hs: String = ""
}