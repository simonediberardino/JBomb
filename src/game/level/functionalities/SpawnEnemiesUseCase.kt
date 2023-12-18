package game.level.functionalities

import game.entity.models.Enemy
import game.entity.models.Entity
import game.level.Level
import game.multiplayer.HostBehavior
import java.lang.reflect.InvocationTargetException
import java.util.*

class SpawnEnemiesUseCase(val startEnemiesCount: Int, val availableEnemies: Array<Class<out Enemy>>): LevelUseCase {
    override fun invoke() {
        val hostBehavior = object: HostBehavior {
            override fun executeHostLogic() {
                spawnEnemies()
            }
        }

        hostBehavior.executeHostLogic()
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
                enemy.spawn(false, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}