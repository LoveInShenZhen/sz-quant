package sz.futu.protocol

import com.google.protobuf.AbstractMessage
import io.vertx.core.buffer.Buffer
import org.apache.commons.codec.digest.DigestUtils

//
// Created by kk on 2018/11/10.
//
class FutuMessage constructor(val header: FutuProtoHeader, val body: AbstractMessage) {

    private val bodyBytes: ByteArray = body.toByteArray()

    init {
        header.arrBodySHA1 = DigestUtils.sha1(bodyBytes)
        header.nBodyLen = bodyBytes.size
    }

    fun toBuffer(): Buffer {
        val buf = Buffer.buffer()
        buf.appendBuffer(header.encodeToBuf())
        buf.appendBytes(bodyBytes)

        return buf
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendln("-------- Header --------------------------------")
        sb.appendln(this.header.toString())
        sb.appendln("-------- Body ----------")
        sb.appendln(this.body.toString())
        sb.appendln("------------------------------------------------")

        return sb.toString()
    }

    companion object {

        private const val MAX_MESSAGE_ID = 65535
        private var serialNo = 0

        private fun nextSerialNo(): Int {
            return synchronized(serialNo) {
                // if 0 or MAX_MESSAGE_ID, it becomes 1 (first valid messageId)
                serialNo = if ((serialNo % MAX_MESSAGE_ID) != 0) {
                    serialNo + 1
                } else {
                    1
                }

                serialNo
            }
        }

        fun newMessage(body: AbstractMessage): FutuMessage {
            val header = FutuProtoHeader()
            header.nProtoID = ProtocolMap.protoIdOfMessage(body.javaClass.kotlin)
            header.nSerialNo = nextSerialNo()

            return FutuMessage(header, body)
        }

    }
}