package game.domain.level.gamehandler.imp

import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.level.levels.Level
import game.domain.level.behavior.*
import game.domain.level.gamehandler.model.GameHandler
import game.utils.Utility
import java.awt.Image

open class DefaultGameHandler(level: Level): GameHandler(level) {
    override val borderImages: Array<Image?>
        get() {
            val SIDES = 4
            val pitch = arrayOfNulls<Image>(SIDES)
            for (i in 0 until SIDES) {
                val path = level.fileSystemHandler.getImageForCurrentLevel("border_$i.png")
                pitch[i] = Utility.loadImage(path)
            }
            return pitch
        }

    override fun generateStone() = GenerateStoneBehavior(level.field).invoke()

    override fun generatePlayer() = GeneratePlayerBehavior(level.info.playerSpawnCoordinates).invoke()

    override fun generateDestroyableBlock() {
        DespawnDestroyableBlocksBehavior().invoke()
        GenerateDestroyableBlocksBehavior(level).invoke()
    }

    override fun spawnBoss() {
        SpawnBossBehavior(level.info.boss ?: return).invoke()
    }

    override fun spawnEnemies() = spawnEnemies(level.info.availableEnemies)

    override fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>) =
            SpawnEnemiesBehavior(level.info.startEnemiesCount, availableEnemies).invoke()
}