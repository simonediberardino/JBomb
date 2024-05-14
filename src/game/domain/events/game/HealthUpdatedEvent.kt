package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent

class HealthUpdatedEvent : GameEvent {
    override fun invoke(arg: Any?) {
        try {
            val playerHp = JBomb.match.player?.state?.hp ?: 0
            JBomb.match.inventoryElementControllerHp.setNumItems(playerHp)
        } catch (ignored: UninitializedPropertyAccessException) {
        }
    }
}