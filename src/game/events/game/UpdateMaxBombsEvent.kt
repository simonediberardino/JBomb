package game.events.game

import game.Bomberman
import game.events.models.GameEvent


class UpdateMaxBombsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel.onUpdateMaxBombsGameEvent(arg as Int)
    }
}