package game.engine.events.game

import game.Bomberman
import game.engine.events.models.GameEvent
import game.network.events.forward.UpdateInfoEventForwarder


class UpdateCurrentAvailableItemsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().player ?: return
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateCurrentAvailableBombsEvent(arg as Int)
        Bomberman.getMatch().updateInventoryWeaponController()
        UpdateInfoEventForwarder().invoke(Bomberman.getMatch().player!!.toDto())
    }
}