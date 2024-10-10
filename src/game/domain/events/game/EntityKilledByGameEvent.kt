package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.domain.level.behavior.GameBehavior
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.network.events.forward.EntityKilledByEventForwarder
import game.network.events.forward.UpdateEnemiesCountEventForwarder
import game.utils.dev.Log

/**
 * Called when a character kills another character
 * Can be processed by the server or received and processed by a client.
 */
class EntityKilledByGameEvent: GameEvent {
    override fun invoke(vararg arg: Any?) {
        val victim = arg[0] as Character
        val attacker = arg[1] as Entity

        val realAttacker = if (attacker is AbstractExplosion) {
            attacker.state.owner
        } else {
            attacker
        } as Character

        Log.e("EntityKilledByGameEvent: processing $realAttacker $victim")
        JBomb.match.currentLevel.eventHandler.onKill(realAttacker, victim)

        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit = {
                Log.e("EntityKilledByGameEvent: sending")

                EntityKilledByEventForwarder().invoke(victim, realAttacker)
            }

            override fun clientBehavior(): () -> Unit = {}
        }

        gameBehavior.invoke()
    }
}