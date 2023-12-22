package game.http.sockets

import game.http.callbacks.TCPClientCallback
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class TCPClient(private val serverAddress: String,
                private val serverPort: Int
) : TCPSocket {
    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter
    private val listeners: MutableSet<TCPClientCallback> = mutableSetOf()

    fun connect() {
        socket = Socket(serverAddress, serverPort)
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        writer = PrintWriter(socket.getOutputStream(), true)

        for (listener in listeners) {
            listener.onConnect()
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

    fun receiveData(): String {
        return reader.readLine()
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