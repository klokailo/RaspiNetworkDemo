package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.packet.Packet

interface InboundPacket: Packet {

    fun act()

}