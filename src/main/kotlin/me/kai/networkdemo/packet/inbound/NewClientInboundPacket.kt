package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.recipient.RecipientAddress
import java.io.DataInputStream
import java.net.InetAddress
import java.nio.ByteBuffer

class NewClientInboundPacket(val recipientAddress: RecipientAddress): InboundPacket {

    override val id: Byte = 1

    constructor(input: DataInputStream): this(Unit.run {
        RecipientAddress(
            InetAddress.getByAddress(byteArrayOf(input.readByte(), input.readByte(), input.readByte(), input.readByte())),
            ByteBuffer.wrap(byteArrayOf(input.readByte(), input.readByte())).short
        )
    })

    override fun act() {
        Client.instance.recipients.add(recipientAddress)
    }

    override fun print() = println("[Inbound] Established new recipient $recipientAddress")

}