package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.EncodedPacket

// The client $encoded.sender has notified me of their response to TODO, I will respond accordingly
class ResponseInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    override val type: Byte = 4

    override fun act() {}

    override fun print() {
        TODO()
    }

}