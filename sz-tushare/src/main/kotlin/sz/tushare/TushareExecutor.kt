package sz.tushare

import sz.scaffold.tools.logger.Logger
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

//
// Created by kk on 2018/10/31.
//
class TushareExecutor(private val limitPerMinute: Int = 80,
                      private val sleepTime: Long = 500,
                      private val interval: Long = 500) : Executor {

    private val taskQueue = LinkedBlockingDeque<Runnable>()
    private val counterService = Executors.newSingleThreadScheduledExecutor()
    private var counter = 0
    private var exceptionHandler: (Runnable, Exception) -> Unit = { _, ex ->
        Logger.debug(ex.localizedMessage)
    }

    private var enabled = true

    init {
        // 每15秒, 清一次计数
        counterService.scheduleAtFixedRate({
            counter = 0
        }, 15, 15, TimeUnit.SECONDS)

        object : Thread() {
            override fun run() {
                this.name = "TushareExecutor"
                while (enabled) {
                    if (counter < limitPerMinute / 4) {
                        val task = taskQueue.poll(100, TimeUnit.MICROSECONDS)
                        if (task != null) {
                            counter++
                            try {
                                task.run()
                                if (interval > 0) {
//                                    Logger.debug("Task was finished and the worker thread will go to sleep $interval ms")
                                    Thread.sleep(interval)
                                }
                            } catch (ex: Exception) {
                                exceptionHandler(task, ex)
                            }

                        }
                    } else {
                        Logger.debug("超过 $limitPerMinute 次/分钟 频率限制, seleep $sleepTime ms")
                        Thread.sleep(sleepTime)
                    }

                }
            }
        }.start()

    }

    override fun execute(task: Runnable) {
        taskQueue.offer(task)
    }

    fun whenException(handler: (Runnable, Exception) -> Unit): TushareExecutor {
        exceptionHandler = handler
        return this
    }

    fun close() {
        enabled = false
        counterService.shutdown()
    }

    companion object {

        val Singleton = TushareExecutor()

    }
}