package sz.morpheus

import com.zavtech.morpheus.frame.DataFrame

//
// Created by kk on 2019/9/12.
//

fun DataFrame<*, String>.printSchema() {
    println(this.schemaInfo())
}

fun DataFrame<*, String>.schemaInfo(): String {
    val sb = StringBuilder()
    sb.appendln(String.format("%-32s| %s", "column name", "type"))
    sb.appendln("-".repeat(80))
    this.cols().forEach { col ->
        sb.appendln(String.format("%-32s: %s", col.key(), col.typeInfo().simpleName))
    }
    sb.appendln("-".repeat(80))
    sb.appendln("total columns count: ${this.colCount()}")
    return sb.toString()
}