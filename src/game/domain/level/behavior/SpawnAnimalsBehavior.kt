package game.domain.level.behavior

import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.utils.dev.Log
import java.util.*

class SpawnAnimalsBehavior(
        private val startAnimalsCount: Int,
        private val availableAnimals: Array<Class<out AnimalEntity>>
) : GameBehavior() {
    override fun hostBehavior(): () -> Unit = {
        for (i in 0 until startAnimalsCount) {
            // Select a random enemy class from the availableEnemies array.
            val animalClass = availableAnimals[Random().nextInt(availableAnimals.size)]

            var animal: AnimalEntity
            try {
                animal = animalClass.getConstructor().newInstance()

                // Spawn the enemy on the game board.
                animal.logic.spawn(forceSpawn = true, forceCentering = false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun clientBehavior(): () -> Unit = {}
}