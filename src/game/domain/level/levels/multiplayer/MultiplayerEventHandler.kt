package game.domain.level.levels.multiplayer

import game.JBomb
import game.domain.events.game.MultiplayerScoreEvent
import game.domain.events.game.ScoreGameEvent
import game.domain.level.behavior.RespawnDeadPlayerBehavior
import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.utils.dev.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MultiplayerEventHandler : DefaultLevelEventHandler() {
    override fun onKill(attacker: Entity, victim: Entity) {
        val actualAttacker = if (attacker is AbstractExplosion) {
            attacker.state.owner
        } else {
            attacker
        }

        if (actualAttacker !is BomberEntity) {
            return
        }

        MultiplayerScoreEvent(actualAttacker, 100).invoke()

        Log.i("$actualAttacker killed $victim")

        JBomb.match.scope.launch {
            delay(2_000)

            if (!JBomb.match.gameState)
                return@launch

            if (!victim.state.isSpawned) {
                RespawnDeadPlayerBehavior(
                        id = victim.info.id,
                        clazz = victim.javaClass,
                        entity = victim
                ).invoke()
            }
        }
    }
}