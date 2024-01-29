package game.network.repo

import game.network.models.HttpMessage

class HttpRepository private constructor() {
    companion object {
        val instance: HttpRepository by lazy { HttpRepository() }
    }

    fun send(message: HttpMessage) {
        /* val string = HttpParserSerializer.instance.serialize(message)

        var sent = false
        for (s in message.senders) {
            val sender = when(s) {
                HttpActor.SERVER -> TCPServer.instance
                HttpActor.CLIENT -> TCPClient.instance
            } ?: continue

            sent = true
            Log.e("Sending $message to $sender")
            sender.sendData(string)
            break
        }
        Log.e("Message $message sent: $sent") */
    }
}