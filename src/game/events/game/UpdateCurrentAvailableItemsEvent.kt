package game.events.game

import game.Bomberman
import game.events.models.GameEvent


class UpdateCurrentAvailableItemsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().player ?: return
        Bomberman.getMatch().currentLevel.eventHandler.onUpdateCurrentAvailableBombsEvent(arg as Int)
        Bomberman.getMatch().updateInventoryWeaponController()
    }
}