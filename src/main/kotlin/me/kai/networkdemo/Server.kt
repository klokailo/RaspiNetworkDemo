package me.kai.networkdemo

import me.kai.networkdemo.packet.PacketContents
import me.kai.networkdemo.packet.PacketType
import me.kai.networkdemo.packet.inbound.InboundPacket
import me.kai.networkdemo.recipient.RecipientAddress
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int) {

    var serverSocket: ServerSocket? = null
    var clientSocket: Socket? = null
    var inStream: DataInputStream? = null

    private var running: Boolean = false

    init {
        Thread {
            running = true
            serverSocket = ServerSocket(port)
            while (running) {
                clientSocket = serverSocket!!.accept()
                inStream = DataInputStream(BufferedInputStream(clientSocket!!.getInputStream()))
                try {
                    val awaitedPacket = awaitPacket(inStream!!)
                    val inboundPacket: InboundPacket = awaitedPacket.produceInboundPacket()
                    inboundPacket.act()
                    if (Client.instance.printsEnabled) inboundPacket.print()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                } finally {
                    inStream?.close()
                    clientSocket?.close()
                }
            }
        }.start()
        println("[Server] Localhost server started on port $port")
    }

    fun close() {
        running = false
        inStream?.close()
        clientSocket?.close()
        serverSocket?.close()
    }

    private fun awaitPacket(input: DataInputStream) =
        PacketContents(
            PacketType.fromId(input.readByte()),
            RecipientAddress(ByteArray(6).also { input.readFully(it) }), // Sender
            ByteArray(input.readByte().toInt()).also { input.readFully(it) }) // Length + data

}