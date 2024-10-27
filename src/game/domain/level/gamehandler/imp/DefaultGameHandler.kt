package game.domain.level.gamehandler.imp

import game.domain.level.behavior.DespawnDestroyableBlocksBehavior
import game.domain.level.behavior.GenerateDestroyableBlocksBehavior
import game.domain.level.behavior.GeneratePlayerBehavior
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.geo.Coordinates
import game.utils.Utility
import java.awt.Image


abstract class DefaultGameHandler(level: Level): GameHandler(level) {
    override fun generatePlayer() = GeneratePlayerBehavior().invoke()

    override fun dynamicSpawn(entity: Entity) {
        val spawnpoint = chooseSpawnpointLogic(entity)
        entity.info.position = spawnpoint
        entity.logic.spawn(forceSpawn = false, forceCentering = false)
    }

    override fun chooseSpawnpointLogic(entity: Entity): Coordinates {
        return level.info.customSpawnpoints.random()
    }

    override fun generateDestroyableBlock() {
        DespawnDestroyableBlocksBehavior().invoke()
        GenerateDestroyableBlocksBehavior(level).invoke()
    }

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
}