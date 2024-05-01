package game.network.sockets

import game.Bomberman
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
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
    private var clients: MutableMap<Long, IndexedClient> = mutableMapOf()
    private val listeners: MutableSet<TCPServerCallback> = mutableSetOf()
    private var progressiveId = 0L
    private val scope = CoroutineScope(Dispatchers.IO)

    fun open() {
        try {
            socket = ServerSocket(port)
            onStart()
            start()
        } catch (ioException: IOException) {
            close()
        }
    }

    private fun onClose() {
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
            val reader = BufferedReader(InputStreamReader(clientSocket.client.getInputStream()))

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
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            disconnectClient(clientSocket)
        }
    }

    fun start() {
        scope.launch {
            while (true) {
                val clientSocket = socket.accept()
                val indexedClient = IndexedClient(progressiveId, clientSocket)

                clients[progressiveId] = indexedClient
                progressiveId++

                Log.i("Client connected: ${clientSocket.inetAddress.hostAddress}")

                // Launch coroutine to handle the client
                launch {
                    handleClient(indexedClient)
                }
            }
        }
    }

    override fun sendData(data: String) {
        scope.launch {
            clients.values.parallelStream().forEach {
                sendData(it.client, data)
            }
        }
    }

    fun sendData(clientId: Long, data: String, ignore: Boolean) {
        if (ignore) {
            for (client in clients.values) {
                if (client.id != clientId)
                    sendData(client.client, data)
            }
            return
        }

        sendData(clients[clientId]?.client ?: return, data)
        Log.i("sendData: $clientId sent $data")
    }

    private fun sendData(client: Socket, data: String) {
        val writer = PrintWriter(client.getOutputStream(), true)
        writer.println(data)
    }

    fun register(tcpServerCallback: TCPServerCallback) {
        listeners.add(tcpServerCallback)
    }

    fun unregister(tcpServerCallback: TCPServerCallback) {
        listeners.remove(tcpServerCallback)
    }

    fun close() {
        clients.values.forEach {
            disconnectClient(it)
        }

        socket.close()

        onClose()

        scope.cancel()
    }

    private fun disconnectClient(clientSocket: IndexedClient) {
        clientSocket.client.close()
        clients.remove(clientSocket.id)

        for (listener in listeners) {
            listener.onClientDisconnected(clientSocket)
        }
    }

    class IndexedClient(val id: Long, val client: Socket)
}
