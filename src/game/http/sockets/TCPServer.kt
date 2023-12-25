package game.http.sockets

import game.http.callbacks.TCPServerCallback
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class TCPServer(private var port: Int) : TCPSocket {
    private lateinit var socket: ServerSocket
    private var clients: MutableMap<Int, IndexedClient> = mutableMapOf()
    private val listeners: MutableSet<TCPServerCallback> = mutableSetOf()
    private var progressiveId = 0

    fun open() {
        try {
            socket = ServerSocket(port)
            onStart()
            start()
        } catch (ioException: IOException) {
            onClose()
        }
    }

    private fun onClose() {
        for (listener in listeners) {
            listener.onCloseServer()
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
            val writer = PrintWriter(clientSocket.client.getOutputStream(), true)

            for (listener in listeners) {
                listener.onClientConnected(clientSocket)
            }

            while (true) {
                // Reads the stream from the client;
                val clientData = reader.readLine()

                if (clientData == null) {
                    // Client disconnected
                    println("Client disconnected")
                    break
                }

                println("Received from client: $clientData")

                // Respond to the client
                writer.println("Hello from server!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            clientSocket.client.close()
            clients.remove(clientSocket.id)
        }
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val clientSocket = socket.accept()
                val indexedClient = IndexedClient(progressiveId, clientSocket)

                clients[progressiveId] = indexedClient
                progressiveId++

                println("Client connected: ${clientSocket.inetAddress.hostAddress}")

                // Launch coroutine to handle the client
                launch {
                    handleClient(indexedClient)
                }
            }
        }
    }

    override fun sendData(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            clients.values.parallelStream().forEach {
                sendData(it.client, data)
            }
        }
    }

    fun sendData(clientId: Int, data: String) {
        sendData(clients[clientId]?.client ?: return, data)
        println("sendData: $clientId sent $data")
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

    companion object {
        var instance: TCPServer? = null
            get() {
                if (instance == null)
                    return null

                if (instance!!.socket.isClosed) {
                    instance = null
                    return null
                }

                return instance
            }
    }

    class IndexedClient(val id: Int, val client: Socket)
}
