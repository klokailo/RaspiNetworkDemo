package me.kai.networkdemo.packet.outbound

class ClientClosedOutboundPacket: BroadcastOutboundPacket {

    override val id: Byte = 0

    override val body = byteArrayOf() // Uses the sender in the header

    override fun print() = println("[Outbound] Sent client closed outbound packet to network")

}