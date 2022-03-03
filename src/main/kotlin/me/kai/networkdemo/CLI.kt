package me.kai.networkdemo

import me.kai.networkdemo.packet.outbound.AnnounceOutboundPacket
import me.kai.networkdemo.packet.outbound.WhisperOutboundPacket
import me.kai.networkdemo.recipient.RecipientAddress
import java.lang.StringBuilder
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Scanner
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)

    val addresses = mutableListOf<String>()
    val networkInterfaces = NetworkInterface.getNetworkInterfaces()
    while (networkInterfaces.hasMoreElements()) {
        val interfaceAddresses = networkInterfaces.nextElement().inetAddresses
        while (interfaceAddresses.hasMoreElements()) {
            val address = interfaceAddresses.nextElement()
            if (address is Inet4Address && !address.hostAddress.equals("127.0.0.1")) addresses.add(address.hostAddress)
        }
    }
    if (addresses.isEmpty()) {
        println("Error: no IPV4 addresses detected (connected to LAN?)")
        exitProcess(129)
    }
    val address = if (addresses.size > 1) {
        println("IPV4 addresses identified: ")
        for ((index, address) in addresses.withIndex()) {
            println("[${index + 1}] $address")
        }
        print("Select IPV4 address [number] to use: ")
        val selectedAddress = input.nextInt()
        if (selectedAddress <= 0 || selectedAddress > addresses.size) {
            println("Error: invalid selected address")
            exitProcess(129)
        }
        println("Using ${addresses[selectedAddress - 1]}")
        addresses[selectedAddress - 1]
    } else {
        println("Using only identified address: ${addresses[0]}")
        addresses[0]
    }

    print("Enter port number: ")
    val port = input.nextInt()

    Client.start(address, port)
    println("Type \"help\" for list of commands")
    var line: String? = null
    while (input.hasNext() && input.nextLine().also { line = it } != null) {
        if (line!!.isEmpty()) continue
        if (line!!.startsWith("quit", ignoreCase = true)) {
            break
        } else if (line!!.startsWith("connect")) {
            try {
                val split = line!!.split(" ")
                val ip =
                    if (split[1].equals("localhost", ignoreCase = true)) "127.0.0.1"
                    else split[1]
                val recipientPort = Integer.parseInt(split[2])
                try {
                    Client.instance.inviteNewClient(ip, recipientPort)
                } catch (exception: Exception) {
                    println("Error executing connect:")
                    exception.printStackTrace()
                }
            } catch (ignored: Exception) {
                println("Bad format for connect!")
            }
        } else if (line!!.startsWith("announce", ignoreCase = true)) {
            val message = line!!.replaceFirst("announce ", "")
            try {
                AnnounceOutboundPacket(message).sendAndPrint()
            } catch (exception: Exception) {
                println("Error executing announce:")
                exception.printStackTrace()
            }
        } else if (line!!.startsWith("whisper", ignoreCase = true)) {
            try {
                val split = line!!.split(" ")
                val ip =
                    if (split[1].equals("localhost", ignoreCase = true)) "127.0.0.1"
                    else split[1]
                val recipientPort = Integer.parseInt(split[2])
                val messageBuilder = StringBuilder()
                for (i in 3 until split.size) messageBuilder.append(split[i])
                val message = messageBuilder.toString()
                try {
                    WhisperOutboundPacket(RecipientAddress(ip, recipientPort), message).sendAndPrint()
                } catch (exception: Exception) {
                    println("Error executing whisper:")
                    exception.printStackTrace()
                }
            } catch (ignored: Exception) {
                println("Bad format for whisper!")
            }
        } else if (line!!.startsWith("list", ignoreCase = true)) {
            println("Clients on this network: ")
            println("  - ${Client.instance.clientAddress} (Me)")
            for (recipient in Client.instance.recipients) {
                println("  - $recipient")
            }
        } else {
            println("List of commands:")
            println("  - help: This message")
            println("  - connect <ip> <port>: Connects a new client on to the network, and notifies the rest of the network")
            println("  - list: Provides a list of all the clients on the current network")
            println("  - quit: Stops this client")
            println("  - announce <message>: Broadcast a message to all clients")
            println("  - whisper <ip> <port> <message>: Whisper a message to a recipient")
        }
    }
    exitProcess(129)
}