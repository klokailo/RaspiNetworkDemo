package me.kai.networkdemo.recipient

import me.kai.networkdemo.packet.outbound.OutboundPacket

interface RecipientConnection {

    val recipientAddress: RecipientAddress

    fun sendPacket(packet: OutboundPacket)
    fun close()

}