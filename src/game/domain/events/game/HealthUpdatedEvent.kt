package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent

class HealthUpdatedEvent : GameEvent {
    override fun invoke(arg: Any?) {
        try {
            val playerHp = Bomberman.match.player?.state?.hp ?: 0
            Bomberman.match.inventoryElementControllerHp.setNumItems(playerHp)
        } catch (ignored: UninitializedPropertyAccessException) {
        }
    }
}