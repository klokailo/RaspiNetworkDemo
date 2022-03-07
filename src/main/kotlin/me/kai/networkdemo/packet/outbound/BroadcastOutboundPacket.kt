package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client

// I am broadcasting to the rest of the network
interface BroadcastOutboundPacket: OutboundPacket {

    override fun send() = Client.instance.broadcastPacket(this)

}