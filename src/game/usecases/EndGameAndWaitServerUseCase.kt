package game.usecases

import game.JBomb
import game.domain.events.game.EndGameGameEvent
import game.network.events.forward.EndGameEventForwarder
import game.network.gamehandler.ServerGameHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class EndGameAndWaitServerUseCase: UseCase<Unit> {
    override suspend fun invoke() {
        EndGameGameEvent().invoke()
        EndGameEventForwarder().invoke()

        if (!JBomb.match.isServer)
            return

        val server = JBomb.match.onlineGameHandler as? ServerGameHandler ?: return

        // Use flow to listen for changes in clientsConnected
        server.observeClientsConnected()
            .filter { it == 0 } // Only proceed when no clients are connected
            .first()            // Wait until the condition is met

        JBomb.match.disconnectOnlineAndStayInGame()
    }

    private fun ServerGameHandler.observeClientsConnected(): Flow<Int> = flow {
        while (clientsConnected > 0) {
            emit(clientsConnected)   // Emit current client count
            delay(1000)              // Delay to avoid tight polling, this is suspendable
        }
        emit(clientsConnected)       // Emit the final count (which should be 0)
    }
}