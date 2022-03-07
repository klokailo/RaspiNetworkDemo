package me.kai.networkdemo

import me.kai.networkdemo.packet.EncodedPacket
import me.kai.networkdemo.packet.inbound.ClientClosedInboundPacket
import me.kai.networkdemo.packet.inbound.InboundPacket
import me.kai.networkdemo.packet.inbound.NetworkInviteInboundPacket
import me.kai.networkdemo.packet.inbound.MessageInboundPacket
import me.kai.networkdemo.packet.inbound.NewClientInboundPacket
import me.kai.networkdemo.packet.inbound.ResponseInboundPacket
import me.kai.networkdemo.recipient.RecipientAddress
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int) {

    companion object {
        const val CLIENT_CLOSED_INBOUND_ID: Byte = 0
        const val NEW_CLIENT_INBOUND_ID: Byte = 1
        const val MESSAGE_INBOUND_ID: Byte = 2
        const val NETWORK_INVITE_INBOUND_ID: Byte = 3
        const val RESPONSE_INBOUND_ID: Byte = 4
    }

    var serverSocket: ServerSocket? = null
    var clientSocket: Socket? = null
    var outStream: DataOutputStream? = null
    var inStream: DataInputStream? = null

    private var running: Boolean = false

    init {
        Thread {
            running = true
            serverSocket = ServerSocket(port)
            while (running) {
                clientSocket = serverSocket!!.accept()
                outStream = DataOutputStream(clientSocket!!.getOutputStream())
                inStream = DataInputStream(BufferedInputStream(clientSocket!!.getInputStream()))
                val awaitedPacket = awaitPacket(inStream!!)
                val inboundPacket: InboundPacket = when (awaitedPacket.header.packetId) {
                    CLIENT_CLOSED_INBOUND_ID -> ClientClosedInboundPacket(awaitedPacket)
                    NEW_CLIENT_INBOUND_ID -> NewClientInboundPacket(awaitedPacket)
                    MESSAGE_INBOUND_ID -> MessageInboundPacket(awaitedPacket)
                    NETWORK_INVITE_INBOUND_ID -> NetworkInviteInboundPacket(awaitedPacket)
                    RESPONSE_INBOUND_ID -> ResponseInboundPacket(awaitedPacket)
                    else -> {
                        println("[Inbound] WARNING: received packet with unknown id ${awaitedPacket.header.packetId}")
                        continue
                    }
                }
                inboundPacket.act()
                if (Client.instance.printsEnabled) inboundPacket.print()
                inStream?.close()
                outStream?.close()
                clientSocket?.close()
            }
        }.start()
        println("[Server] Localhost server started on port $port")
    }

    fun close() {
        running = false
        inStream?.close()
        outStream?.close()
        clientSocket?.close()
        serverSocket?.close()
    }

    private fun awaitPacket(input: DataInputStream) =
        EncodedPacket(input.readByte(),
            RecipientAddress(ByteArray(6).also { input.readFully(it) }), // Sender
            ByteArray(input.readByte().toInt()).also { input.readFully(it) }) // Length + data

}