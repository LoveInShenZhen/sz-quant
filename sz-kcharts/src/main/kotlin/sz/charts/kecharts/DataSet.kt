package sz.charts.kecharts

/**
 * http://www.echartsjs.com/option.html#dataset
 *
 * 数据组织形式, 我采用 __按列的 key-value 形式，每一项表示二维表的 "一列"__
 */
class DataSet {

    var source = mutableMapOf<String, Array<*>>()

    var dimensions = mutableListOf<Dimension>()
}