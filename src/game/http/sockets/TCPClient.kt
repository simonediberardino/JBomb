package game.http.sockets

import game.http.callbacks.TCPClientCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.net.UnknownHostException

class TCPClient(private val serverAddress: String,
                private val serverPort: Int
) : TCPSocket {
    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter
    private val listeners: MutableSet<TCPClientCallback> = mutableSetOf()

    fun connect() {
        try {
            socket = Socket(serverAddress, serverPort)
            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer = PrintWriter(socket.getOutputStream(), true)

            for (listener in listeners) {
                listener.onConnect()
            }

            readStream()
        } catch (exception: UnknownHostException) {
            close()
            exception.printStackTrace()

            for (listener in listeners) {
                listener.onError()
            }
        }
    }

    fun register(tcpClientCallback: TCPClientCallback) {
        listeners.add(tcpClientCallback)
    }

    fun unregister(tcpClientCallback: TCPClientCallback) {
        listeners.remove(tcpClientCallback)
    }

    override fun sendData(data: String) {
        writer.println(data)
    }

    private fun readStream() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                while (true) {
                    // Reads the stream from the server;
                    val serverData = reader.readLine()

                    if (serverData == null) {
                        // Client disconnected
                        println("Server disconnected")
                        break
                    }

                    println("Received from server: $serverData")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket.close()
            }
        }
    }

    fun close() {
        reader.close()
        writer.close()
        socket.close()

        for (listener in listeners) {
            listener.onDisconnect()
        }
    }

    companion object {
        var instance: TCPClient? = null
            get() {
                field ?: return null

                if (field!!.socket.isClosed) {
                    field = null
                    return null
                }

                return field
            }
    }
}