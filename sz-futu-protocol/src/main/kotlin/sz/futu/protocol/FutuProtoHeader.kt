package sz.futu.protocol

import io.vertx.core.buffer.Buffer
import jodd.util.StringUtil

//
// Created by kk on 2018/11/10.
//

/**
 * 议数据包括协议头以及协议体，协议头固定字段，协议体根据具体协议决定。
 * 参考: https://futunnopen.github.io/futuquant/protocol/intro.html
 *
 *  struct APIProtoHeader
 *  {
 *      u8_t szHeaderFlag[2];        // 2 Bytes
 *      u32_t nProtoID;              // 4 Bytes
 *      u8_t nProtoFmtType;          // 1 Bytes
 *      u8_t nProtoVer;              // 1 Bytes
 *      u32_t nSerialNo;             // 4 Bytes
 *      u32_t nBodyLen;              // 4 Bytes
 *      u8_t arrBodySHA1[20];        // 20 Bytes
 *      u8_t arrReserved[8];         // 8 Bytes
 *  };
 *  头部总计: 44 Bytes
 *
 *     字段	            说明
 *     --------------------------------------------------------------
 *     szHeaderFlag	    包头起始标志，固定为“FT”
 *     nProtoID	        协议ID
 *     nProtoFmtType	协议格式类型，0为Protobuf格式，1为Json格式
 *     nProtoVer	    协议版本，用于迭代兼容, 目前填0
 *     nSerialNo	    包序列号，用于对应请求包和回包, 要求递增
 *     nBodyLen	        包体长度
 *     arrBodySHA1	    包体原始数据(解密后)的SHA1哈希值
 *     arrReserved	    保留8字节扩展
 *
 *  u8_t表示8位无符号整数，u32_t表示32位无符号整数
 *  arrBodySHA1用于校验请求数据在网络传输前后的一致性，必须正确填入
 *  协议头的二进制流使用的是 小端字节序 ，即一般不需要使用ntohl等相关函数转换数据
 */
class FutuProtoHeader {

    var szHeaderFlag = "FT"
    var nProtoID = 0
    var nProtoFmtType = 0
    var nProtoVer = 0
    var nSerialNo: Int = 0
    var nBodyLen: Int = 0
    var arrBodySHA1 = ByteArray(20)
    var arrReserved = ByteArray(8)

    val sha1: String
        get() {
            return StringUtil.toHexString(arrBodySHA1)
        }

    fun encodeToBuf(): Buffer {
        val buf = Buffer.buffer(44)
        buf.appendBytes(szHeaderFlag.map { it.toByte() }.toByteArray())
        buf.appendUnsignedIntLE(nProtoID.toLong())
        buf.appendByte(nProtoFmtType.toByte())
        buf.appendByte(nProtoVer.toByte())
        buf.appendUnsignedIntLE(nSerialNo.toLong())
        buf.appendUnsignedIntLE(nBodyLen.toLong())
        buf.appendBytes(arrBodySHA1)
        buf.appendBytes(arrReserved)

        return buf
    }

    fun decodeFromBuf(buf: Buffer): FutuProtoHeader {
        szHeaderFlag = buf.getBytes(0, 2).toString(Charsets.UTF_8)  // 2 Bytes
        nProtoID = buf.getUnsignedIntLE(2).toInt()                  // 4 Bytes
        nProtoFmtType = buf.getByte(6).toInt()                      // 1 Byte
        nProtoVer = buf.getByte(7).toInt()                          // 1 Byte
        nSerialNo = buf.getUnsignedIntLE(8).toInt()                 // 4 Bytes
        nBodyLen = buf.getUnsignedIntLE(12).toInt()                 // 4 Bytes
        arrBodySHA1 = buf.getBytes(16, 36)                          // 20 Bytes
        arrReserved = buf.getBytes(36, 44)                          // 8 Bytes

        return this
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendln("{")
        sb.appendln("   szHeaderFlag        : $szHeaderFlag")
        sb.appendln("   nProtoID            : $nProtoID")
        sb.appendln("   nProtoFmtType       : $nProtoFmtType")
        sb.appendln("   nProtoVer           : $nProtoVer")
        sb.appendln("   nSerialNo           : $nSerialNo")
        sb.appendln("   nBodyLen            : $nBodyLen")
        sb.appendln("   arrBodySHA1         : ${StringUtil.toHexString(arrBodySHA1)}")
        sb.appendln("   arrReserved         : ${StringUtil.toHexString(arrReserved)}")
        sb.appendln("}")

        return sb.toString()
    }

    companion object {

        val headerLength = 44

        fun decode(buf: Buffer): FutuProtoHeader {
            val header = FutuProtoHeader()
            return header.decodeFromBuf(buf)
        }
    }
}