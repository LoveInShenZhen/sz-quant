package sz.tushare.record

import jodd.bean.BeanUtil
import sz.scaffold.annotations.Comment
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

//
// Created by kk on 2018/10/11.
//
open class RecordBase {

    fun fillData(fields: List<String>, values: List<Any>) {
        values.forEachIndexed { index, value ->
            val fieldName = fields[index]
            BeanUtil.declaredForced.setProperty(this, fieldName, value)
        }
    }

    fun apiFields(): String {
        return this.javaClass.kotlin.declaredMemberProperties
                .filter { it.findAnnotation<Comment>() != null }.joinToString(",") { it.name }
    }

}