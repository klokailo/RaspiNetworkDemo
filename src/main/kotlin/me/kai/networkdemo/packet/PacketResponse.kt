package me.kai.networkdemo.packet

data class PacketResponse(val packetTypeSent: PacketType, val responseType: Type) {

    enum class Type(val id: Byte, val message: String) {

        SUCCESS(0, "Success"),
        GENERIC_FAIL(-1, "Fail: unknown"),
        NETWORK_FAIL(-2, "Fail: network error"),
        PROGRAM_FAIL(-3, "Fail: program fail"),
        ACKNOWLEDGE(1, "Acknowledge");

        companion object {
            fun fromId(id: Byte): Type {
                for (type in values()) if (type.id == id) return type
                throw IllegalArgumentException("Invalid PacketResponse.Type ID: $id")
            }
        }

    }

}