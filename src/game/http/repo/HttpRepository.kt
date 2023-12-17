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
        val string = HttpParserSerializer.instance.serialize(message)

        var sent = false
        for (receiver in message.receivers) {
            val sender = when(receiver) {
                HttpActor.SERVER -> TCPServer.instance
                HttpActor.CLIENT -> TCPClient.instance
            } ?: continue

            sent = true
            println("Sending $message to $sender")
            sender.sendData(string)
            break
        }
        println("Message $message sent: $sent")
    }
}