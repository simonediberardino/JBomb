package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder


class UpdateCurrentAvailableItemsEvent : GameEvent {
    override fun invoke(vararg arg: Any?) {
        val player = JBomb.match.player ?: return
        val value = arg[0] as Int

        if (value > player.state.maxBombs) {
            return
        }

        JBomb.match.currentLevel.eventHandler.onUpdateCurrentAvailableBombsEvent(value)
        UpdateInfoEventForwarder().invoke(player.toEntityNetwork())
    }
}