package me.kai.networkdemo.packet.outbound

// I am closing my connection and notifying the rest of the network
class ClientClosedOutboundPacket: BroadcastOutboundPacket {

    override val type: Byte = 0

    override val body = byteArrayOf() // Uses the sender in the header

    override fun print() = println("[Outbound] Sent client closed outbound packet to network")

}