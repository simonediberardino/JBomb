package game.network.sockets

import game.network.callbacks.TCPClientCallback
import game.utils.dev.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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
    private val scope = CoroutineScope(Dispatchers.IO)

    fun connect() {
        if (serverAddress.isBlank()) {
            close()
            onError("Unknown Host")
            return
        }

        Log.e("Connecting to $serverAddress")

        try {
            socket = Socket(serverAddress, serverPort)
            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer = PrintWriter(socket.getOutputStream(), true)

            for (listener in listeners) {
                listener.onConnect()
            }

            readStream()
        } catch (exception: Exception) {
            close()
            onError(exception.localizedMessage)
        }
    }

    private fun onError(message: String?) {
        for (listener in listeners) {
            listener.onError(message)
        }
    }

    private fun onDataReceived(data: String) {
        listeners.forEach {
            it.onDataReceived(data)
        }
    }

    override fun sendData(data: String) {
        writer.println(data)
    }

    private fun readStream() {
        scope.launch {
            try {
                while (true) {
                    Log.e("Reading")
                    // Reads the stream from the server;
                    val serverData = reader.readLine()

                    if (serverData == null) {
                        // Server disconnected
                        Log.i("Server disconnected")
                        break
                    }

                    onDataReceived(serverData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                close()
                onError(null)
            }
        }
    }

    fun close() {
        if (this::reader.isInitialized) {
            reader.close()
        }

        if (this::writer.isInitialized) {
            writer.close()
        }

        if (this::socket.isInitialized) {
            socket.close()
        }

        for (listener in listeners) {
            listener.onDisconnect()
        }

        scope.cancel()
    }

    fun register(tcpClientCallback: TCPClientCallback) {
        listeners.add(tcpClientCallback)
    }

    fun unregister(tcpClientCallback: TCPClientCallback) {
        listeners.remove(tcpClientCallback)
    }
}