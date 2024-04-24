package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class UpdateLocalEnemiesCountGameEvent: GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.match.updateEnemiesAliveCount(count = arg as Int)

        if (arg == 0) {
            AllEnemiesEliminatedGameEvent().invoke(null)
        }
    }
}