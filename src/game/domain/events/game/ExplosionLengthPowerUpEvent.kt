package game.domain.events.game

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.events.models.GameEvent


class ExplosionLengthPowerUpEvent : GameEvent {
    override fun invoke(vararg arg: Any?) {
        var bomberEntity = arg[0] as BomberEntity
        JBomb.match
                .currentLevel
                .eventHandler
                .onUpdateBombsLengthEvent(
                        bomberEntity.state.currExplosionLength + 1,
                        true
                )
    }
}