package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.recipient.RecipientAddress
import java.io.DataInputStream
import java.net.InetAddress
import java.nio.ByteBuffer

class ClientClosedInboundPacket(val recipient: RecipientAddress): InboundPacket {

    constructor(input: DataInputStream): this(RecipientAddress(
        InetAddress.getByAddress(byteArrayOf(input.readByte(), input.readByte(), input.readByte(), input.readByte())),
        ByteBuffer.wrap(byteArrayOf(input.readByte(), input.readByte())).short
    ))

    override val id: Byte = 0

    private var succeeded = false

    override fun act() {
        for (target in Client.instance.recipients) {
            if (target == recipient) {
                Client.instance.recipients.remove(recipient)
                succeeded = true
                return
            }
        }
    }

    override fun print() {
        if (succeeded) {
            println("[Inbound] Recipient closed $recipient")
        } else {
            println("[Inbound] [WARNING] Received recipient closed for invalid recipient $recipient")
        }
    }

}