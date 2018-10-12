package sz.tushare.record

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import sz.scaffold.annotations.Comment
import sz.scaffold.tools.BizLogicException
import sz.tushare.ResultPayload

//
// Created by kk on 2018/10/11.
//
// 参考: https://tushare.pro/document/2?doc_id=25

class StockBasicRecord : RecordBase() {

    @Comment("TS代码")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var ts_code: String = ""

    @Comment("股票代码")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var symbol: String = ""

    @Comment("股票名称")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var name: String = ""

    @Comment("所在地域")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var area: String = ""

    @Comment("所属行业")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var industry: String = ""

    @Comment("股票全称")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var fullname: String = ""

    @Comment("英文全称")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var enname: String = ""

    @Comment("市场类型 (主板/中小板/创业板)")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var market: String = ""

    @Comment("交易所代码")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var exchange_id: String = ""

    @Comment("交易货币")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var curr_type: String = ""

    @Comment("上市状态: L-上市 D-退市 P-暂停上市")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var list_status: String = ""

    @Comment("上市日期")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var list_date: String = ""

    @Comment("退市日期")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var delist_date: String = ""

    @Comment("是否沪深港通标的: N-否 H-沪股通 S-深股通")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    var is_hs: String = ""

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