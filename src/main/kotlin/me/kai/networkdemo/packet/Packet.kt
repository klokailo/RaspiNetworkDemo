package me.kai.networkdemo.packet

interface Packet {

    val type: Byte

    fun print()

}