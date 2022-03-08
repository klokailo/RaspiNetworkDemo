package me.kai.networkdemo.recipient

import me.kai.networkdemo.Client
import java.net.InetAddress
import java.nio.ByteBuffer

// Can't be a data class because we would leak the private constructor through copy() D:
class RecipientAddress private constructor(val ipName: String, val signedPort: Int, val ip: InetAddress, val unsignedPort: Short) {

    companion object {
        private fun String.toLocalIp(localIpName: String = Client.instance.clientAddress.ipName): String {
            if (equals("localhost", ignoreCase = true) || this == "127.0.0.1") return localIpName
            return this
        }
    }

    val encoded by lazy { ip.address + ByteBuffer.allocate(2).putShort(unsignedPort).array() }

    constructor(ipName: String, signedPort: Int, localIpName: String = Client.instance.clientAddress.ipName): this(
        ipName.toLocalIp(localIpName),
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

    constructor(byteArray: ByteArray): this(
        InetAddress.getByAddress(byteArray.copyOfRange(0, 4)),
        ByteBuffer.wrap(byteArray.copyOfRange(4, 6)).short
    )

    override fun equals(other: Any?): Boolean {
        if (other !is RecipientAddress) return false
        return other.ip == ip && other.unsignedPort == unsignedPort
    }

    fun equals(ipName: String, signedPort: Int, localIpName: String = Client.instance.clientAddress.ipName): Boolean {
        return ipName.toLocalIp(localIpName) == this.ipName && signedPort == this.signedPort
    }

    fun equals(ip: InetAddress, unsignedPort: Short): Boolean {
        return ip == this.ip && unsignedPort == this.unsignedPort
    }

    override fun hashCode() = ip.hashCode() * 31 + unsignedPort

    override fun toString() = "$ipName:$signedPort"

}