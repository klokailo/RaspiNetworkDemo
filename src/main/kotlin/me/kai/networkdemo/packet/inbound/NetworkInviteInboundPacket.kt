package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.InboundPacketContents
import me.kai.networkdemo.packet.PacketType
import me.kai.networkdemo.packet.outbound.IntroduceClientOutboundPacket
import me.kai.networkdemo.recipient.RecipientAddress

// The client $encoded.sender has invited me to join their network, and listed those who are already on it.
// I will first notify all of the recipients already connected to me about all the other recipients I am discovering
// I will also notify all of the new recipients I am discovering about the recipients that are already connected to me
// Finally, I will add all of the new recipients to my list
class NetworkInviteInboundPacket(contents: InboundPacketContents): InboundPacket(contents) {

    val recipients = HashSet<RecipientAddress>()

    init {
        if (contents.body.size % 6 != 0) throw IllegalArgumentException("Cannot parse NetworkInviteInboundPacket from list of recipients with size ${contents.body.size}!")
        for (i in 0 until contents.body.size step 6) recipients.add(RecipientAddress(contents.body.copyOfRange(i, i + 6)))
    }

    override val type = PacketType.NETWORK_INVITE

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
            if (recipient != sender) println("[Inbound] Received from $sender address of new recipient on network: $recipient")
        }
    }

}