package sz.tushare.data

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.SparkSession
import sz.scaffold.tools.logger.Logger
import sz.tushare.data.subdbs.*
import sz.tushare.record.*
import java.io.File

//
// Created by kk on 2018/11/27.
//
class TuDateset(val options: TuDbOptions, val spark: SparkSession) {

    lateinit var stockBasicDS: Dataset<StockBasic>
    lateinit var tradeCalDS: Dataset<TradeCal>
    lateinit var stockCompanyDS: Dataset<StockCompany>
    lateinit var hsConstDS: Dataset<HsConst>
    lateinit var indexBasicDS: Dataset<IndexBasic>
    lateinit var indexWeightDS: Dataset<IndexWeight>
    lateinit var hsgtTop10DS: Dataset<HsgtTop10>
    lateinit var topListDS: Dataset<TopList>
    lateinit var hs300DS: Dataset<IndexWeight>

    private val adjFactorDsMap = mutableMapOf<String, Dataset<AdjFactor>>()
    private val dailyBasicDsMap = mutableMapOf<String, Dataset<DailyBasic>>()

    fun loadDataset(): TuDateset {
        stockBasicDS = loadDataSet(StockBasicDB(options))
        tradeCalDS = loadDataSet(TradeCalDB(options))
        stockCompanyDS = loadDataSet(StockCompanyDB(options))
        hsConstDS = loadDataSet(HSConstDB(options))
        indexBasicDS = loadDataSet(IndexBasicDB(options))
        indexWeightDS = loadDataSet(IndexWeightDB(options))
        hsgtTop10DS = loadDataSet(HsgtTop10DB(options))
        topListDS = loadDataSet(TopListDB(options))

        return this
    }

    fun adjFactorDSof(tsCode: String): Dataset<AdjFactor> {
        return adjFactorDsMap.getOrPut(tsCode) {
            loadDataSet(subdb = AdjFactorDB(dbOptions = options,
                    ts_code = tsCode),
                    prefix = "tu_${tsCode}_")
        }
    }

    fun dailyBasicDSof(tsCode: String): Dataset<DailyBasic> {
        return dailyBasicDsMap.getOrPut(tsCode) {
            loadDataSet(subdb = DailyBasicDB(dbOptions = options,
                    ts_code = tsCode),
                    prefix = "tu_${tsCode}_")
        }
    }

    private inline fun <reified T : Any> loadDataSet(subdb: IDbFolder, prefix: String = "tu_", createTempView: Boolean = true): Dataset<T> {
        val ds = spark.read().format("CSV")
                .option("path", subdb.folder().absolutePath)
                .option("header", "true")
                .option("inferSchema", "true")
                .load()
                .`as`(Encoders.bean(T::class.java))

        if (createTempView) {
            ds.createTempView("$prefix${subdb.folder().name}")
        }
        Logger.debug("load data from folder: ${subdb.folder().absolutePath}")
        return ds
    }

    private inline fun <reified T : Any> loadDataSet(dir: String, createTempView: Boolean = true): Dataset<T> {
        val folder = File(dir)
        val ds = spark.read().format("CSV")
                .option("path", folder.absolutePath)
                .option("header", "true")
                .option("inferSchema", "true")
                .load()
                .`as`(Encoders.bean(T::class.java))

        if (createTempView) {
            ds.createTempView("tu_${folder.name}")
        }
        Logger.debug("load data from folder: ${folder.absolutePath}")
        return ds
    }

}