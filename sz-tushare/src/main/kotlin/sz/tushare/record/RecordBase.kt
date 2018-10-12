package sz.tushare.record

import sz.scaffold.annotations.Comment
import sz.scaffold.tools.BizLogicException
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*

//
// Created by kk on 2018/10/11.
//
open class RecordBase {

    fun fillData(fields: List<String>, values: List<Any?>) {
        val propMap = propMapOf(this.javaClass.kotlin)

        values.forEachIndexed { index, value ->
            val propName = fields[index]
            val prop = propMap[propName]!!
            val propValue = if (value == null) {
                val propType = prop.returnType
                if (propType.isMarkedNullable) {
                    // 属性字段标记为可以为 null
                    value
                } else {
                    // 属性字段不能够为 null
                    when {
                        propType.isSubtypeOf(String::class.createType()) -> ""
                        else -> throw BizLogicException("class: ${this.javaClass.name} property: ${prop.name} is marked not null, but get a null value")
                    }
                }
            } else {
                value
            }

            prop.setter.call(this, propValue)
        }
    }

    fun apiFields(): String {
        return this.javaClass.kotlin.declaredMemberProperties
                .filter { it.findAnnotation<Comment>() != null }.joinToString(",") { it.name }
    }

    companion object {

        private val typePropMap = mutableMapOf<KClass<*>, Map<String, KMutableProperty<*>>>()

        fun propMapOf(kclazz: KClass<*>): Map<String, KMutableProperty<*>> {
            return typePropMap.getOrPut(kclazz) {
                kclazz.memberProperties
                        .filter { it is KMutableProperty<*> }
                        .map { Pair(it.name, it as KMutableProperty<*>) }
                        .toMap()
            }
        }

    }
}