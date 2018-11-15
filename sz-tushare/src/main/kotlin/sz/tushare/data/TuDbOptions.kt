package sz.tushare.data

import sz.tushare.TushareExecutor
import java.io.File

//
// Created by kk on 2018/11/15.
//

/**
 * TuShare 数据的管理器的配置, 负责下载,存储,本地查询,按需异步下载,定时更新等等
 *
 * @param dbPath: TuShare 本地数据保存的文件目录路径
 * @param executor: 执行下载数据请求的执行器. 因为TuShare对数据接口的调用有频率的限制, 大概是 80次/分钟
 */
class TuDbOptions(val dbPath: String, val executor: TushareExecutor) {

    fun dbFolder() : File {
        return File(dbPath)
    }
}