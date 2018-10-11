package sz.tushare

import sz.scaffold.annotations.Comment
import sz.scaffold.tools.json.Json
import sz.scaffold.tools.logger.Logger
import sz.tushare.record.StockBasicRecord

//
// Created by kk on 2018/10/11.
//
object TushareApi {

    fun stockBasic(@Comment("是否沪深港通标的, N-否 H-沪股通 S-深股通") is_hs: String = "",
                   @Comment("上市状态: L-上市 D-退市 P-暂停上市") list_status: String = "",
                   @Comment("交易所 SSE上交所 SZSE深交所 HKEX港交所") exchange_id: String = ""): List<StockBasicRecord> {
        val api = ApiPayload()
        api.api_name = "stock_basic"
        api.addParam("is_hs", is_hs)
                .addParam("list_status", list_status)
                .addParam("exchange_id", exchange_id)

        api.fields = StockBasicRecord().apiFields()
        Logger.debug("api.fields = ${api.fields}")
        val result = api.send()

        val payload = Json.fromJsonString(result, ResultPayload::class.java)
        return StockBasicRecord.buildFrom(payload)
    }
}