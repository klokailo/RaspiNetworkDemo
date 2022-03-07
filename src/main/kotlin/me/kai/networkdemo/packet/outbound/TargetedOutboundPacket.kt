package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.recipient.RecipientAddress

// I am sending a message targeted at one recipient
interface TargetedOutboundPacket: OutboundPacket {

    val recipient: RecipientAddress

    override fun send() {
        for (target in Client.instance.recipients) {
            if (target == recipient) {
                Client.instance.sendPacket(recipient, this)
                return
            }
        }
        throw IllegalArgumentException("Recipient $recipient for targeted outbound packet does not exist!")
    }

}