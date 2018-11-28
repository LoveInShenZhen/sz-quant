@file:JvmName("CmdApp")

package sz.quant.spark

import jodd.io.findfile.FindFile
import org.apache.spark.api.java.function.ForeachFunction
import org.apache.spark.ml.linalg.VectorUDT
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.stat.Correlation
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.Row
import org.apache.spark.sql.RowFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.*
import sz.scaffold.Application
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.logger.Logger
import sz.tushare.TushareExecutor
import sz.tushare.data.TuDateset
import sz.tushare.data.TuDbOptions
import java.io.Serializable


// Created by kk on 17/8/29.
//
object CmdApp {

    private val spark = SparkSession.builder()
//            .master("spark://192.168.3.3:7077")
            .master("local[4]")
            .appName("KK").orCreate!!

    @JvmStatic
    fun main(args: Array<String>) {
        Application.setupVertx()

        indexStock()
//        runDatasetCreationExample()

        spark.stop()
    }

    fun test() {

        val finder = FindFile.createWildcardFF()
                .include("*.hs_const.csv")
                .matchOnlyFileName()
                .searchPath("/Volumes/USBDATA/tushare_data/hs_const")

        val dataFiles = finder.findAll().map { "file://${it.absolutePath}" }

        Logger.debug(dataFiles.toJsonPretty())

        Logger.debug("0000000000000000000")
        val df = spark.read()
                .option("header", "true") // Use first line of all files as header
                .option("inferSchema", "true") // Automatically infer data types
                .csv("file:///Volumes/USBDATA/tushare_data/hs_const/")
        Logger.debug("111111111111111111111111")
        df.show()
        Logger.debug("222222222222222222")
        df.printSchema()
        Logger.debug("333333333333333333")
    }

    fun runDatasetCreationExample() {
        val person = Person(name = "Andy", age = 32)

        val personEncoder = Encoders.bean(person.javaClass)

        val path = "/Users/kk/work/tmp/spark/examples/src/main/resources/people.json"

        val peopleDS = spark.read()
                .json(path).`as`(personEncoder)

        peopleDS.show()

        peopleDS.printSchema()

        peopleDS.createOrReplaceTempView("people")

        val df = spark.sql("select name as NAME from people where age > 19")

        df.show()
    }

    fun correlationTest() {
        val data = listOf<Row>(
                RowFactory.create(Vectors.sparse(4, intArrayOf(0, 3), doubleArrayOf(1.0, -2.0))),
                RowFactory.create(Vectors.dense(4.0, 5.0, 0.0, 3.0)),
                RowFactory.create(Vectors.dense(6.0, 7.0, 0.0, 8.0)),
                RowFactory.create(Vectors.sparse(4, intArrayOf(0, 3), doubleArrayOf(9.0, 1.0)))
        )

        val schema = StructType()
                .add("features", VectorUDT()) //StructType(arrayOf(StructField("features", VectorUDT(), false, Metadata.empty())))

        val df = spark.createDataFrame(data, schema)
        val r1 = Correlation.corr(df, "features", "pearson").head()
        Logger.debug("Pearson correlation matrix:\n" + r1.get(0).toString())

        val r2 = Correlation.corr(df, "features", "spearman").head()
        Logger.debug("Spearman correlation matrix:\n" + r2.get(0).toString())
    }

    fun constStocks() {
        val dfHsConst = spark.read()
                .format("CSV")
                .option("path", "/Users/kk/work/tushare_data/hs_const")
                .option("header", "true") // Use first line of all files as header
                .option("inferSchema", "true") // Automatically infer data types
                .load()

        dfHsConst.createTempView("hs_const")

        val dfStockBasic = spark.read()
                .format("CSV")
                .option("path", "/Users/kk/work/tushare_data/stock_basic")
                .option("header", "true")
                .option("inferSchema", "true")
                .load()

        dfStockBasic.createTempView("stock_basic")

        val df = spark.sql("select A.ts_code, B.name from hs_const as A left join stock_basic as B on A.ts_code = B.ts_code")
        df.show()
    }

    fun indexStock() {
        val options = TuDbOptions(dbPath = "/Volumes/USBDATA/tushare_data",
                executor = TushareExecutor.Singleton)
        val tuDS = TuDateset(options, spark).loadDataset()
        tuDS.stockCompanyDS.show()
    }

}

data class Person(var name: String, var age: Int) : Serializable
