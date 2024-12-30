package game.usecases

import game.JBomb
import game.domain.events.game.EndGameGameEvent
import game.network.events.forward.EndGameEventForwarder
import game.network.gamehandler.ServerGameHandler
import game.network.sockets.TCPServer
import game.properties.RuntimeProperties
import game.utils.dev.Log
import game.utils.dev.suspendCoroutineWithTimeout
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.timeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

/**
 * UseCase to handle the end of the game when the server is hosting the game.
 * It waits for client disconnections and ensures the server is shut down after a delay or when all clients disconnect.
 */
class EndGameAndWaitClientsToDisconnectUseCase : UseCase<Unit> {
    override suspend fun invoke() {
        Log.i("[Endgame] Ending game")

        if (!JBomb.match.isServer) return

        val server = JBomb.match.onlineGameHandler as? ServerGameHandler
        val clientsConnected = server?.server?.clients
        val areClientsConnected = server != null && (clientsConnected?.size ?: 0) > 0

        // Executes the end game callback
        EndGameGameEvent().invoke()

        if (server != null && areClientsConnected) {
            Log.i("[Endgame] Ending game with ${clientsConnected?.size} connected")

            Log.i("[Endgame] Waiting for clients to disconnect...")

            try {
                withTimeout(10_000L) { // Stop collecting after 10 seconds
                    server.server.eventFlow
                        .onSubscription {
                            Log.i("onSubscription")

                            // Notifies all clients when it starts listening
                            // for clients disconnections
                            EndGameEventForwarder().invoke()
                        }
                        .takeWhile { server.server.clients.isNotEmpty() } // Stop collecting when no clients are connected
                        .collect { event ->
                            if (event is TCPServer.ServerEvent.ClientDisconnected) {
                                Log.i("[Endgame] Client disconnected")
                            }
                        }
                }
            } catch (e: TimeoutCancellationException) {
                Log.i("[Endgame] Timeout reached while waiting for disconnection events")
            }

            Log.i("[Endgame] All clients disconnected")
        }

        doDisconnect()
    }

    private suspend fun doDisconnect() {
        Log.i("[Endgame] Disconnecting server...")

        try {
            withTimeout(10_000L) {
                JBomb.match.disconnectOnlineAndStayInGame()
            }
        } catch (e: TimeoutCancellationException) {
            Log.i("[Endgame] Timeout reached while waiting for disconnecting")
        }

        delay(JBomb.Properties.delayStartMatch)

        if (RuntimeProperties.dedicatedServer) {
            JBomb.startLevelByArgs()
        }
    }
}
