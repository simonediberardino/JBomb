package game.level.online

import game.http.callbacks.TCPClientCallback
import game.http.dispatch.HttpMessageReceiverHandler
import game.http.serializing.HttpParserSerializer
import game.http.sockets.TCPClient

class ClientGameHandler(
        private val serverAddress: String,
        private val serverPort: Int
) : TCPClientCallback, OnlineGameHandler {

    private lateinit var client: TCPClient
    var id = -1
    var connected: Boolean = false
        private set

    private fun connect() {
        client = TCPClient(serverAddress, serverPort)
        client.connect()
        client.register(this)
    }

    override fun onError() {
        connected = false
    }

    override fun onDisconnect() {
        connected = false
    }

    override fun onConnect() {
        connected = true
    }

    override fun onIdReceived(id: Int) {
        this.id = id
        println("onIdReceived: $id")
    }

    override fun onDataReceived(data: String) {
        println("onDataReceived $data")
        val formattedData: Map<String, String> = HttpParserSerializer.instance.parse(data)
        HttpMessageReceiverHandler.instance.handle(formattedData)
    }

    override fun onStart() {
        connect()
    }

    override fun onClose() {
    }
}