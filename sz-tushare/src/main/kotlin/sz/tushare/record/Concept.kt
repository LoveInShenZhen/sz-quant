package sz.tushare.record

import sz.annotations.Mark

//
// Created by kk on 2018/11/1.
//

/**
 * 概念股分类
 * 参考: https://tushare.pro/document/2?doc_id=125
 */
class Concept : RecordBase() {

    @Mark("概念分类ID")
    var code: String = ""

    @Mark("概念分类名称")
    var name: String = ""

    @Mark("来源")
    var src: String = ""

}