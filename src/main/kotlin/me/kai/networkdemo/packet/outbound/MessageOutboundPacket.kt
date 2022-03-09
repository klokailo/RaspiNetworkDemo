package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.packet.PacketType

// I am sending some form of text message
abstract class MessageOutboundPacket(val message: String): OutboundPacket {

    private val messageBytes = message.toByteArray()

    override val type = PacketType.MESSAGE

    init {
        if (messageBytes.size > Byte.MAX_VALUE) throw IllegalArgumentException("Message $message exceeds max length of ${Byte.MAX_VALUE} bytes!")
    }

    override val body = messageBytes
}