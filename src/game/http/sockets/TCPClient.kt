package game.http.sockets

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class TCPClient(private val serverAddress: String, private val serverPort: Int) : TCPSocket {
    private lateinit var socket: Socket
    private lateinit var reader: BufferedReader
    private lateinit var writer: PrintWriter

    fun connect() {
        socket = Socket(serverAddress, serverPort)
        reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        writer = PrintWriter(socket.getOutputStream(), true)
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
    }
}