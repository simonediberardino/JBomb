package game.level

import game.data.DataInputOutput
import game.entity.blocks.InvisibleBlock
import game.entity.enemies.boss.Boss
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.localization.Localization
import game.powerups.portal.Portal
import game.powerups.portal.World1Portal
import game.powerups.portal.World2Portal
import game.powerups.portal.WorldPortal
import game.ui.panels.game.PitchPanel
import java.awt.Dimension
import java.lang.reflect.InvocationTargetException
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.swing.JPanel

class WorldSelectorLevel : StoryLevel() {
    override val worldId: Int
        get() = 0
    override val levelId: Int
        get() = 0

    override val startEnemiesCount: Int
        get() {
            return 0
        }

    override val maxDestroyableBlocks: Int
        get() = 0
    override val nextLevel: Class<out Level?>?
        get() = null

    override val availableEnemies: Array<Class<out Enemy>>
        get() {
            return arrayOf()
        }

    override val boss: Boss?
        get() = null

    public override fun spawnEnemies() {}
    fun generateInvisibleBlock() {
        //BORDER INVISIBLE BLOCKS COLUMNS
        run {
            var i = 0
            while (i < PitchPanel.DIMENSION.getWidth() / PitchPanel.GRID_SIZE - 1) {
                InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(i, 0))).spawn()
                InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(i, (PitchPanel.DIMENSION.getHeight() / PitchPanel.GRID_SIZE - 1).toInt()))).spawn()
                i++
            }
        }
        //ROWS
        var i = 0
        while (i < PitchPanel.DIMENSION.getHeight() / PitchPanel.GRID_SIZE - 1) {
            InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(0, i))).spawn()
            InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension((PitchPanel.DIMENSION.getWidth() / PitchPanel.GRID_SIZE).toInt() - 1, i))).spawn()
            i++
        }
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(1, 1), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(2, 1), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(3, 1), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(4, 1), -PitchPanel.GRID_SIZE / 6, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(6, 1), -PitchPanel.GRID_SIZE / 3, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(11, 1), -PitchPanel.GRID_SIZE / 3, -PitchPanel.GRID_SIZE / 2)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(1, 7), +PitchPanel.GRID_SIZE / 2, -PitchPanel.GRID_SIZE / 2)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(2, 9), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(2, 8), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(8, 10), 0, -PitchPanel.GRID_SIZE / 2)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(9, 9), -PitchPanel.GRID_SIZE / 3, -PitchPanel.GRID_SIZE / 6)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(9, 8), PitchPanel.GRID_SIZE / 6, -PitchPanel.GRID_SIZE / 6)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(9, 7), PitchPanel.GRID_SIZE / 6, -PitchPanel.GRID_SIZE / 6)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(10, 6), PitchPanel.GRID_SIZE * 2 / 3, -PitchPanel.GRID_SIZE * 2 / 3)).spawn(true, false)


        //OUT OF BOUNDS BARRIER BLOCKS
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(10, 9), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(10, 8), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(11, 9), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(11, 8), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(10, 7), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(11, 7), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(11, 7), 0, -PitchPanel.GRID_SIZE / 2)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(10, 7), 0, -PitchPanel.GRID_SIZE / 2)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(9, 7), PitchPanel.GRID_SIZE / 6, -PitchPanel.GRID_SIZE / 2)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(1, 8), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(1, 9), 0, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(1, 2), PitchPanel.GRID_SIZE / 3, -PitchPanel.GRID_SIZE / 3)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(3, 6), PitchPanel.GRID_SIZE / 6, +PitchPanel.GRID_SIZE / 3)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(7, 4), PitchPanel.GRID_SIZE / 6, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(7, 5), PitchPanel.GRID_SIZE / 6, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(8, 5), -PitchPanel.GRID_SIZE / 2, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(8, 4), -PitchPanel.GRID_SIZE / 2, 0)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(11, 3), -PitchPanel.GRID_SIZE / 2, +PitchPanel.GRID_SIZE / 3)).spawn(true, false)
        InvisibleBlock(Coordinates.fromRowAndColumnsToCoordinates(Dimension(10, 2), -PitchPanel.GRID_SIZE / 6, +PitchPanel.GRID_SIZE / 2)).spawn(true, false)
    }

    override fun generateEntities(jPanel: JPanel) {
        generateInvisibleBlock()
        generatePlayer()
        generatePortals()
    }

    private fun generatePortals() {
        val lastWorldId = 1.coerceAtLeast(DataInputOutput.getInstance().lastWorldId)

        val worldPortals = WORLDS_ID_TO_PORTAL
                .filterKeys { it <= lastWorldId }
                .values
                .mapNotNull {
                    try {
                        it.getDeclaredConstructor().newInstance() as? Portal
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

        worldPortals.forEach { it.spawn(true, false) }
    }

    override val playerSpawnCoordinates: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(5, 2), 0, 0)
    override val maxBombs: Int
        // This method returns the maximum number of bombs that a player can have at one time.
        get() = 0

    override fun toString(): String {
        return Localization.get(Localization.ISLAND)
    }

    companion object {
        val WORLDS_ID_TO_PORTAL = hashMapOf(
                1 to World1Portal::class.java,
                2 to World2Portal::class.java
        )
    }
}