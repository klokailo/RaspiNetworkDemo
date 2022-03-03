package me.kai.networkdemo.packet.inbound

import java.io.DataInputStream

class MessageInboundPacket(val message: String): InboundPacket {

    constructor(input: DataInputStream): this(Unit.run {
        val size = input.readByte()
        val stringBytes = ByteArray(size.toInt())
        for (i in 0 until size) stringBytes[i] = input.readByte()
        String(stringBytes)
    })

    override val id: Byte = 2

    override fun act() {}

    override fun print() = println("[Inbound] Message received: $message")

}