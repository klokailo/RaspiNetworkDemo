package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.EncodedPacket

class ResponseInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    override val id: Byte = 4

    override fun act() {}

    override fun print() {
        TODO()
    }

}