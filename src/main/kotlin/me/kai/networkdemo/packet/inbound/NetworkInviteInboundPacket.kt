package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.EncodedPacket
import me.kai.networkdemo.packet.outbound.IntroduceClientOutboundPacket
import me.kai.networkdemo.recipient.RecipientAddress

class NetworkInviteInboundPacket(encoded: EncodedPacket): InboundPacket(encoded) {

    val recipients = HashSet<RecipientAddress>()

    init {
        if (encoded.body.size % 6 != 0) throw IllegalArgumentException("Cannot parse NetworkInviteInboundPacket from list of recipients with size ${encoded.body.size}!")
        for (i in 0 until encoded.body.size / 6) {
            val byteLocation = i * 6
            recipients.add(RecipientAddress(encoded.body.copyOfRange(byteLocation, byteLocation + 6)))
        }
    }

    override val id: Byte = 3

    override fun act() {
        // For joining two connected networks together
        for (connectedRecipient in Client.instance.recipients) {
            for (newRecipient in recipients) {
                // Notify the new recipients we are connecting to about our already known ones
                IntroduceClientOutboundPacket(connectedRecipient, newRecipient).sendAndPrint()
                // Notify our already known recipients about the new ones
                IntroduceClientOutboundPacket(newRecipient, connectedRecipient).sendAndPrint()
            }
        }
        for (recipient in recipients) Client.instance.recipients.add(recipient)
    }

    override fun print() {
        println("[Inbound] Received invite to new network packet from $sender")
        for (recipient in recipients) {
            println("[Inbound] Received from $sender address of new recipient on network: $recipient")
        }
    }

}