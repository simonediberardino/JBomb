package game.network.sockets

import game.network.callbacks.TCPClientCallback
import game.utils.dev.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class TCPClient(private val serverAddress: String, private val serverPort: Int) : TCPSocket {
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
    fun connect() {
        if (serverAddress.isBlank()) {
            close()
            emitError("Unknown Host")
            return
        }

        Log.e("Connecting to $serverAddress")

        try {
            socket = Socket(serverAddress, serverPort)
            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            writer = PrintWriter(socket.getOutputStream(), true)

            emitConnect()
            readStream()
        } catch (exception: Exception) {
            close()
            emitError(exception.localizedMessage)
        }
    }

    private fun emitError(message: String?) {
        scope.launch {
            _eventFlow.emit(TCPClientEvent.ErrorOccurred(message))
        }
    }

    private fun emitConnect() {
        scope.launch {
            _eventFlow.emit(TCPClientEvent.Connected)
        }
    }

    private fun emitDisconnect() {
        scope.launch {
            _eventFlow.emit(TCPClientEvent.Disconnected)
        }
    }

    private fun emitDataReceived(data: String) {
        scope.launch {
            _eventFlow.emit(TCPClientEvent.DataReceived(data))
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

    fun close() {
        scope.cancel()

        if (this::writer.isInitialized) {
            try {
                writer.close()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        if (this::socket.isInitialized) {
            try {
                socket.close()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

        emitDisconnect()
    }
}

// Define events for the TCP client
sealed class TCPClientEvent {
    object Connected : TCPClientEvent()
    object Disconnected : TCPClientEvent()
    data class ErrorOccurred(val message: String?) : TCPClientEvent()
    data class DataReceived(val data: String) : TCPClientEvent()
}