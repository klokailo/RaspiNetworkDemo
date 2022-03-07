package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.EncodedPacket

class MessageInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    val message = String(encoded.body)

    override val id: Byte = 2

    override fun act() {}

    override fun print() = println("[Inbound] Message received from $sender: $message")

}