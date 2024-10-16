package game.domain.level.behavior

import game.JBomb
import game.domain.world.domain.entity.actors.impl.blocks.stone_block.StoneBlock
import game.domain.world.domain.entity.geo.Coordinates
import game.presentation.ui.panels.game.PitchPanel
import javax.swing.JPanel

class GenerateStoneBehavior : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return { generateStone() }
    }

    override fun clientBehavior(): (() -> Unit) {
        return {}
    }

    private fun generateStone() {
        // Set the current x and y coordinates to the top-left corner of the game board.
        var currX = 0
        var currY = PitchPanel.GRID_SIZE

        val mapDimension = JBomb.match.currentLevel.info.mapDimension ?: return

        // Loop through the game board, adding stone blocks at every other grid position.
        while (currY < mapDimension.height - PitchPanel.GRID_SIZE) {
            while (currX < mapDimension.width - PitchPanel.GRID_SIZE && currX + PitchPanel.GRID_SIZE * 2 <= mapDimension.width) {
                // Move the current x coordinate to the next grid position.
                currX += PitchPanel.GRID_SIZE

                // Create a new stone block at the current coordinates and spawn it on the game board.
                StoneBlock(Coordinates(currX, currY)).logic.spawn()

                // Move the current x coordinate to the next grid position.
                currX += PitchPanel.GRID_SIZE
            }
            // Move the current x coordinate back to the left side of the game board.
            currX = 0

            // Move the current y coordinate down to the next row of grid positions.
            currY += PitchPanel.GRID_SIZE * 2
        }
    }
}