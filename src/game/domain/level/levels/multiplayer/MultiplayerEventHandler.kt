package game.domain.level.levels.multiplayer

import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.utils.dev.Log

class MultiplayerEventHandler: DefaultLevelEventHandler() {
    override fun onKill(attacker: Entity, victim: Entity) {
        val actualAttacker = if (attacker is AbstractExplosion) {
            attacker.state.owner
        } else {
            attacker
        }

        Log.i("$actualAttacker killed $victim")
    }
}