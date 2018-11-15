package sz.futu.client

import io.netty.channel.ChannelPipeline
import io.vertx.core.Vertx
import io.vertx.core.impl.NetSocketInternal
import io.vertx.core.net.NetClient
import io.vertx.core.net.NetClientOptions
import jodd.exception.ExceptionUtil
import sz.futu.protocol.FutuMessage
import sz.futu.protocol.InitConnect
import sz.futu.protocol.KeepAlive
import sz.scaffold.Application
import sz.scaffold.tools.logger.Logger
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

//
// Created by kk on 2018/11/9.
//
class FutuClient(val vertx: Vertx, val ip: String, val port: Int, options: NetClientOptions) {

    private val tcpClient: NetClient = vertx.createNetClient(options)

    private var netSocket: NetSocketInternal? = null
    val connection: NetSocketInternal
        get() {
            if (netSocket == null) {
                throw RuntimeException("client尚未连接到服务器")
            }
            return netSocket!!
        }

    /**
     * tcp连接成功后, 并且等InitConnect协议先完成后, connectionReadyFuture 才为 true
     */
    private val connectionReadyFuture = CompletableFuture<Boolean>()
    private var connectionReady = false

    private var keepAliveTimer: Long = -1
    private var connectionProps = InitConnect.S2C.newBuilder().defaultInstanceForType

    private val clientVer = Application.config.getInt("futu.clientVer")
    private val clientID = Application.config.getString("futu.clientID")
    private val recvNotify = Application.config.getBoolean("futu.recvNotify")

    private var closeHandler: () -> Unit = {}
    private var exceptionHandler: (Throwable) -> Unit = {
        Logger.debug("发生异常: ${it.localizedMessage}\n关闭连接")
        Logger.debug(ExceptionUtil.exceptionStackTraceToString(it))
        this.close()
    }

    private var receiveKeepAliveHandler: (KeepAlive.Response) -> Unit = {}
    private var messageHandler: (FutuMessage) -> Unit = {}
    private val pendingMessageHandler = ConcurrentHashMap<Int, CompletableFuture<FutuMessage>>()

    fun connect(): Boolean {
        tcpClient.connect(port, ip) { res ->
            if (res.succeeded()) {
                netSocket = res.result() as NetSocketInternal
                Logger.debug("Connected! [${connection.javaClass.name}]")

                initChannel(netSocket!!.channelHandlerContext().pipeline())

                connection.closeHandler {
                    connectionReady = false
                    if (keepAliveTimer > -1) {
                        vertx.cancelTimer(keepAliveTimer)
                    }
                    closeHandler()
                }.exceptionHandler {
                    // 异常发生后就把连接关闭
                    this.close()
                    exceptionHandler(it)
                }

                connection.messageHandler { msg ->
                    if (msg is FutuMessage) {
                        // TODO: sha1校验
                        when (msg.header.nProtoID) {
                            1004 -> receiveKeepAliveHandler(msg.body as KeepAlive.Response)
                            1001 -> receivedInitConnectMsg(msg)
                            else -> {
                                val pendingHandler = pendingMessageHandler.getOrDefault(msg.header.nSerialNo, null)
                                if (pendingHandler != null) {
                                    pendingHandler.complete(msg)
                                } else {
                                    messageHandler(msg)
                                }
                            }
                        }
                    } else {
                        throw Exception("Wrong message type: ${msg.javaClass.name}")
                    }
                }

                // TODO: 初始化其他 handler

                sendInitConnect()
            } else {
                Logger.debug("Failed to connect: ${res.cause().localizedMessage}")
                connectionReadyFuture.complete(false)
            }
        }

        return connectionReadyFuture.get()
    }

    fun close() {
        tcpClient.close()
    }

    fun whenClosed(handler: () -> Unit): FutuClient {
        this.closeHandler = handler
        return this
    }

    fun whenReceiveKeepAlive(handler: (KeepAlive.Response) -> Unit): FutuClient {
        this.receiveKeepAliveHandler = handler
        return this
    }

    fun whenReceiveMessage(handler: (FutuMessage) -> Unit): FutuClient {
        this.messageHandler = handler
        return this
    }

    private fun initChannel(pipeLine: ChannelPipeline) {
        // TODO: 实现 ChannelPipeline 的初始化, 添加handler
        pipeLine.addBefore("handler", "mqttDecoder", FutuDecoder())
    }

    /**
     * # 1001初始化连接
     *
     * * 请求其它协议前必须等InitConnect协议先完成
     * * 若FutuOpenD配置了加密， “connAESKey”将用于后续协议加密
     * * keepAliveInterval 为建议client发起心跳 KeepAlive 的间隔
     */
    private fun sendInitConnect() {
        val c2s = InitConnect.C2S.newBuilder()
                .setClientID(this.clientID)
                .setClientVer(this.clientVer)
                .setRecvNotify(this.recvNotify)
                .build()

        val req = InitConnect.Request.newBuilder()
                .setC2S(c2s)
                .build()

        val msg = FutuMessage.newMessage(req)

        this.connection.write(msg.toBuffer())
    }

    private fun sendKeepAlive() {
        val c2s = KeepAlive.C2S.newBuilder()
                .setTime(System.currentTimeMillis() / 1000)
        val req = KeepAlive.Request.newBuilder()
                .setC2S(c2s)
                .build()

        val msg = FutuMessage.newMessage(req)

        sendMsg(msg)
    }

    private fun receivedInitConnectMsg(msg: FutuMessage) {
        val body = msg.body as InitConnect.Response
        Logger.debug("收到 InitConnect message\n$msg")
        // TODO: keepAlive 定时器
        this.connectionProps = body.s2C

        connectionReadyFuture.complete(true)
        connectionReady = true

        this.keepAliveTimer = vertx.setPeriodic(connectionProps.keepAliveInterval * 1000L) {
            sendKeepAlive()
        }
        Logger.debug("keepAliveTimer: $keepAliveTimer")
    }

    fun sendMsg(message: FutuMessage) {
        if (connectionReady.not()) {
            Logger.debug("连接未就绪")
            throw RuntimeException("连接未就绪")
        }
        // log 输出排除掉 KeepAlive 消息
        if (message.header.nProtoID != 1004) {
            Logger.debug("send message:\n$message")
        }
        this.connection.write(message.toBuffer())
    }

    fun sendRequest(reqMessage: FutuMessage, timeOutInMs: Long = 60000): CompletableFuture<FutuMessage> {
        val future = CompletableFuture<FutuMessage>()
        pendingMessageHandler[reqMessage.header.nSerialNo] = future
        sendMsg(reqMessage)
        vertx.setTimer(timeOutInMs) {
            pendingMessageHandler.remove(reqMessage.header.nSerialNo)
            Logger.debug("Remove pendingMessageHandler for nSerialNo: ${reqMessage.header.nSerialNo}")
        }
        return future
    }
}