package sz.tushare.record

import sz.scaffold.tools.csv.CSV
import java.io.File

//
// Created by kk on 2018/10/11.
//
object TsRecord {

    fun saveToFile(csvFilePath: String, beans: List<*>, clazz: Class<*>, append: Boolean = false) {
        CSV.saveToFile(File(csvFilePath), beans, clazz, append)
    }

    fun saveToFile(csvFile: File, beans: List<*>, clazz: Class<*>, append: Boolean = false) {
        CSV.saveToFile(csvFile, beans, clazz, append)
    }

    inline fun <reified T : Any> saveToFile(csvFilePath: String, beans: List<T>, append: Boolean = false) {
        CSV.saveToFile(File(csvFilePath), beans, T::class.java, append)
    }

    inline fun <reified T : Any> saveToFile(csvFile: File, beans: List<T>, append: Boolean = false) {
        CSV.saveToFile(csvFile, beans, T::class.java, append)
    }

    inline fun <reified T : Any> loadFromFile(csvFilePath: String): List<T> {
        return CSV.loadFromFile(csvFilePath)
    }

    inline fun <reified T : Any> loadFromFile(csvFile: File): List<T> {
        return CSV.loadFromFile(csvFile)
    }
}