package game.network.sockets

import game.utils.dev.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.time.withTimeoutOrNull
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class TCPClient(
    private val serverAddress: String,
    private val serverPort: Int,
    private val timeout: Int = 15_000
) : TCPSocket {
    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter
    val scope = CoroutineScope(Dispatchers.IO)

    // Define SharedFlow for events
    private val _eventFlow = MutableSharedFlow<TCPClientEvent>(replay = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * Connects to the server and initializes input/output streams.
     */
    suspend fun connect() {
        if (serverAddress.isBlank()) {
            close()
            emitError("Unknown Host")
            return
        }

        Log.i("[TCPClient] Connecting to $serverAddress")

        try {
            withTimeoutOrNull(timeout.toLong()) {
                socket = Socket()
                socket.connect(InetSocketAddress(serverAddress, serverPort))

                reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                writer = PrintWriter(socket.getOutputStream(), true)
            } ?: run { 
                throw IOException("Could not connect to $serverAddress")
            }
           
            Log.i("[TCPClient] Connected to $serverAddress, connection status = ${socket.isConnected}")

            emitConnect()
            readStream()
        } catch (exception: Exception) {
            exception.printStackTrace()
            emitError(exception.localizedMessage)
            close()
        }
    }

    private suspend fun emitError(message: String?) {
        _eventFlow.emit(TCPClientEvent.ErrorOccurred(message))
    }

    private suspend fun emitConnect() {
        _eventFlow.emit(TCPClientEvent.Connected)
    }

    private suspend fun emitDisconnect() {
        _eventFlow.emit(TCPClientEvent.Disconnected)
    }

    private suspend fun emitDataReceived(data: String) {
        // consider putting launch here
        _eventFlow.emit(TCPClientEvent.DataReceived(data))
    }

    override fun sendData(data: String) {
        writer.println(data)
    }

    private fun readStream() {
        scope.launch {
            try {
                while (true) {
                    Log.i("[TCPClient] Reading")
                    // Reads the stream from the server;
                    val serverData = reader.readLine()

                    if (serverData == null) {
                        // Server disconnected
                        Log.i("[TCPClient] Server disconnected")
                        emitDisconnect()
                        break
                    }

                    emitDataReceived(serverData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emitDisconnect()
            } finally {
                close()
            }
        }
    }

    suspend fun close() {
        if (this::writer.isInitialized) {
            Log.i("[TCPClient] Closing writer stream")
            
            try {
                writer.close()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        if (this::reader.isInitialized) {
            Log.i("[TCPClient] Closing reader stream")

            try {
                reader.close()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        if (this::socket.isInitialized) {
            Log.i("[TCPClient] Closing socket")

            try {
                socket.close()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        emitDisconnect()

        scope.cancel()
    }
}

// Define events for the TCP client
sealed class TCPClientEvent {
    object Connected : TCPClientEvent()
    object Disconnected : TCPClientEvent()
    data class ErrorOccurred(val message: String?) : TCPClientEvent()
    data class DataReceived(val data: String) : TCPClientEvent()
}