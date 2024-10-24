package game.network.gamehandler

import game.JBomb
import game.network.callbacks.TCPClientCallback
import game.network.dispatch.HttpMessageReceiverHandler
import game.network.serializing.HttpParserSerializer
import game.network.sockets.TCPClient
import game.network.sockets.TCPClientEvent
import game.utils.dev.Log
import kotlinx.coroutines.launch

class ClientGameHandler(
    private val serverAddress: String,
    private val serverPort: Int
): OnlineGameHandler {

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
     * Establishes a connection to the game server and starts listening to the event flow.
     */
    private fun connect() {
        client = TCPClient(serverAddress, serverPort)
        client.connect()

        // Start collecting events from the client's eventFlow
        client.scope.launch {
            client.eventFlow.collect { event ->
                when (event) {
                    is TCPClientEvent.Connected -> onConnect()
                    is TCPClientEvent.Disconnected -> onDisconnect()
                    is TCPClientEvent.ErrorOccurred -> onError(event.message)
                    is TCPClientEvent.DataReceived -> onDataReceived(event.data)
                }
            }
        }
    }

    /**
     * Handles errors that may occur during the client-server communication.
     */
    private fun onError(message: String?) {
        connected = false
        JBomb.networkError(message)
    }

    /**
     * Handles disconnection events from the game server.
     */
    private fun onDisconnect() {
        connected = false
        Log.i("ClientGameHandler onDisconnect")
    }

    /**
     * Handles successful connection events to the game server.
     */
    private fun onConnect() {
        connected = true
        Log.i("ClientGameHandler onConnect")
    }
    /**
     * Initiates the connection to the game server upon the start of the client game handler.
     */
    override suspend fun onStart() {
        connect()
    }

    /**
     * Handles the closure of the client game handler.
     */
    override fun onClose() {
        // Handle clean-up when closing the client game handler
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

        if (connected && this::client.isInitialized) {
            client.sendData(data)
        }
    }

    /**
     * Sends data to the game server with an option to ignore a specific receiver.
     *
     * @param data The data to be sent to the game server.
     * @param receiverId The ID of the intended receiver client.
     * @param ignore If true, the data will be sent to all clients except the specified receiverId.
     */
    override fun sendData(data: String, receiverId: Long, ignore: Boolean) {
        sendData(data) // In this client-side implementation, it just forwards the data to the server.
    }

    /**
     * Checks whether the client game handler is currently running and connected to the game server.
     *
     * @return True if the client game handler is running and connected, false otherwise.
     */
    override fun isRunning(): Boolean {
        return connected
    }

    /**
     * Disconnects from the game server and stops the event flow collection.
     */
    override suspend fun disconnect() {
        if (this::client.isInitialized) {
            println("Disconnecting client")
            connected = false
            client.close()
        }
    }
}