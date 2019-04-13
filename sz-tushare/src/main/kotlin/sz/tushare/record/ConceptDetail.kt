package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//

/**
 * 概念股列表
 * 参考: https://tushare.pro/document/2?doc_id=126
 */
class ConceptDetail : RecordBase() {

    @Mark("概念代码")
    var id: String = ""

    @Mark("股票代码")
    var ts_code: String = ""

    @Mark("股票名称")
    var name: String = ""

    @Mark("纳入日期")
    var in_date: String = ""

    @Mark("剔除日期")
    var out_date: String = ""

}