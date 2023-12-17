package game.events.game

import game.Bomberman
import game.events.models.GameEvent


class UpdateCurrentAvailableBombsEvent : GameEvent {
    override fun invoke(arg: Any?) {
        if (Bomberman.getMatch().player == null) return
        Bomberman.getMatch().currentLevel.onUpdateCurrentAvailableBombsEvent(arg as Int)
        Bomberman.getMatch().updateInventoryWeaponController()
    }
}