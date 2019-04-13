package sz.tushare

import com.typesafe.config.ConfigFactory
import jodd.http.HttpRequest
import sz.scaffold.tools.json.Json
import java.io.File
import java.util.concurrent.CompletableFuture

//
// Created by kk on 2018/10/10.
//

// 参考: https://tushare.pro/document/1?doc_id=40

class ApiPayload {

    // 接口名称
    var api_name = ""

    // 用于识别唯一用户的标识
    var token = ""

    // 接口参数，如daily接口中start_date和end_date
    var params = mutableMapOf<String, Any>()

    // 字段列表，用于接口获取指定的字段，以逗号分隔，如"open,high,low,close"
    var fields = ""

    init {
        token = apiToken
    }

    fun addParam(paramName: String, paramValue: Any): ApiPayload {
        params.put(paramName, paramValue)
        return this
    }

    fun json(): String {
        return Json.toJsonStr(this)
    }

    fun send(): String {
        val future = sendAsync()
        return future.get()
    }

    fun sendAsync(): CompletableFuture<String> {
//        Logger.debug("post json:\n${this.toJsonPretty()}")
        return HttpRequest.post(apiUrl)
                .header("Content-Type", "application/json; charset=utf-8")
                .body(this.json())
                .sendAsync()
                .thenApply { response ->
                    response.bodyText()
                }
    }

    companion object {
        init {
            if (File("conf/application.conf").exists()) {
                System.setProperty("config.file", "conf/application.conf")
            }
        }

        val config = ConfigFactory.load()
        val apiUrl: String
            get() = config.getString("sz.tushare.url")

        val apiToken: String
            get() = config.getString("sz.tushare.token")
    }
}

class ResultPayload {

    var code = 0

    var msg: String? = null

    var data: DataPart? = null

    var request_id: String? = null
}

class DataPart {
    var fields = listOf<String>()

    var items = listOf<List<Any?>>()
}
