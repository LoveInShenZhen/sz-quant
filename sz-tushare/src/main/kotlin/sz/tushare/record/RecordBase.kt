package sz.tushare.record

import jodd.util.ClassUtil
import sz.annotations.Mark
import sz.tushare.ResultPayload
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

//
// Created by kk on 2018/10/11.
//
open class RecordBase {

    fun fillData(fields: List<String>, values: List<Any?>) {
        val propMap = propMapOf(this.javaClass.kotlin)

        values.forEachIndexed { index, value ->
            val propName = fields[index]
            val prop = propMap[propName]!!
            val propValue = valueOfProp(prop, value)
            prop.setter.call(this, propValue)
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun valueOfProp(prop: KMutableProperty<*>, value: Any?): Any? {
        val propType = prop.returnType
        if (value == null) {
            // 处理值为 null 的情况
            return if (propType.isMarkedNullable) {
                // 属性字段标记为可以为 null, 则直接返回 null
                null
            } else {
                // 属性字段不能够为 null
                when {
                    // 如果属性字段类型是字符串, 则返回空字符串
                    propType.isSubtypeOf(String::class.createType()) -> ""
                    propType.isSubtypeOf(Double::class.createType()) -> Double.NaN
                    propType.isSubtypeOf(Float::class.createType()) -> Float.NaN
                    // 其他类型, 但是又没有标记为允许为空, 则抛出异常
                    else -> throw RuntimeException("class: ${this.javaClass.name} property: ${prop.name} is marked not null, but get a null value")
                }
            }
        } else {
            // value is not null

            // 需要处理 value 与 属性字段的类型不匹配的问题, case by case 的进行人工转换处理
            // 先判断 value 与 属性字段的类型是否一致 ( value 的实际类型, 必须与属性字段的类型一致, 或者是其类型的子类)
            if (value.javaClass.kotlin.isSubclassOf(propType.jvmErasure)) {
                // 类型兼容, 不用处理, 返回value
                return value
            } else {
                // 类型不兼容, 需要对 value 进行转换
                val propJavaClazz = propType.jvmErasure.java
                val convertedValue = when {
                    // 首先判断和处理基础类型
                    ClassUtil.isTypeOf(String::class.java, propJavaClazz) -> value.toString()
                    ClassUtil.isTypeOf(Long::class.java, propJavaClazz) -> value.toString().toLongOrNull()
                    ClassUtil.isTypeOf(Int::class.java, propJavaClazz) -> value.toString().toIntOrNull()

                    ClassUtil.isTypeOf(Double::class.java, propJavaClazz) -> {
                        val txtValue = value.toString()
                        if (txtValue.isBlank() || txtValue.toLowerCase() in setOf("null", "nan")) {
                            Double.NaN
                        } else {
                            txtValue.toDoubleOrNull()
                        }
                    }

                    ClassUtil.isTypeOf(Float::class.java, propJavaClazz) -> {
                        val txtValue = value.toString()
                        if (txtValue.isBlank() || txtValue.toLowerCase() in setOf("null", "nan")) {
                            Float.NaN
                        } else {
                            txtValue.toFloatOrNull()
                        }
                    }

                    ClassUtil.isTypeOf(Boolean::class.java, propJavaClazz) -> value.toString().toUpperCase() == "TRUE"

                    // 复杂类型
                    ClassUtil.isTypeOf(BigDecimal::class.java, propJavaClazz) -> {
                        val valueTxt = value.toString()
                        if (valueTxt.isBlank()) {
                            null
                        } else {
                            BigDecimal(valueTxt)
                        }
                    }

                    else -> throw RuntimeException("class: ${this.javaClass.name} 里, value: $value 的类型: ${value.javaClass.kotlin.simpleName} 与 property: ${prop.name} 的类型:${propType.jvmErasure.simpleName}不兼容")
                }

                if (convertedValue == null) {
                    if (propType.isMarkedNullable) {
                        // 属性允许为 null
                        return null
                    } else {
                        // 属性不允许为 null
                        throw RuntimeException("class: ${this.javaClass.name} property: ${prop.name} is marked not null, but get a null value: '$value'")
                    }
                } else {
                    return convertedValue
                }

            }
        }
    }

    fun apiFields(): String {
        return this.javaClass.kotlin.declaredMemberProperties
                .filter { it.findAnnotation<Mark>() != null }.joinToString(",") { it.name }
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

        inline fun <reified T : RecordBase> buildFrom(resultPayload: ResultPayload): List<T> {
            if (resultPayload.code != 0) {
                throw RuntimeException("result code: ${resultPayload.code}, msg: ${resultPayload.msg}")
            }

            return resultPayload.data!!.items.map { values ->
                val bean = T::class.java.newInstance()
                bean.fillData(resultPayload.data!!.fields, values)
                bean
            }
        }

        val undefinedDecimal = BigDecimal(Int.MIN_VALUE)


    }
}
