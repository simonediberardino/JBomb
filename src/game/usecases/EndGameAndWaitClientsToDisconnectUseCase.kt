package game.usecases

import game.JBomb
import game.domain.events.game.EndGameGameEvent
import game.network.events.forward.EndGameEventForwarder
import game.network.gamehandler.ServerGameHandler
import game.network.sockets.TCPServer
import game.properties.RuntimeProperties
import game.utils.dev.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * UseCase to handle the end of the game when the server is hosting the game.
 * It waits for client disconnections and ensures the server is shut down after a delay or when all clients disconnect.
 */
class EndGameAndWaitClientsToDisconnectUseCase : UseCase<Unit> {
    override suspend fun invoke() {
        Log.i("[Endgame] Ending game")

        if (!JBomb.match.isServer) return

        val server = JBomb.match.onlineGameHandler as? ServerGameHandler ?: return
        val areClientsConnected = server.server.clients.isNotEmpty()

        Log.i("[Endgame] Ending game with ${server.server.clients.size} connected")

        if (areClientsConnected) {
            server.server.scope.launch {
                Log.i("[Endgame] Waiting for clients to disconnect...")

                server.server.eventFlow.collect { event ->
                    // If a client disconnects, check if there are no more clients connected.
                    if (event is TCPServer.ServerEvent.ClientDisconnected) {
                        if (server.clientsConnected == 0) {
                            JBomb.scope.launch {
                                doDisconnect()
                            }
                        }
                    }
                }
            }
        }

        // Invoke the in-game end event and forward the end game event to all clients.
        EndGameGameEvent().invoke()

        when {
            !areClientsConnected -> {
                JBomb.scope.launch {
                    doDisconnect()
                }
            }

            else -> {
                Log.i("[Endgame] Sending end game event to clients")
                EndGameEventForwarder().invoke()
            }
        }
    }

    private fun doDisconnect() {
        Log.i("[Endgame] Disconnecting server...")

        // Disconnect the server when there are no clients.
        JBomb.match.disconnectOnlineAndStayInGame()

        if (RuntimeProperties.dedicatedServer) {
            JBomb.startLevelByArgs()
        }
    }
}
