package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.domain.world.domain.entity.actors.abstracts.base.Entity

class EliminatedEntityGameEvent: GameEvent {
    override fun invoke(vararg arg: Any?) {
        super.invoke(*arg)
        JBomb.match.currentLevel.eventHandler.onEliminated(arg[0] as Entity)
    }
}