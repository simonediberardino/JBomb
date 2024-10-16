package game.network.gamehandler

import game.JBomb
import game.domain.match.JBombMatch
import game.network.callbacks.TCPServerCallback
import game.network.dispatch.HttpMessageReceiverHandler
import game.network.events.forward.LevelInfoHttpEventForwarder
import game.network.serializing.HttpParserSerializer
import game.network.sockets.TCPServer
import game.network.usecases.SendServerInfoToMasterServerUseCase
import game.network.usecases.SendStopServerToMasterServerUseCase
import game.presentation.ui.pages.server_browser.ServerInfo
import game.properties.RuntimeProperties
import game.usecases.GetInetAddressUseCase
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log
import kotlinx.coroutines.launch

/**
 * Handles communication with clients from the server-side using TCP.
 *
 * @param port The port number on which the server listens for client connections.
 */
class ServerGameHandler(private val port: Int) : TCPServerCallback {
    private lateinit var server: TCPServer
    private var ipv4: String? = null

    val clientsConnected: Int
        get() = if (this::server.isInitialized) server.clients.size else 0

    /**
     * Indicates whether the server is currently running and accepting client connections.
     */
    var running: Boolean = false
        private set

    /**
     * Creates and opens the TCP server for handling client connections.
     */
    fun create() {
        server = TCPServer(port)
        server.also {
            it.register(this)
            it.open()
        }
    }

    /**
     * Callback method invoked when the server is closed.
     */
    override suspend fun onCloseServer() {
        running = false

        JBomb.scope.launch {
            ipv4?.let { SendStopServerToMasterServerUseCase(it, port).invoke() }
        }
    }

    /**
     * Callback method invoked when the server is started.
     */
    override fun onStartServer() {
        running = true
        server.scope.launch {
            ipv4 = GetInetAddressUseCase().invoke()?.hostName
            updateInfo()
        }
    }

    private suspend fun updateInfo() {
        val playerCount = if (RuntimeProperties.dedicatedServer) clientsConnected else clientsConnected + 1

        val serverInfo = ipv4?.let {
            ServerInfo(
                name = JBomb.match.currentLevel.info.networkName,
                ip = it,
                port = JBombMatch.port,
                players = playerCount,
                ping = 0
            )
        }

        Log.e("Sending update info $serverInfo")

        serverInfo?.let {
            SendServerInfoToMasterServerUseCase(
                serverInfo = it
            ).invoke()
        }
    }

    /**
     * Callback method invoked when a client is connected to the server.
     *
     * @param indexedClient The information about the connected client.
     */
    override fun onClientConnected(indexedClient: TCPServer.IndexedClient) {
        val data: MutableMap<String, String> = HashMap()
        data["id"] = indexedClient.id.toString()

        val levelInfo = JBomb.match.currentLevel.info

        data["levelId"] = levelInfo.levelId.toString()
        data["worldId"] = levelInfo.worldId.toString()

        Log.i("onClientConnected $data")

        // Sends the info of the level to the client
        LevelInfoHttpEventForwarder().invoke(data)

        server.scope.launch {
            updateInfo()
        }
    }

    override fun onClientDisconnected(indexedClient: TCPServer.IndexedClient) {
        val client = JBomb.match.getEntityById(indexedClient.id) ?: return
        client.logic.despawn()

        server.scope.launch {
            updateInfo()
        }
    }

    /**
     * Initiates the creation and opening of the server upon the start of the server game handler.
     */
    override fun onStart() {
        create()
    }

    /**
     * Callback method invoked when the server is closed.
     */
    override fun onClose() {}

    /**
     * Sends data to all connected clients.
     *
     * @param data The data to be sent to all clients.
     */
    override fun sendData(data: String) {
        if (this::server.isInitialized) {
            server.sendData(data)
        }
    }

    /**
     * Sends data to a specific client with an option to ignore a specific receiver.
     *
     * @param data The data to be sent to the client.
     * @param receiverId The ID of the intended receiver client.
     * @param ignore If true, the data will be sent to all clients except the specified receiverId.
     */
    override fun sendData(data: String, receiverId: Long, ignore: Boolean) {
        if (receiverId == -1L) {
            sendData(data)
            return
        }

        if (this::server.isInitialized) {
            server.sendData(receiverId, data, ignore)
        }
    }

    /**
     * Callback method invoked when data is received from a client.
     *
     * @param data The raw data received from the client.
     */
    override fun onDataReceived(data: String) {
        val formattedData: Map<String, String> = HttpParserSerializer.instance.parse(data)
        HttpMessageReceiverHandler.instance.handle(formattedData)

        Log.e("onDataReceived $formattedData")
        // if message is not private, forward it to every other client
        if (!formattedData["private"].toBoolean()) {
            val senderId = formattedData.getOrTrim("actorId")?.toLong() ?: return
            sendData(data, receiverId = senderId, ignore = true)
        }
    }

    /**
     * Checks whether the server game handler is currently running and accepting client connections.
     *
     * @return True if the server game handler is running, false otherwise.
     */
    override fun isRunning(): Boolean = running

    override suspend fun disconnect() {
        if (this::server.isInitialized)
            server.close()
    }
}
