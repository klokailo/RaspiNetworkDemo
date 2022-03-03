package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.recipient.RecipientAddress

class IntroduceClientOutboundPacket(val newClient: RecipientAddress, override val recipient: RecipientAddress): TargetedOutboundPacket {

    override val id: Byte = 1

    override fun encode() = newClient.encoded

    override fun print() = println("[Outbound] Introducing new client $newClient to $recipient")
}