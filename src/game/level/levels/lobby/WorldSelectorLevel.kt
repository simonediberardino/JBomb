package game.level.levels.lobby

import game.data.DataInputOutput
import game.entity.blocks.InvisibleBlock
import game.entity.enemies.boss.Boss
import game.entity.models.Coordinates
import game.entity.models.Enemy
import game.actorbehavior.GameBehavior
import game.actorbehavior.GeneratePlayerBehavior
import game.level.gamehandler.imp.DefaultGameHandler
import game.level.gamehandler.model.GameHandler
import game.level.info.imp.IslandLevelInfo
import game.level.info.imp.LobbyLevelInfo
import game.level.info.model.DefaultLevelInfo
import game.level.info.model.LevelInfo
import game.level.levels.Level
import game.level.levels.StoryLevel
import game.localization.Localization
import game.powerups.portal.Portal
import game.powerups.portal.World1Portal
import game.powerups.portal.World2Portal
import game.ui.panels.game.PitchPanel
import java.awt.Dimension

class WorldSelectorLevel : IslandLevel() {
    override fun generateLevel() {
        generatePortals()
    }

    private fun generatePortals() {
        val lastWorldId = 1.coerceAtLeast(DataInputOutput.getInstance().lastWorldId)

        val worldPortals = WORLDS_ID_TO_PORTAL
                .filterKeys { it <= lastWorldId }
                .values
                .mapNotNull {
                    try {
                        it.getDeclaredConstructor().newInstance()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

        worldPortals.forEach { it.spawn(true, false) }
    }

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