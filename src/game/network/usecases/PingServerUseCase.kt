package game.network.usecases

import game.usecases.UseCase
import game.utils.dev.Log
import java.net.Socket

class PingServerUseCase(private val host: String, val port: Int, private val timeout: Int = 2000): UseCase<Boolean> {
    override suspend fun invoke(): Boolean = try {
        // Attempt to create a socket connection to the host and port
        Log.e("Pinging $host:$port")
        Socket().use { socket ->
            socket.connect(java.net.InetSocketAddress(host, port), timeout)
        }
        true // Connection successful, the server is reachable
    } catch (e: Exception) {
        false // Timeout reached, server is not reachable
    }
}