package sz.futu.client

import com.google.protobuf.AbstractMessage
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ReplayingDecoder
import io.vertx.core.buffer.Buffer
import jodd.util.StringUtil
import org.apache.commons.codec.digest.DigestUtils
import sz.futu.protocol.FutuMessage
import sz.futu.protocol.FutuProtoHeader
import sz.futu.protocol.ProtocolMap
import sz.scaffold.tools.logger.Logger

//
// Created by kk on 2018/11/11.
//
class FutuDecoder : ReplayingDecoder<FutuDecoder.DecoderState>(DecoderState.READ_FIXED_HEADER) {

    enum class DecoderState {
        READ_FIXED_HEADER,
        READ_BODY
    }

    private var currentHeader : FutuProtoHeader? = null

    override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
        when (state()) {
            DecoderState.READ_FIXED_HEADER -> {
                if (byteBuf.readableBytes() < FutuProtoHeader.headerLength) {
                    return
                }

                val buf = Buffer.buffer(byteBuf.readBytes(FutuProtoHeader.headerLength))
                currentHeader = FutuProtoHeader.decode(buf)
                checkpoint(DecoderState.READ_BODY)
            }

            DecoderState.READ_BODY -> {
                if (byteBuf.readableBytes() < currentHeader!!.nBodyLen) {
                    return
                }

                val bodyBytes = ByteArray(currentHeader!!.nBodyLen)
                byteBuf.readBytes(bodyBytes)
                val sha1 = StringUtil.toHexString(DigestUtils.sha1(bodyBytes))
                if (sha1 != currentHeader!!.sha1) {
                    Logger.debug("消息 sha1 校验不通过:\n$currentHeader")
                    throw Error("消息 sha1 校验不通过")
                }

                val bodyObj = ProtocolMap.responseParserById(currentHeader!!.nProtoID).parseFrom(bodyBytes) as AbstractMessage
                out.add(FutuMessage(currentHeader!!, bodyObj))
                currentHeader = null
                checkpoint(DecoderState.READ_FIXED_HEADER)
            }

            else -> throw Error("不应该执行到此处")
        }
    }


}