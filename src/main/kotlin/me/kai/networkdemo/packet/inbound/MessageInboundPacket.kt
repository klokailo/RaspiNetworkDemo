package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.PacketContents
import me.kai.networkdemo.packet.PacketType

// The client $encoded.sender has sent me a message. I don't know if this is an announcement or a whisper
class MessageInboundPacket(contents: PacketContents): InboundPacket(contents) {

    val message = String(contents.body)

    override val type = PacketType.MESSAGE

    override fun act() {}

    override fun print() = println("[Inbound] Received from $sender message: $message")

}