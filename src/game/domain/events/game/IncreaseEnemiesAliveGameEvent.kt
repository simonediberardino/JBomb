package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.domain.level.behavior.GameBehavior
import game.network.events.forward.UpdateEnemiesCountEventForwarder
import game.utils.dev.Log

class IncreaseEnemiesAliveGameEvent: GameEvent {
    // Only for server, notifies all clients
    override fun invoke(vararg arg: Any?) {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit {
                return {
                    val enemiesAlive = JBomb.match.enemiesAlive

                    UpdateLocalEnemiesCountGameEvent().invoke(enemiesAlive + 1) // updates locally
                    UpdateEnemiesCountEventForwarder().invoke(enemiesAlive + 1) // notifies clients

                    Log.e("Host is notifying new increased count ${enemiesAlive - 1}")
                }
            }

            override fun clientBehavior(): () -> Unit {
                return {}
            }
        }

        gameBehavior.invoke()
    }
}