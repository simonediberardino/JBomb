package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent


class ScoreGameEvent : GameEvent {
    override fun invoke(vararg arg: Any?) {
        JBomb.match.currentLevel.let {
            if (it.info.scoreEnabled)
                it.eventHandler.onScoreGameEventLocal(arg[0] as Int)
        }
    }
}