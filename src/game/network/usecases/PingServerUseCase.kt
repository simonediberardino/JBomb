package game.network.usecases

import game.usecases.UseCase
import game.utils.dev.Log
import java.net.Socket
import kotlin.system.measureTimeMillis

class PingServerUseCase(private val host: String, val port: Int, private val timeout: Int = 2000): UseCase<Int> {
    override suspend fun invoke(): Int = try {
        // Attempt to create a socket connection to the host and port
        Log.i("Pinging $host:$port")
        val ping = measureTimeMillis {
            Socket().use { socket ->
                socket.connect(java.net.InetSocketAddress(host, port), timeout)
            }
        }.toInt()

        ping // Connection successful, the server is reachable
    } catch (e: Exception) {
        -1 // Timeout reached, server is not reachable
    }
}