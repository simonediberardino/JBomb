package game.events.game

import game.Bomberman
import game.entity.models.BomberEntity
import game.events.models.GameEvent


class ExplosionLengthPowerUpEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel.onUpdateBombsLengthEvent(arg as BomberEntity, arg.currExplosionLength + 1)
    }
}