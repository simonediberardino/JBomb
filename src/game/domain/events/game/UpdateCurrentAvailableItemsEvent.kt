package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder


class UpdateCurrentAvailableItemsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().player ?: return
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateCurrentAvailableBombsEvent(arg as Int)
        Bomberman.getMatch().updateInventoryWeaponController()
        UpdateInfoEventForwarder().invoke(Bomberman.getMatch().player!!.toEntityNetwork())
    }
}