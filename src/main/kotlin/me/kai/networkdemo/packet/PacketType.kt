package me.kai.networkdemo.packet

import me.kai.networkdemo.packet.inbound.ClientClosedInboundPacket
import me.kai.networkdemo.packet.inbound.InboundPacket
import me.kai.networkdemo.packet.inbound.MessageInboundPacket
import me.kai.networkdemo.packet.inbound.NetworkInviteInboundPacket
import me.kai.networkdemo.packet.inbound.NewClientInboundPacket
import me.kai.networkdemo.packet.inbound.ResponseInboundPacket

enum class PacketType(val id: Byte, val label: String) {

    CLIENT_CLOSED(0, "CLIENT_CLOSED") { // Sender has closed
        override fun produceInboundPacket(contents: PacketContents) = ClientClosedInboundPacket(contents)
    },
    NEW_CLIENT(1, "NEW_CLIENT") { // Sender is exposing new client to recipient
        override fun produceInboundPacket(contents: PacketContents) = NewClientInboundPacket(contents)
    },
    MESSAGE(2, "MESSAGE") { // Sender is sending message
        override fun produceInboundPacket(contents: PacketContents) = MessageInboundPacket(contents)
    },
    NETWORK_INVITE(3, "NETWORK_INVITE") { // Sender is inviting recipient to a network
        override fun produceInboundPacket(contents: PacketContents) = NetworkInviteInboundPacket(contents)
    },
    RESPONSE(4, "RESPONSE") { // Sender is sending a response
        override fun produceInboundPacket(contents: PacketContents) = ResponseInboundPacket(contents)
    };

    abstract fun produceInboundPacket(contents: PacketContents): InboundPacket

    companion object {
        fun fromId(id: Byte): PacketType {
            for (type in values()) if (type.id == id) return type
            throw IllegalArgumentException("Invalid packet type id $id")
        }
    }

}