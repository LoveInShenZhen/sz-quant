package sz.tushare

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import sz.scaffold.Application
import sz.scaffold.tools.json.Json
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.logger.Logger
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
        Logger.debug("post json:\n${this.toJsonPretty()}")
        val future = CompletableFuture<String>()
        val request = client.postAbs(apiUrl) { response ->
            response.bodyHandler { buf ->
                future.complete(buf.toString(Charsets.UTF_8))
            }
        }
        val buf = Buffer.buffer(this.json(), Charsets.UTF_8.name())
        request.putHeader("Content-Type", "application/json; charset=utf-8")
                .putHeader("Content-Length", buf.length().toString())
                .write(buf)
                .end()

        return future
    }

    companion object {
        val apiUrl: String
            get() = Application.config.getString("sz.tushare.url")

        val apiToken: String
            get() = Application.config.getString("sz.tushare.token")

        val client = Vertx.vertx().createHttpClient()
    }
}

class ResultPayload {

    var code = 0

    var msg: String? = null

    var data = DataPart()
}

class DataPart {
    var fields = listOf<String>()

    var items = listOf<List<Any?>>()
}
