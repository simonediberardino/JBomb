package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder


class UpdateCurrentAvailableItemsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.match.player ?: return
        Bomberman.match.currentLevel!!.eventHandler.onUpdateCurrentAvailableBombsEvent(arg as Int)
        Bomberman.match.updateInventoryWeaponController()
        UpdateInfoEventForwarder().invoke(Bomberman.match.player!!.toEntityNetwork())
    }
}