package me.kai.networkdemo.packet.outbound

// I am sending some form of text message
abstract class MessageOutboundPacket(val message: String): OutboundPacket {

    private val messageBytes = message.toByteArray()

    override val type: Byte = 2

    init {
        if (messageBytes.size > Byte.MAX_VALUE) throw IllegalArgumentException("Message $message exceeds max length of ${Byte.MAX_VALUE} bytes!")
    }

    override val body = messageBytes
}