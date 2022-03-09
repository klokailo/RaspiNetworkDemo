package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.InboundPacketContents
import me.kai.networkdemo.packet.PacketType
import me.kai.networkdemo.recipient.RecipientAddress

// The client $encoded.sender has just notified me about a new client $recipientAddress on the network, I'll add it to my list
class NewClientInboundPacket(contents: InboundPacketContents): InboundPacket(contents) {

    override val type = PacketType.NEW_CLIENT

    init {
        if (contents.body.size != 6) throw IllegalArgumentException("Cannot parse NewClientInboundPacket from packet with body size ${contents.body.size}, should be 6.")
    }

    val recipientAddress = RecipientAddress(contents.body)

    override fun act() {
        Client.instance.recipients.add(recipientAddress)
    }

    override fun print() = println("[Inbound] Received from $sender address of established new recipient $recipientAddress")

}