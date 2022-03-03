package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.packet.Packet

interface OutboundPacket: Packet {

    fun encode(): ByteArray

    fun send()

    // Only prints if enabled
    fun sendAndPrint() {
        send()
        print()
    }

}