package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.EncodedPacket

// The client $encoded.sender has just told me they are closing, I'll remove them from my recipients
class ClientClosedInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    override val type: Byte = 0

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
            println("[Inbound] Recipient closed $sender")
        } else {
            println("[Inbound] [WARNING] Received recipient closed for invalid recipient $sender")
        }
    }

}