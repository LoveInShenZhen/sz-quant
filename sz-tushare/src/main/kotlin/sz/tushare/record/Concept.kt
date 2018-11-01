package sz.tushare.record

import sz.scaffold.annotations.Comment

//
// Created by kk on 2018/11/1.
//

/**
 * 概念股分类
 * 参考: https://tushare.pro/document/2?doc_id=125
 */
class Concept : RecordBase() {

    @Comment("概念分类ID")
    var code: String = ""

    @Comment("概念分类名称")
    var name: String = ""

    @Comment("来源")
    var src: String = ""

}