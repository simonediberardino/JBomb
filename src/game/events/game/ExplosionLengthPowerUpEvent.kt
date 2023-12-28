package game.events.game

import game.Bomberman
import game.entity.player.BomberEntity
import game.events.models.GameEvent


class ExplosionLengthPowerUpEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateBombsLengthEvent(arg as BomberEntity, arg.currExplosionLength + 1)
    }
}