package me.kai.networkdemo.recipient

import me.kai.networkdemo.packet.outbound.OutboundPacket
import java.io.DataOutputStream
import java.net.Socket

class SingleRecipientConnection(override val recipientAddress: RecipientAddress): RecipientConnection {

    private val recipientSocket = Socket(recipientAddress.ip, recipientAddress.signedPort)
    private val outStream = DataOutputStream(recipientSocket.getOutputStream())

    override fun sendPacket(packet: OutboundPacket) = outStream.write(packet.encode())

    override fun close() {
        outStream.close()
        recipientSocket.close()
    }

}