package game.engine.level.gamehandler.model

import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.level.levels.Level
import java.awt.Image

abstract class GameHandler(protected val level: Level) {
    open fun generate() {
        generateStone()
        generatePlayer()
        startLevel()
    }

    fun startLevel() {
        generateDestroyableBlock()
        spawnBoss()
        spawnEnemies()
        level.onStartLevel()
    }

    abstract fun generateStone()
    abstract fun generatePlayer()
    abstract fun generateDestroyableBlock()
    abstract fun spawnBoss()
    abstract fun spawnEnemies()
    abstract fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>)
    abstract val borderImages: Array<Image?>
}