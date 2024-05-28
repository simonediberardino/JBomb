package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent

class UpdateLocalEnemiesCountGameEvent: GameEvent {
    override fun invoke(vararg arg: Any?) {
        if (JBomb.isGameEnded)
            return

        JBomb.match.updateEnemiesAliveCount(count = arg[0] as Int)

        if (arg[0] == 0) {
            AllEnemiesEliminatedGameEvent().invoke(null)
        }
    }
}