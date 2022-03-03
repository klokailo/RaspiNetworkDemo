package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client

class ClientClosedOutboundPacket: BroadcastOutboundPacket {

    override val id: Byte = 0

    override fun encode() = Client.instance.clientAddress.encoded

    override fun print() = println("[Outbound] Sent client closed outbound packet to network")

}