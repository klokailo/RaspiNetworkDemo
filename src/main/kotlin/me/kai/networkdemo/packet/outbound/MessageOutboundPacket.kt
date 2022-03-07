package me.kai.networkdemo.packet.outbound

abstract class MessageOutboundPacket(val message: String): OutboundPacket {

    private val messageBytes = message.toByteArray()

    override val id: Byte = 2

    init {
        if (messageBytes.size > Byte.MAX_VALUE) throw IllegalArgumentException("Message $message exceeds max length of ${Byte.MAX_VALUE} bytes!")
    }

    override val body = messageBytes
}