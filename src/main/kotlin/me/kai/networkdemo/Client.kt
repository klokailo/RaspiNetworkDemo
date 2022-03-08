package me.kai.networkdemo

import me.kai.networkdemo.packet.outbound.BroadcastOutboundPacket
import me.kai.networkdemo.packet.outbound.ClientClosedOutboundPacket
import me.kai.networkdemo.packet.outbound.NetworkInviteOutboundPacket
import me.kai.networkdemo.packet.outbound.NewClientOutboundPacket
import me.kai.networkdemo.packet.outbound.OutboundPacket
import me.kai.networkdemo.recipient.RecipientAddress
import me.kai.networkdemo.recipient.RecipientConnection
import me.kai.networkdemo.recipient.SingleRecipientConnection

class Client private constructor(val clientAddress: RecipientAddress, val printsEnabled: Boolean) {

    companion object {
        lateinit var instance: Client
        private var started = false

        fun start(localIpName: String, port: Int, prints: Boolean = true) {
            if (started) throw IllegalStateException("Client has already been started on ${instance.clientAddress.signedPort}")
            instance = Client(RecipientAddress(localIpName, port, localIpName = localIpName), prints)
            started = true
        }
    }

    val recipients: MutableSet<RecipientAddress> = HashSet()
    val server = Server(clientAddress.signedPort)

    init {
        println("[Client] Localhost client start on port ${clientAddress.signedPort}")
        Runtime.getRuntime().addShutdownHook(Thread {
            ClientClosedOutboundPacket().sendAndPrint()
            server.close()
            println("[Client] Closed local server\n")
        })
    }

    fun inviteNewClient(ipName: String, port: Int) {
        val address = RecipientAddress(ipName, port)
        NewClientOutboundPacket(address).sendAndPrint()
        recipients.add(address)
        NetworkInviteOutboundPacket(address).sendAndPrint()
    }

    fun hasRecipient(ipName: String, port: Int): Boolean {
        for (recipient in recipients) if (recipient.equals(ipName, port)) return true
        return false
    }

    fun createConnection(recipient: RecipientAddress, handler: (RecipientConnection) -> Unit) {
        val connection = SingleRecipientConnection(recipient)
        handler(connection)
        connection.close()
    }

    fun sendPacket(recipient: RecipientAddress, packet: OutboundPacket) {
        createConnection(recipient) {
            it.sendPacket(packet)
        }
    }

    fun broadcastPacket(packet: BroadcastOutboundPacket) {
        for (recipient in recipients) sendPacket(recipient, packet)
    }

}