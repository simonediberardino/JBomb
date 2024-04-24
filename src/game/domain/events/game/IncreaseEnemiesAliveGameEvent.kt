package game.domain.events.game

import game.Bomberman
import game.domain.events.models.GameEvent
import game.domain.level.behavior.GameBehavior
import game.network.events.forward.UpdateEnemiesCountEventForwarder

class IncreaseEnemiesAliveGameEvent: GameEvent {
    // Only for server, notifies all clients
    override fun invoke(arg: Any?) {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit {
                return {
                    val enemiesAlive = Bomberman.match.enemiesAlive

                    UpdateLocalEnemiesCountGameEvent().invoke(enemiesAlive + 1) // updates locally
                    UpdateEnemiesCountEventForwarder().invoke(enemiesAlive) // notifies clients
                }
            }

            override fun clientBehavior(): () -> Unit {
                return {}
            }
        }

        gameBehavior.invoke()
    }
}