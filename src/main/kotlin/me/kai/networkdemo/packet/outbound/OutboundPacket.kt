package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.Packet
import me.kai.networkdemo.packet.PacketHeader

interface OutboundPacket: Packet {

    val header get() = PacketHeader(id, Client.instance.clientAddress, body.size.toByte()).encoded
    val body: ByteArray

    fun send()

    // Only prints if enabled
    fun sendAndPrint() {
        send()
        print()
    }

}