package game.network.gamehandler

import game.Bomberman
import game.network.callbacks.TCPClientCallback
import game.network.dispatch.HttpMessageReceiverHandler
import game.network.serializing.HttpParserSerializer
import game.network.sockets.TCPClient
import game.utils.dev.Log

/**
 * Handles communication with the game server from the client-side using TCP.
 *
 * @param serverAddress The address of the game server.
 * @param serverPort The port number of the game server.
 */
class ClientGameHandler(
        private val serverAddress: String,
        private val serverPort: Int
) : TCPClientCallback {

    private lateinit var client: TCPClient
    var id = -1L

    companion object {
        /**
         * Indicates whether the client is currently connected to the game server.
         */
        var connected: Boolean = false
            private set
    }

    /**
     * Establishes a connection to the game server.
     */
    private fun connect() {
        client = TCPClient(serverAddress, serverPort)
        client.register(this)
        client.connect()
    }

    /**
     * Handles errors that may occur during the client-server communication.
     */
    override fun onError(message: String?) {
        connected = false
        Bomberman.networkError(message)
    }

    /**
     * Handles disconnection events from the game server.
     */
    override fun onDisconnect() {
        connected = false
        Log.i("ClientGameHandler onDisconnect")
    }

    /**
     * Handles successful connection events to the game server.
     */
    override fun onConnect() {
        connected = true
        Log.i("ClientGameHandler onConnect")
    }

    /**
     * Receives the unique identifier assigned to the client by the game server.
     *
     * @param id The unique identifier assigned to the client.
     */
    override fun onIdReceived(id: Long) {
        this.id = id
    }

    /**
     * Initiates the connection to the game server upon the start of the client game handler.
     */
    override fun onStart() {
        connect()
    }

    /**
     * Handles the closure of the client game handler.
     */
    override fun onClose() {
    }

    /**
     * Processes received data from the game server.
     *
     * @param data The raw data received from the game server.
     */
    override fun onDataReceived(data: String) {
        Log.i("${javaClass.simpleName} onDataReceived $data")
        val formattedData: Map<String, String> = HttpParserSerializer.instance.parse(data)
        HttpMessageReceiverHandler.instance.handle(formattedData)
    }

    /**
     * Sends data to the game server.
     *
     * @param data The data to be sent to the game server.
     */
    override fun sendData(data: String) {
        Log.i("${javaClass.simpleName} sendData")

        if (connected && this::client.isInitialized)
            client.sendData(data)
    }

    /**
     * Sends data to the game server with an option to ignore a specific receiver.
     *
     * @param data The data to be sent to the game server.
     * @param receiverId The ID of the intended receiver client.
     * @param ignore If true, the data will be sent to all clients except the specified receiverId.
     */
    override fun sendData(data: String, receiverId: Long, ignore: Boolean) {
        sendData(data)
    }

    /**
     * Checks whether the client game handler is currently running and connected to the game server.
     *
     * @return True if the client game handler is running and connected, false otherwise.
     */
    override fun isRunning(): Boolean {
        return connected
    }

    override fun disconnect() {
        client.close()
    }
}
