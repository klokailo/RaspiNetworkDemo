package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.InboundPacketContents
import me.kai.networkdemo.packet.PacketType

// The client $encoded.sender has notified me of their response to TODO, I will respond accordingly
class ResponseInboundPacket(contents: InboundPacketContents): InboundPacket(contents) {

    override val type = PacketType.RESPONSE

    override fun act() {}

    override fun print() {
        TODO()
    }

}