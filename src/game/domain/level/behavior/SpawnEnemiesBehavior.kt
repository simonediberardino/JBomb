package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.geo.Coordinates
import kotlinx.coroutines.launch
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
            val enemy: Enemy
            var maxAttempts = 5
            try {
                enemy = enemyClass.getConstructor().newInstance()
                val coordinate = Coordinates.generateCoordinatesAwayFromPlayers( maxAttempts)
                enemy.logic.move(coordinate)
                // Spawn the enemy on the game board.
                enemy.logic.spawn()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}