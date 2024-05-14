package game.domain.events.game

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.events.models.GameEvent


class ExplosionLengthPowerUpEvent : GameEvent {
    override fun invoke(arg: Any?) {
        JBomb.match.currentLevel!!.eventHandler.onUpdateBombsLengthEvent(arg as BomberEntity, arg.state.currExplosionLength + 1)
    }
}