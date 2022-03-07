package me.kai.networkdemo.packet

import me.kai.networkdemo.Server

data class PacketResponse(val packetIdSent: Byte, val responseType: Type) {

    val packetSentName = when(packetIdSent) {
        Server.CLIENT_CLOSED_INBOUND_TYPE -> "CLIENT_CLOSED"
        Server.NEW_CLIENT_INBOUND_TYPE -> "NEW_CLIENT"
        Server.MESSAGE_INBOUND_TYPE -> "MESSAGE"
        Server.NETWORK_INVITE_INBOUND_TYPE -> "NETWORK_INVITE"
        Server.RESPONSE_INBOUND_TYPE -> "RESPONSE"
        else -> "UNKNOWN"
    }

    enum class Type(val id: Byte, val message: String) {

        SUCCESS(0, "Success"),
        GENERIC_FAIL(-1, "Fail: unknown"),
        NETWORK_FAIL(-2, "Fail: network error"),
        PROGRAM_FAIL(-3, "Fail: program fail"),
        ACKNOWLEDGE(1, "Acknowledge");

        companion object {
            fun typeFromId(id: Byte): Type {
                for (type in values()) if (type.id == id) return type
                throw IllegalArgumentException("Invalid PacketResponse.Type ID: $id")
            }
        }

    }

}