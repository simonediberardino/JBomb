package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class UpdateLocalEnemiesCountGameEvent: GameEvent {
    override fun invoke(arg: Any?) {
        if (Bomberman.isGameEnded)
            return

        Bomberman.match.updateEnemiesAliveCount(count = arg as Int)

        if (arg == 0) {
            AllEnemiesEliminatedGameEvent().invoke(null)
        }
    }
}