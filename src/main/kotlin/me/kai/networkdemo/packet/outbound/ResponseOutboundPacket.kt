package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.packet.PacketResponse
import me.kai.networkdemo.recipient.RecipientAddress

class ResponseOutboundPacket(override val recipient: RecipientAddress, val response: PacketResponse): TargetedOutboundPacket {

    override val id: Byte = 4

    private val encoded by lazy {
        ByteArray(2).also {
            it[0] = response.packetIdSent
            it[1] = response.responseType.id
        }
    }

    override fun encode() = encoded

    override fun print() = println("[Outbound] Sending \"${response.responseType.message}\" to $recipient in response to ${TODO()}")

}