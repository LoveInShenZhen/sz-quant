package sz.tushare.record

import sz.scaffold.tools.csv.CSV

//
// Created by kk on 2018/10/11.
//
object TsRecord {

    fun saveToFile(csvFilePath: String, beans: List<*>, clazz: Class<*>, append: Boolean = true) {
        CSV.saveToFile(csvFilePath, beans, clazz, append)
    }

    inline fun <reified T : Any> saveToFile(csvFilePath: String, beans: List<T>, append: Boolean = true) {
        CSV.saveToFile(csvFilePath, beans, T::class.java, append)
    }

    inline fun <reified T : Any> loadFromFile(csvFilePath: String): List<T> {
        return CSV.loadFromFile(csvFilePath)
    }
}