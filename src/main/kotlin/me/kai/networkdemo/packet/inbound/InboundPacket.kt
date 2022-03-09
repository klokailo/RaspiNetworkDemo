package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.InboundPacketContents
import me.kai.networkdemo.packet.Packet

abstract class InboundPacket(contents: InboundPacketContents): Packet {

    val sender = contents.header.sender

    abstract fun act()

}