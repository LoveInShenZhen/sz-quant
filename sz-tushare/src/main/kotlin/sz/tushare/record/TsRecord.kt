package sz.tushare.record

import sz.scaffold.tools.csv.CSV

//
// Created by kk on 2018/10/11.
//
object TsRecord {

    fun saveToFile(csvFilePath: String, beans: List<*>, clazz: Class<*>) {
        CSV.saveToFile(csvFilePath, beans, clazz, true)
    }

    fun <T> loadFromFile(csvFilePath: String, clazz: Class<T>): List<T> {
        TODO()
    }
}