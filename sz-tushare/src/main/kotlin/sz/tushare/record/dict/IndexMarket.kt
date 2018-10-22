package sz.tushare.record.dict

//
// Created by kk on 2018/10/22.
//
enum class IndexMarket(val desc: String) {

    MSCI("MSCI指数"),
    CSI("中证指数"),
    SSE("上交所指数"),
    SZSE("深交所指数"),
    CICC("中金所指数"),
    SW("申万指数"),
    CNI("国证指数"),
    OTH("其他指数")
}