package game.domain.level.levels.level_editor

import game.JBomb
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.info.model.LevelInfo
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.impl.blocks.base_block.Block
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.powerups.BlockMoverPowerUp
import game.localization.Localization
import java.awt.Dimension

data class LevelGenerationData(
    val mapDimension: Dimension?,
    val data: Map<String, List<Coordinates>>
)

class LevelEditor(private val levelData: LevelGenerationData?): Level() {
    override val info: LevelInfo = object: LevelEditorLevelInfo(this) {
        override val debug: Boolean = false
    }

    override val gameHandler: GameHandler = LevelEditorGameHandler(this, levelData)

    override fun endLevel() {

    }

    override fun onStartLevel() {
        handleBlockMover()
    }

    private fun handleBlockMover() {
        val player = JBomb.match.player ?: return

        JBomb.JBombFrame.matchPanel.refreshPowerUps(listOf(BlockMoverPowerUp::class.java))
        player.logic.addClassInteractWithMouseDrag(Block::class.java)
        player.logic.addClassInteractWithMouseClick(Block::class.java)
    }

    fun updateMapDimension(mapDimension: Dimension) {
        info.mapDimension = mapDimension
    }

    override fun toString(): String {
        return Localization.get(Localization.LEVEL_EDITOR_NAME)
    }
}