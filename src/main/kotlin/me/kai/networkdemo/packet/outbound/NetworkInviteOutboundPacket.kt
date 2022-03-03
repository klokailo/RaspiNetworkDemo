package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.recipient.RecipientAddress

class NetworkInviteOutboundPacket(override val recipient: RecipientAddress, val existingRecipients: HashSet<RecipientAddress>): TargetedOutboundPacket {

    // Add all connected recipients
    constructor(recipient: RecipientAddress): this(recipient, HashSet<RecipientAddress>().apply {
        for (target in Client.instance.recipients) add(target)
        add(Client.instance.clientAddress)
        remove(recipient)
    })

    override val id: Byte = 3

    override fun encode(): ByteArray {
        // Format: Number of clients (1b), ip #1 (4b), port #1 (2b), ip #2 (4b), port #2 (2b)
        val bytes = ByteArray(6 * existingRecipients.size + 1)
        var index = 0
        bytes[index++] = existingRecipients.size.toByte()
        for (recipient in existingRecipients) for (byte in recipient.encoded) bytes[index++] = byte
        return bytes
    }

    override fun print() = println("[Outbound] Invited $recipient to join the network")

}