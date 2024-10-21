package game.network.events.process

import game.JBomb
import game.domain.events.game.EndGameGameEvent
import game.domain.events.models.HttpEvent
import game.utils.dev.Log

class EndGameEventProcessor: HttpEvent {
    override fun invoke(vararg extras: Any) {
        Log.e("Client received EndGameEventProcessor")
        EndGameGameEvent().invoke()
        JBomb.match.disconnectOnlineAndStayInGame()
    }
}