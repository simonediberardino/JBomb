package game.usecases

import game.domain.match.JBombMatch
import game.network.usecases.PingServerUseCase
import game.properties.RuntimeProperties

class ReconnectToServerUseCase: UseCase<Boolean> {
    override suspend fun invoke(): Boolean {
        val server = RuntimeProperties.lastConnectedIp
        if (server.isEmpty())
            return false

        val tokens = server.split(":").dropLastWhile { it.isEmpty() }
        val ipv4 = tokens[0]
        val port: Int = tokens.getOrNull(1)?.toInt() ?: JBombMatch.port // Default port if parsing fails

        val pingResult = PingServerUseCase(ipv4, port, 5_000).invoke()

        if (!pingResult)
            return false

        ConnectToServerUseCase(RuntimeProperties.lastConnectedIp).invoke()
        return true
    }
}