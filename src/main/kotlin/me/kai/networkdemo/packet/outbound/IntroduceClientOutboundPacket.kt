package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.recipient.RecipientAddress

// Hello $recipient, please add $newClient to your recipients
class IntroduceClientOutboundPacket(val newClient: RecipientAddress, override val recipient: RecipientAddress): TargetedOutboundPacket {

    override val type: Byte = 1

    override val body = newClient.encoded

    override fun print() = println("[Outbound] Introduced new client $newClient to $recipient")
}