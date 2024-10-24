package game.network.sockets

import game.utils.dev.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class TCPServer(private var port: Int) : TCPSocket {
    private lateinit var socket: ServerSocket
    internal var clients: MutableMap<Long, IndexedClient> = mutableMapOf()
    private var progressiveId = 0L
    val scope = CoroutineScope(Dispatchers.IO)

    // SharedFlow to emit server events (client connections, disconnections, and messages)
    private val _eventFlow = MutableSharedFlow<ServerEvent>()
    val eventFlow = _eventFlow.asSharedFlow() // Public flow for clients to listen

    suspend fun open() {
        try {
            socket = ServerSocket(port)
            emitEvent(ServerEvent.ServerStarted)
            start()
        } catch (ioException: IOException) {
            runBlocking {
                close()
            }
        }
    }

    private suspend fun handleClient(clientSocket: IndexedClient) = withContext(Dispatchers.IO) {
        try {
            clientSocket.reader.use { reader ->
                emitEvent(ServerEvent.ClientConnected(clientSocket.id))

                while (true) {
                    val clientData = reader.readLine() ?: break
                    Log.i("Received from client ${clientSocket.id}: $clientData")

                    // Emit the received data as an event
                    emitEvent(ServerEvent.DataReceived(clientSocket.id, clientData))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            disconnectClient(clientSocket)
        }
    }

    private suspend fun emitEvent(event: ServerEvent) {
        _eventFlow.emit(event) // Emit event to all listeners
    }

    fun start() {
        scope.launch {
            while (true) {
                try {
                    if (socket.isClosed)
                        break

                    val clientSocket = socket.accept()
                    val indexedClient = IndexedClient(
                        id = progressiveId,
                        client = clientSocket,
                        writer = PrintWriter(clientSocket.getOutputStream(), true),
                        reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                    )

                    clients[progressiveId] = indexedClient
                    progressiveId++

                    Log.i("Client connected: ${clientSocket.inetAddress.hostAddress}")

                    // Launch coroutine to handle the client
                    launch {
                        handleClient(indexedClient)
                    }
                } catch (e: Exception) {
                    break
                }
            }
        }
    }

    override fun sendData(data: String) {
        scope.launch {
            clients.values.forEach { client ->
                sendData(client, data)
            }
        }
    }

    private fun sendData(client: IndexedClient, data: String) {
        client.writer.println(data)
    }

    fun sendData(clientId: Long, data: String, ignore: Boolean) {
        if (ignore) {
            for (client in clients.values) {
                if (client.id != clientId)
                    sendData(client, data)
            }
            return
        }

        sendData(clients[clientId] ?: return, data)
        Log.i("sendData: $clientId sent $data")
    }

    suspend fun close() {
        clients.values.forEach {
            disconnectClient(it)
        }

        if (this::socket.isInitialized) {
            socket.close()
        }

        emitEvent(ServerEvent.ServerClosed)
        scope.cancel()
    }

    private suspend fun disconnectClient(clientSocket: IndexedClient) {
        clientSocket.client.close()
        clientSocket.writer.close()
        clientSocket.reader.close()

        clients.remove(clientSocket.id)

        emitEvent(ServerEvent.ClientDisconnected(clientSocket.id))
    }

    fun isClosed(): Boolean = socket.isClosed

    class IndexedClient(val id: Long, val client: Socket, val writer: PrintWriter, val reader: BufferedReader)

    // Define different types of events the server can emit
    sealed class ServerEvent {
        object ServerStarted : ServerEvent()
        object ServerClosed : ServerEvent()
        data class ClientConnected(val clientId: Long) : ServerEvent()
        data class ClientDisconnected(val clientId: Long) : ServerEvent()
        data class DataReceived(val clientId: Long, val data: String) : ServerEvent()
    }
}
