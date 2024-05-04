package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class DeathGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.match.currentLevel.eventHandler.onDeathGameEvent()
    }
}