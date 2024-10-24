package game.usecases

import game.JBomb
import game.domain.events.game.EndGameGameEvent
import game.network.events.forward.EndGameEventForwarder
import game.network.gamehandler.ServerGameHandler
import game.network.sockets.TCPServer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * UseCase to handle the end of the game when the server is hosting the game.
 * It waits for client disconnections and ensures the server is shut down after a delay or when all clients disconnect.
 */
class EndGameAndWaitClientsToDisconnectUseCase : UseCase<Unit> {
    override suspend fun invoke() {
        if (!JBomb.match.isServer) return

        val server = JBomb.match.onlineGameHandler as? ServerGameHandler ?: return

        // Launch a coroutine to monitor server events for client disconnections.
        server.server.scope.launch {
            server.server.eventFlow.collect { event ->
                // If a client disconnects, check if there are no more clients connected.
                if (event is TCPServer.ServerEvent.ClientDisconnected) {
                    if (server.clientsConnected == 0) {
                        // Disconnect the server when there are no clients.
                        JBomb.match.disconnectOnlineAndStayInGame()
                    }
                }
            }
        }

        // Invoke the in-game end event and forward the end game event to all clients.
        EndGameGameEvent().invoke()
        EndGameEventForwarder().invoke()
    }
}
