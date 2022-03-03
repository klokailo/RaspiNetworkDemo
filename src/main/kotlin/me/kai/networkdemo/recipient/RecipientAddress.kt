package me.kai.networkdemo.recipient

import me.kai.networkdemo.Client
import java.net.InetAddress
import java.nio.ByteBuffer

// Can't be a data class because we would leak the private constructor through copy() D:
class RecipientAddress private constructor(val ipName: String, val signedPort: Int, val ip: InetAddress, val unsignedPort: Short) {

    val encoded by lazy { ip.address + ByteBuffer.allocate(2).putShort(unsignedPort).array() }

    constructor(ipName: String, signedPort: Int, localIpName: String = Client.instance.clientAddress.ipName): this(
        if (ipName.equals("localhost", ignoreCase = true) || ipName == "127.0.0.1") localIpName else ipName,
        signedPort,
        InetAddress.getByName(if (ipName.equals("localhost", ignoreCase = true) || ipName == "127.0.0.1") localIpName else ipName),
        (signedPort - Short.MAX_VALUE).toShort().also {
            if (signedPort < 0) throw IllegalArgumentException("Invalid port number! Must be equivalent to unsigned short.")
        })

    constructor(ip: InetAddress, unsignedPort: Short): this(
        ip.hostAddress,
        unsignedPort.toInt() + Short.MAX_VALUE,
        ip,
        unsignedPort
    )

    override fun equals(other: Any?): Boolean {
        if (other !is RecipientAddress) return false
        return other.ip == ip && other.unsignedPort == unsignedPort
    }

    override fun hashCode() = ip.hashCode() * 31 + unsignedPort

    override fun toString() = "$ipName:$signedPort"

}