package game.network.usecases

import game.usecases.UseCase
import game.utils.dev.Log
import java.net.Socket
import kotlin.system.measureTimeMillis

class PingServerUseCase(
    private val host: String,
    private val port: Int,
    private val timeout: Int = 2000
) : UseCase<Long> {
    override suspend fun invoke(): Long {
        val socket = Socket()

        return try {
            // Attempt to create a socket connection to the host and port
            Log.i("Pinging $host:$port")

            val ping = measureTimeMillis {
                socket.connect(java.net.InetSocketAddress(host, port), timeout)
            }

            if (!socket.isConnected) {
                println("$host:$port refused connection")
                return -1
            }

            Log.i("Ping to $host:$port is ${ping}ms")
            ping // Connection successful, the server is reachable
        } catch (e: Exception) {
            Log.i("$host:$port is not reachable, ${e.message}")
            -1 // Timeout reached, server is not reachable
        } finally {
            try {
                socket.close()
            } catch (ignored: Exception) {
                // Ignoring exception while closing the socket
            }
        }
    }
}