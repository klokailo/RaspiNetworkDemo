package me.kai.networkdemo.packet

interface Packet {

    val type: PacketType

    fun print()

}