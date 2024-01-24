package game.http.repo

import game.http.models.HttpActor
import game.http.models.HttpMessage
import game.http.serializing.HttpParserSerializer
import game.http.sockets.TCPClient
import game.http.sockets.TCPServer

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