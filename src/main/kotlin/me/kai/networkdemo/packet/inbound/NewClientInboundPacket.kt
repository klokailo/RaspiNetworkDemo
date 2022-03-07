package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.EncodedPacket
import me.kai.networkdemo.recipient.RecipientAddress

class NewClientInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    override val id: Byte = 1

    init {
        if (encoded.body.size != 6) throw IllegalArgumentException("Cannot parse NewClientInboundPacket from packet with body size ${encoded.body.size}, should be 6.")
    }

    val recipientAddress = RecipientAddress(encoded.body)

    override fun act() {
        Client.instance.recipients.add(recipientAddress)
    }

    override fun print() = println("[Inbound] Received from $sender address of established new recipient $recipientAddress")

}