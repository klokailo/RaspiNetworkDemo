package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.PacketResponse

class ResponseInboundPacket(val response: PacketResponse): InboundPacket {

    override val id: Byte = 3

    override fun act() {}

    override fun print() {
        TODO()
    }

}