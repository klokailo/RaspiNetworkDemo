package me.kai.networkdemo.packet.outbound

abstract class MessageOutboundPacket(val message: String): OutboundPacket {

    override val id: Byte = 2

    // Format: sender-ip (4), sender-port (2), length (1), message (length)
    private val encoded by lazy {
        with(message.toByteArray()) {
            if (size > Byte.MAX_VALUE) throw throw IllegalArgumentException("Message $message exceeds max length of ${Byte.MAX_VALUE} bytes!")
            val bytes = ByteArray(size + 7)
            var index = 0
            bytes[index++] = size.toByte()
            for (byte in this) bytes[index++] = byte
            bytes
        }
    }

    override fun encode() = encoded

}