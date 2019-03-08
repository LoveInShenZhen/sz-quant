package sz.charts.echarts

//
// Created by kk on 2018-12-21.
//
data class Dimension(var type: String = "", var name: String = "", var displayName: String = "") {

    companion object {

        val TYPE_NUMBER = "number"
        val TYPE_ORDINAL = "ordinal"
        val TYPE_FLOAT = "float"
        val TYPE_INT = "int"
        val TYPE_TIME = "time"
    }
}