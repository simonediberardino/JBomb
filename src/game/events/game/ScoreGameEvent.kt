package game.events.game

import game.Bomberman
import game.events.models.GameEvent


class ScoreGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel.onScoreGameEvent(arg as Int)
    }
}