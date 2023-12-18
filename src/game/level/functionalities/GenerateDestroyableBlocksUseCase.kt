package game.level.functionalities

import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.models.Coordinates
import game.level.Level
import game.multiplayer.HostBehavior
import game.powerups.portal.EndLevelPortal
import game.ui.panels.game.PitchPanel

class GenerateDestroyableBlocksUseCase(val level: Level): LevelUseCase {
    override fun invoke() {
        val hostBehavior = object: HostBehavior {
            override fun executeHostLogic() {
                generateDestroyableBlocks()
            }
        }

        hostBehavior.executeHostLogic()
    }

    fun generateDestroyableBlocks() {
        var block = DestroyableBlock(Coordinates(0, 0))

        // Initialize a counter for the number of destroyable blocks spawned.
        var i = 0

        // Loop until the maximum number of destroyable blocks has been spawned.
        while (i < level.maxDestroyableBlocks) {
            // If the current destroyable block has not been spawned, generate new coordinates for it and spawn it on the game board.
            if (!block.isSpawned) {
                block.coords = Coordinates.generateCoordinatesAwayFrom(Bomberman.getMatch().player.coords, PitchPanel.GRID_SIZE * 2)
                block.spawn()

                // Force the first spawned block to have the End level portal
                if (i == 0 && !level.isLastLevelOfWorld && !level.isArenaLevel) {
                    block.powerUpClass = EndLevelPortal::class.java
                } else {
                    block.powerUpClass = level.randomPowerUpClass
                }
            } else {
                block = DestroyableBlock(Coordinates(0, 0))
                i++
            }
        }
    }
}