package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.EncodedPacket

// The client $encoded.sender has sent me a message. I don't know if this is an announcement or a whisper
class MessageInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    val message = String(encoded.body)

    override val type: Byte = 2

    override fun act() {}

    override fun print() = println("[Inbound] Received from $sender message: $message")

}