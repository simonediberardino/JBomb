package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder


class UpdateCurrentAvailableItemsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        val player = Bomberman.match.player ?: return
        val value = arg as Int

        if (value > player.state.maxBombs) {
            return
        }

        Bomberman.match.currentLevel.eventHandler.onUpdateCurrentAvailableBombsEvent(value)
        UpdateInfoEventForwarder().invoke(player.toEntityNetwork())
    }
}