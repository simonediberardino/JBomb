package game.level.online

import game.http.callbacks.TCPClientCallback
import game.http.dispatch.HttpMessageReceiverHandler
import game.http.serializing.HttpParserSerializer
import game.http.sockets.TCPClient

class ClientGameHandler(
        private val serverAddress: String,
        private val serverPort: Int
) : TCPClientCallback {

    private lateinit var client: TCPClient
    var id = -1L

    companion object {
        var connected: Boolean = false
            private set
    }

    private fun connect() {
        client = TCPClient(serverAddress, serverPort)
        client.register(this)
        client.connect()
    }

    override fun onError() {
        connected = false
    }

    override fun onDisconnect() {
        connected = false
        println("ClientGameHandler onDisconnect")
    }

    override fun onConnect() {
        connected = true
        println("ClientGameHandler onConnect")
    }

    override fun onIdReceived(id: Long) {
        this.id = id
    }

    override fun onStart() {
        connect()
    }

    override fun onClose() {
    }

    override fun onDataReceived(data: String) {
        println("${javaClass.simpleName} onDataReceived $data")
        val formattedData: Map<String, String> = HttpParserSerializer.instance.parse(data)
        HttpMessageReceiverHandler.instance.handle(formattedData)
    }

    override fun sendData(data: String) {
        println("${javaClass.simpleName} sendData")
        client.sendData(data)
    }

    override fun sendData(data: String, receiverId: Long) {
        sendData(data)
    }

    override fun isRunning(): Boolean {
        return connected
    }
}