package game.usecases

import game.JBomb
import game.domain.level.levels.lobby.WaitingRoomLevel
import game.domain.match.JBombMatch
import game.network.gamehandler.ClientGameHandler
import game.properties.RuntimeProperties

class ConnectToServerUseCase(val server: String): UseCase<Unit> {
    override suspend fun invoke() {
        RuntimeProperties.lastConnectedIp = server
        val tokens = server.split(":").dropLastWhile { it.isEmpty() }
        val ipv4 = tokens[0]
        val port: Int = tokens.getOrNull(1)?.toInt() ?: JBombMatch.port // Default port if parsing fails

        JBomb.startLevel(
            WaitingRoomLevel(),
            ClientGameHandler(ipv4, port)
        )
    }
}