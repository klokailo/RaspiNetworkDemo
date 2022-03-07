package me.kai.networkdemo.packet.outbound

import me.kai.networkdemo.recipient.RecipientAddress

// I am whispering a message to a targeted recipient
class WhisperOutboundPacket(override val recipient: RecipientAddress, message: String): MessageOutboundPacket(message), TargetedOutboundPacket {

    override fun print() = println("[Outbound] Sent whisper message packet to $recipient \"$message\"")

}