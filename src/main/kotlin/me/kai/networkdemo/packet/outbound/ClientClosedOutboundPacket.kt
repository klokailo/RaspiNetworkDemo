package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.packet.PacketType

// I am closing my connection and notifying the rest of the network
class ClientClosedOutboundPacket: BroadcastOutboundPacket {

    override val type = PacketType.CLIENT_CLOSED

    override val body = byteArrayOf() // Uses the sender in the header

    override fun print() = println("[Outbound] Sent client closed outbound packet to network")

}