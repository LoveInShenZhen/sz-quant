package sz.tushare.record

import sz.scaffold.annotations.Comment
import sz.scaffold.tools.BizLogicException
import sz.tushare.ResultPayload

//
// Created by kk on 2018/10/11.
//
// 参考: https://tushare.pro/document/2?doc_id=25

class StockBasicRecord : RecordBase() {

    @Comment("TS代码")
    var ts_code: String? = null

    @Comment("股票代码")
    var symbol: String? = null

    @Comment("股票名称")
    var name: String? = null

    @Comment("所在地域")
    var area: String? = null

    @Comment("所属行业")
    var industry: String? = null

    @Comment("股票全称")
    var fullname: String? = null

    @Comment("英文全称")
    var enname: String? = null

    @Comment("市场类型 (主板/中小板/创业板)")
    var market: String? = null

    @Comment("交易所代码")
    var exchange_id: String? = null

    @Comment("交易货币")
    var curr_type: String? = null

    @Comment("上市状态: L-上市 D-退市 P-暂停上市")
    var list_status: String? = null

    @Comment("上市日期")
    var list_date: String? = null

    @Comment("退市日期")
    var delist_date: String? = null

    @Comment("是否沪深港通标的: N-否 H-沪股通 S-深股通")
    var is_hs: String? = null

    companion object {

        fun buildFrom(resultPayload: ResultPayload): List<StockBasicRecord> {
            if (resultPayload.code != 0) {
                throw BizLogicException("result code: ${resultPayload.code}, msg: ${resultPayload.msg}")
            }

            return resultPayload.data.items.map { values ->
                val bean = StockBasicRecord()
                bean.fillData(resultPayload.data.fields, values)
                bean
            }
        }
    }
}