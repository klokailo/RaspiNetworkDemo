package me.kai.networkdemo.packet

import me.kai.networkdemo.recipient.RecipientAddress

data class EncodedPacket(val header: PacketHeader, val body: ByteArray) {

    init {
        if (body.size > Byte.MAX_VALUE) throw IllegalArgumentException("Cannot form EncodedPacket with body size greated than max byte value!")
    }

    constructor(id: Byte, sender: RecipientAddress, body: ByteArray): this(PacketHeader(id, sender, body.size.toByte()), body)

    // IDE generated signatures for equals and hashcode
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncodedPacket

        if (header != other.header) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }

}