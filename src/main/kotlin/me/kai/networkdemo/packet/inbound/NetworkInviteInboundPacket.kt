package me.kai.networkdemo.packet.inbound

import me.kai.networkdemo.Client
import me.kai.networkdemo.packet.outbound.IntroduceClientOutboundPacket
import me.kai.networkdemo.packet.outbound.NewClientOutboundPacket
import me.kai.networkdemo.recipient.RecipientAddress
import java.io.DataInputStream
import java.net.InetAddress
import java.nio.ByteBuffer

class NetworkInviteInboundPacket(val recipients: Set<RecipientAddress>): InboundPacket {

    constructor(input: DataInputStream): this(Unit.run {
        val size = input.readByte().toInt()
        val recipients = HashSet<RecipientAddress>()
        for (i in 0 until size) {
            val ip = InetAddress.getByAddress(byteArrayOf(input.readByte(), input.readByte(), input.readByte(), input.readByte()))
            val unsignedPort = ByteBuffer.wrap(byteArrayOf(input.readByte(), input.readByte())).short
            val address = RecipientAddress(ip, unsignedPort)
            recipients.add(address)
        }
        recipients
    })

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
        println("[Inbound] Received invite to new network packet")
        for (recipient in recipients) {
            println("[Inbound] New recipient on network: $recipient")
        }
    }

}