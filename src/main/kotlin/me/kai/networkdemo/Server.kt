package me.kai.networkdemo

import me.kai.networkdemo.packet.inbound.ClientClosedInboundPacket
import me.kai.networkdemo.packet.inbound.InboundPacket
import me.kai.networkdemo.packet.inbound.NetworkInviteInboundPacket
import me.kai.networkdemo.packet.inbound.MessageInboundPacket
import me.kai.networkdemo.packet.inbound.NewClientInboundPacket
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
                val packetId = inStream!!.readByte()
                val inboundPacket: InboundPacket = when (packetId) {
                    CLIENT_CLOSED_INBOUND_ID -> ClientClosedInboundPacket(inStream!!)
                    NEW_CLIENT_INBOUND_ID -> NewClientInboundPacket(inStream!!)
                    MESSAGE_INBOUND_ID -> MessageInboundPacket(inStream!!)
                    NETWORK_INVITE_INBOUND_ID -> NetworkInviteInboundPacket(inStream!!)
                    else -> continue
                }
                inboundPacket.act()
                if (Client.instance.printsEnabled) inboundPacket.print()
                clientSocket?.close()
                outStream?.close()
                inStream?.close()
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

}