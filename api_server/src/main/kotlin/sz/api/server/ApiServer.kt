@file:JvmName("ApiServer")

package sz.api.server

import sz.SzEbeanConfig
import sz.scaffold.Application
import sz.scaffold.tools.logger.Logger

//
// Created by kk on 17/8/29.
//
object ApiServer {

    @JvmStatic
    fun main(args: Array<String>) {
        SzEbeanConfig.loadConfig()

        Application.setupVertx()

        Application.runHttpServer()

    }

}
