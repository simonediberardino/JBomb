package game.network.gamehandler

import game.JBomb
import game.localization.Localization
import game.network.dispatch.HttpMessageReceiverHandler
import game.network.serializing.HttpParserSerializer
import game.network.sockets.TCPClient
import game.network.sockets.TCPClientEvent
import game.network.sockets.TCPServer
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
    private suspend fun connect() {
        client = TCPClient(serverAddress, serverPort)

        // Start collecting events from the client's eventFlow
        client.scope.launch {
            client.eventFlow.collect { event ->
                Log.i("[ClientGameHandler] collect: $event")
                try {
                    when (event) {
                        is TCPClientEvent.Connected -> onConnect()
                        is TCPClientEvent.Disconnected -> onDisconnect()
                        is TCPClientEvent.ErrorOccurred -> onError(event.message)
                        is TCPClientEvent.DataReceived -> onDataReceived(event.data)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }

        client.connect()
    }

    /**
     * Handles errors that may occur during the client-server communication.
     */
    private fun onError(message: String?) {
        Log.i("[ClientGameHandler] onError $message")
        connected = false

        JBomb.scope.launch {
            JBomb.networkError(message)
        }
    }

    /**
     * Handles disconnection events from the game server.
     */
    private fun onDisconnect() {
        connected = false
        Log.i("[ClientGameHandler] onDisconnect")
    }

    /**
     * Handles successful connection events to the game server.
     */
    private fun onConnect() {
        connected = true
        Log.i("[ClientGameHandler] onConnect")
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
        Log.i("${javaClass.simpleName} received: $data")

        // Check if the data is JSON-like (starts with '{' or '[')
        if (data.firstOrNull() in setOf('{', '[')) {
            val formattedData = HttpParserSerializer.instance.parse(data)
            HttpMessageReceiverHandler.instance.handle(formattedData)
            return
        }

        // Handle non-JSON data
        handlePlainMessages(data)
    }

    private fun handlePlainMessages(data: String) {
        when (data) {
            TCPServer.ServerCodes.ServerFull.name -> {
                JBomb.networkError(Localization.get(Localization.SERVER_IS_FULL))
            }
        }
    }

    /**
     * Sends data to the game server.
     *
     * @param data The data to be sent to the game server.
     */
    override fun sendData(data: String) {
        if (connected && this::client.isInitialized) {
            Log.i("[ClientGameHandler] sendData")

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
            connected = false
            client.close()
        }
    }
}