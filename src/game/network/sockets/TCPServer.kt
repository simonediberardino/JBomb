package game.network.sockets

import game.network.callbacks.TCPServerCallback
import game.utils.dev.Log
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class TCPServer(private var port: Int) : TCPSocket {
    private lateinit var socket: ServerSocket
    internal var clients: MutableMap<Long, IndexedClient> = mutableMapOf()
    private val listeners: MutableSet<TCPServerCallback> = mutableSetOf()
    private var progressiveId = 0L
    val scope = CoroutineScope(Dispatchers.IO)

    fun open() {
        try {
            socket = ServerSocket(port)
            onStart()
            start()
        } catch (ioException: IOException) {
            runBlocking {
                close()
            }
        }
    }

    private suspend fun onClose() {
        for (listener in listeners) {
            listener.onCloseServer()
            unregister(listener)
        }
    }

    private fun onStart() {
        for (listener in listeners) {
            listener.onStartServer()
        }
    }

    private suspend fun handleClient(clientSocket: IndexedClient) = withContext(Dispatchers.IO) {
        try {
            clientSocket.reader.use { reader ->
                for (listener in listeners) {
                    listener.onClientConnected(clientSocket)
                }

                while (true) {
                    // Reads the stream from the client;
                    val clientData = reader.readLine()

                    if (clientData == null) {
                        // Client disconnected
                        Log.i("Client disconnected")
                        break
                    }

                    Log.i("Received from client: $clientData")
                    for (listener in listeners) {
                        listener.onDataReceived(clientData)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            disconnectClient(clientSocket)
        }
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
            clients.values.parallelStream().forEach {
                sendData(it, data)
            }
        }
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

    private fun sendData(client: IndexedClient, data: String) {
        client.writer.println(data)
    }

    fun register(tcpServerCallback: TCPServerCallback) {
        listeners.add(tcpServerCallback)
    }

    fun unregister(tcpServerCallback: TCPServerCallback) {
        listeners.remove(tcpServerCallback)
    }

    suspend fun close() {
        clients.values.forEach {
            disconnectClient(it)
        }

        if (this::socket.isInitialized) {
            socket.close()
        }

        onClose()

        scope.cancel()
    }

    private fun disconnectClient(clientSocket: IndexedClient) {
        clientSocket.client.close()
        clientSocket.writer.close()
        clientSocket.reader.close()

        clients.remove(clientSocket.id)

        for (listener in listeners) {
            listener.onClientDisconnected(clientSocket)
        }
    }

    class IndexedClient(val id: Long, val client: Socket, val writer: PrintWriter, val reader: BufferedReader)
}
