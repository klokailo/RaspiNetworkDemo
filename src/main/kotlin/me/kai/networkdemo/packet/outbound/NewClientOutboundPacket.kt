package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.recipient.RecipientAddress

class NewClientOutboundPacket(val newClient: RecipientAddress): BroadcastOutboundPacket {

    override val id: Byte = 1

    override fun encode() = newClient.encoded

    override fun print() = println("[Outbound] Introducing new client $newClient to network")

}