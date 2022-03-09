package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.packet.PacketResponse
import me.kai.networkdemo.packet.PacketType
import me.kai.networkdemo.recipient.RecipientAddress

// I am sending my response of $response.responseType to $recipient in response to $response.packetIdSent
class ResponseOutboundPacket(override val recipient: RecipientAddress, val response: PacketResponse): TargetedOutboundPacket {

    override val type = PacketType.RESPONSE

    override val body = ByteArray(2).also {
        it[0] = response.packetTypeSent.id
        it[1] = response.responseType.id
    }

    override fun print() = println("[Outbound] Sent \"${response.responseType.message}\" to $recipient in response to ${response.packetTypeSent.label}")

}