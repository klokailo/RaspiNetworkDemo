package me.kai.networkdemo.packet.outbound

class AnnounceOutboundPacket(message: String): MessageOutboundPacket(message), BroadcastOutboundPacket {

    override fun print() = println("[Outbound] Sending announce message packet \"$message\"")

}