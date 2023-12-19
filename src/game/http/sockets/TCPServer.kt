package game.http.sockets

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class TCPServer(private var port: Int) : TCPSocket {
    private lateinit var socket: ServerSocket
    private var clients: MutableSet<Socket> = mutableSetOf()

    fun open() {
        socket = ServerSocket(port)
    }

    private suspend fun handleClient(clientSocket: Socket) = withContext(Dispatchers.IO) {
        try {
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val writer = PrintWriter(clientSocket.getOutputStream(), true)

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
            clientSocket.close()
            clients.remove(clientSocket)
        }
    }

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val clientSocket = socket.accept()
                clients.add(clientSocket)

                println("Client connected: ${clientSocket.inetAddress.hostAddress}")

                // Launch coroutine to handle the client
                launch {
                    handleClient(clientSocket)
                }
            }
        }
    }

    override fun sendData(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            clients.parallelStream().forEach {
                val writer = PrintWriter(it.getOutputStream(), true)
                writer.println(data)
            }
        }
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
}
