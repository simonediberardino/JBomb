package game.level.actorbehavior

import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.models.Coordinates
import game.level.Level
import game.powerups.portal.EndLevelPortal
import game.ui.panels.game.PitchPanel

class GenerateDestroyableBlocksBehavior(val level: Level): GameBehavior {
    override fun hostBehavior(): () -> Unit {
        return {
            generateDestroyableBlocks()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }

    private fun generateDestroyableBlocks() {
        var block = DestroyableBlock(Coordinates(0, 0))

        // Initialize a counter for the number of destroyable blocks spawned.
        var i = 0

        val levelInfo = level.info;

        // Loop until the maximum number of destroyable blocks has been spawned.
        while (i < levelInfo.maxDestroyableBlocks) {
            // If the current destroyable block has not been spawned, generate new coordinates for it and spawn it on the game board.
            if (!block.isSpawned) {
                block.coords = Coordinates.generateCoordinatesAwayFrom(Bomberman.getMatch().player?.coords, PitchPanel.GRID_SIZE * 2)
                block.spawn()

                // Force the first spawned block to have the End level portal
                if (i == 0 && !level.info.isLastLevelOfWorld && !levelInfo.isArenaLevel) {
                    block.powerUpClass = EndLevelPortal::class.java
                } else {
                    block.powerUpClass = levelInfo.randomPowerUpClass
                }
            } else {
                block = DestroyableBlock(Coordinates(0, 0))
                i++
            }
        }
    }

}