package game.network.events.process

import game.JBomb
import game.domain.events.game.EndGameGameEvent
import game.domain.events.models.HttpEvent
import game.utils.dev.Log
import kotlinx.coroutines.runBlocking

class EndGameEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        Log.i("Client received EndGameEventProcessor")
        EndGameGameEvent().invoke()
        runBlocking {
            JBomb.match.disconnectOnlineAndStayInGame()
        }
    }
}