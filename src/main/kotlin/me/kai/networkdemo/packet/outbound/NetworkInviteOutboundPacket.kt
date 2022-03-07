package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.recipient.RecipientAddress

// Hello $recipient, welcome to the network. The existing recipients already on the network are $existingRecipients
class NetworkInviteOutboundPacket(override val recipient: RecipientAddress, val existingRecipients: HashSet<RecipientAddress>): TargetedOutboundPacket {

    // Add all connected recipients
    constructor(recipient: RecipientAddress): this(recipient, HashSet<RecipientAddress>().apply {
        for (target in Client.instance.recipients) add(target)
        add(Client.instance.clientAddress)
        remove(recipient)
    })

    override val type: Byte = 3

    override val body = run {
        val bytes = ByteArray(6 * existingRecipients.size)
        var index = 0
        for (recipient in existingRecipients) for (byte in recipient.encoded) bytes[index++] = byte
        bytes
    }

    override fun print() = println("[Outbound] Invited $recipient to join the network")

}