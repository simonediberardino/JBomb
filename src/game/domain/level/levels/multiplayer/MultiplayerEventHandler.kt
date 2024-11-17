package game.domain.level.levels.multiplayer

import game.JBomb
import game.domain.events.game.MultiplayerKillsEvent
import game.domain.level.behavior.RespawnDeadPlayerBehavior
import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.localization.Localization
import game.presentation.ui.pages.multiplayer.GameEndedMultiplayerPanel
import game.utils.dev.Log
import game.utils.ui.ToastUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MultiplayerEventHandler : DefaultLevelEventHandler() {
    override fun onEliminated(entity: Entity) {
        if (entity !is BomberEntity) return

        JBomb.match.scope.launch {
            delay(2_000)

            if (!JBomb.match.gameState)
                return@launch

            if (!entity.state.isSpawned) {
                RespawnDeadPlayerBehavior(
                    id = entity.info.id,
                    clazz = entity.javaClass,
                    entity = entity
                ).invoke()
            }
        }
    }

    override fun onKill(attacker: Entity, victim: Entity) {
        val actualAttacker = if (attacker is AbstractExplosion) {
            attacker.state.owner
        } else {
            attacker
        }

        if (actualAttacker !is BomberEntity || victim !is BomberEntity) {
            return
        }

        if (actualAttacker != victim) {
            when (JBomb.match.player) {
                actualAttacker -> victim.properties.name?.let { Localization.get(Localization.YOU_KILLED).replace("%name%", it) }
                    ?.let {
                        //ToastUtils.show(it, false)
                    }
                victim -> {
                    actualAttacker.properties.name?.let { Localization.get(Localization.KILLED_BY).replace("%name%", it) }
                        ?.let {
                            //ToastUtils.show(it, false)
                        }
                }
            }

            MultiplayerKillsEvent(actualAttacker).invoke()
        }

        Log.i("$actualAttacker killed $victim")
    }


    override fun onEndGame() {
        GameEndedMultiplayerPanel.showSummary()
    }
}