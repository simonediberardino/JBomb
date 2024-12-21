package game.domain.level.gamehandler.model

import game.JBomb
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.geo.Coordinates
import game.utils.dev.Log
import java.awt.Image

abstract class GameHandler(protected val level: Level) {
    open fun generate() {
        generateStone()
        generatePlayer()
        startLevel()
    }

    fun startLevel() {
        Log.i("[GameHandler] step 1: generateDestroyableBlock")
        generateDestroyableBlock()

        Log.i("[GameHandler] step 2: spawnMysteryBox")
        spawnMysteryBox()

        Log.i("[GameHandler] step 3: spawnBoss")
        spawnBoss()

        Log.i("[GameHandler] step 4: spawnEnemies")
        spawnEnemies()

        Log.i("[GameHandler] step 5: spawnAnimals")
        spawnAnimals()

        Log.i("[GameHandler] step 6: onStartLevel")
        level.onStartLevel()

        Log.i("[GameHandler] step 7: notifyEntities")
        notifyEntities()

        Log.i("[GameHandler] finish")
    }

    private fun notifyEntities() {
        JBomb.match.getEntities().forEach {
            it.logic.onGameStarted()
        }
    }

    abstract fun generateStone()
    abstract fun generatePlayer()
    abstract fun generateDestroyableBlock()
    abstract fun spawnBoss()
    abstract fun spawnEnemies()
    abstract fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>, count: Int)
    abstract val borderImages: Array<Image?>
    abstract fun spawnMysteryBox()
    abstract fun canGameBeEnded(): Boolean
    abstract fun spawnAnimals(availableAnimals: Array<Class<out AnimalEntity>>)
    abstract fun spawnAnimals()
    abstract fun dynamicSpawn(entity: Entity)
    abstract fun chooseSpawnpointLogic(entity: Entity): Coordinates
}