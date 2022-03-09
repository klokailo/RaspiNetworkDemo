package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.PacketContents
import me.kai.networkdemo.packet.PacketType

// The client $encoded.sender has just told me they are closing, I'll remove them from my recipients
class ClientClosedInboundPacket(contents: PacketContents): InboundPacket(contents) {

    override val type = PacketType.CLIENT_CLOSED

    private var succeeded = false

    override fun act() {
        for (target in Client.instance.recipients) {
            if (target == sender) {
                Client.instance.recipients.remove(sender)
                succeeded = true
                return
            }
        }
    }

    override fun print() {
        if (succeeded) {
            println("[Inbound] Received recipient closed packet: $sender")
        } else {
            println("[Inbound] [WARNING] Received recipient closed for invalid recipient $sender")
        }
    }

}