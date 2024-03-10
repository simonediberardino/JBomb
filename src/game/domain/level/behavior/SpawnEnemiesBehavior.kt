package game.domain.level.behavior

import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import java.util.*

class SpawnEnemiesBehavior(val startEnemiesCount: Int, val availableEnemies: Array<Class<out Enemy>>): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            spawnEnemies()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }

    private fun spawnEnemies() {
        // Spawn a number of enemies at the start of the game.
        for (i in 0 until startEnemiesCount) {
            // Select a random enemy class from the availableEnemies array.
            val enemyClass = availableEnemies[Random().nextInt(availableEnemies.size)]

            // Create an instance of the enemy class using a constructor that takes a Coordinates object as an argument.
            var enemy: Enemy
            try {
                enemy = enemyClass.getConstructor().newInstance()

                // Spawn the enemy on the game board.
                enemy.logic.spawn(forceSpawn = false, forceCentering = false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}