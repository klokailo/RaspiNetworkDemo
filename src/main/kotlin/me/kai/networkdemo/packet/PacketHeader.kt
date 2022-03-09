package me.kai.networkdemo.packet

import me.kai.networkdemo.recipient.RecipientAddress

class PacketHeader(val packetType: PacketType, val sender: RecipientAddress, val length: Byte) {

    val encoded by lazy {
        val array = ByteArray(8) // One for id, 6 for sender, 1 for length
        array[0] = packetType.id
        for (byteIndex in 0 until 6) array[byteIndex + 1] = sender.encoded[byteIndex]
        array[7] = length
        array
    }

}