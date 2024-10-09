package game.domain.level.gamehandler.imp

import game.domain.level.behavior.DespawnDestroyableBlocksBehavior
import game.domain.level.behavior.GenerateDestroyableBlocksBehavior
import game.domain.level.behavior.GeneratePlayerBehavior
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.levels.Level
import game.utils.Utility
import java.awt.Image


abstract class DefaultGameHandler(level: Level): GameHandler(level) {
    override fun generatePlayer() = GeneratePlayerBehavior(level.info.playerSpawnCoordinates).invoke()

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