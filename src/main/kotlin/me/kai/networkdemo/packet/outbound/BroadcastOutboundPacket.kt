package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client

interface BroadcastOutboundPacket: OutboundPacket {

    override fun send() = Client.instance.broadcastPacket(this)

}