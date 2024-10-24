package game.network.gamehandler

import game.JBomb
import game.domain.match.JBombMatch
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

class ServerGameHandler(private val port: Int): OnlineGameHandler {
    lateinit var server: TCPServer
        private set
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
     * Listens to events emitted by the server through `eventFlow`.
     */
    suspend fun create() {
        server = TCPServer(port)
        server.open()

        // Start listening for events from the server's eventFlow
        server.scope.launch {
            server.eventFlow.collect { event ->
                when (event) {
                    is TCPServer.ServerEvent.ServerStarted -> onStartServer()
                    is TCPServer.ServerEvent.ServerClosed -> onCloseServer()
                    is TCPServer.ServerEvent.ClientConnected -> onClientConnected(event.clientId)
                    is TCPServer.ServerEvent.ClientDisconnected -> onClientDisconnected(event.clientId)
                    is TCPServer.ServerEvent.DataReceived -> onDataReceived(event.data)
                }
            }
        }
    }

    /**
     * Initiates the creation and opening of the server upon the start of the server game handler.
     */
    override suspend fun onStart() {
        create()
    }

    /**
     * Callback method invoked when the server is started.
     */
    private fun onStartServer() {
        running = true
        server.scope.launch {
            ipv4 = GetInetAddressUseCase().invoke()?.hostName
            updateInfo()
        }
    }

    /**
     * Callback method invoked when the server is closed.
     */
    private suspend fun onCloseServer() {
        running = false

        JBomb.scope.launch {
            ipv4?.let { SendStopServerToMasterServerUseCase(it, port).invoke() }
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
     * Handles when a client is connected.
     *
     * @param clientId The ID of the connected client.
     */
    private fun onClientConnected(clientId: Long) {
        val data: MutableMap<String, String> = HashMap()
        data["id"] = clientId.toString()

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

    /**
     * Handles when a client is disconnected.
     *
     * @param clientId The ID of the disconnected client.
     */
    private fun onClientDisconnected(clientId: Long) {
        val client = JBomb.match.getEntityById(clientId) ?: return
        client.logic.despawn()

        server.scope.launch {
            updateInfo()
        }
    }

    /**
     * Handles when data is received from a client.
     *
     * @param clientId The ID of the client sending the data.
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
     * Checks whether the server game handler is currently running and accepting client connections.
     *
     * @return True if the server game handler is running, false otherwise.
     */
    override fun isRunning(): Boolean = running

    /**
     * Disconnects all clients and closes the server.
     */
    override suspend fun disconnect() {
        println("Disconnecting server")

        if (this::server.isInitialized) {
            if (!server.isClosed())
                server.close()
        }
    }

    override fun onClose() {}
}
