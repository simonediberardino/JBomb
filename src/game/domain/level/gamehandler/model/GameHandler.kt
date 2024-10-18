package game.domain.level.gamehandler.model

import game.JBomb
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import java.awt.Image

abstract class GameHandler(protected val level: Level) {
    open fun generate() {
        generateStone()
        generatePlayer()
        startLevel()
    }

    fun startLevel() {
        generateDestroyableBlock()
        spawnMysteryBox()
        spawnBoss()
        spawnEnemies()
        spawnAnimals()
        level.onStartLevel()
        JBomb.match.onStartGame()
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
}