package me.kai.networkdemo.packet.outbound

// I am announcing to the rest of the network"
class AnnounceOutboundPacket(message: String): MessageOutboundPacket(message), BroadcastOutboundPacket {

    override fun print() = println("[Outbound] Sent announce message packet \"$message\"")

}