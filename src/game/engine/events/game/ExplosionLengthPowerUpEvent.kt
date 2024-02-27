package game.engine.events.game

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.events.models.GameEvent


class ExplosionLengthPowerUpEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel!!.eventHandler.onUpdateBombsLengthEvent(arg as BomberEntity, arg.currExplosionLength + 1)
    }
}