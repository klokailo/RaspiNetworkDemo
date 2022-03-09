package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.PacketContents
import me.kai.networkdemo.packet.Packet

abstract class InboundPacket(contents: PacketContents): Packet {

    val sender = contents.header.sender

    abstract fun act()

}