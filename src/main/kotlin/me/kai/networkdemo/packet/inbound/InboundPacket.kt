package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.EncodedPacket
import me.kai.networkdemo.packet.Packet

abstract class InboundPacket(encoded: EncodedPacket): Packet {

    val sender = encoded.header.sender

    abstract fun act()

}