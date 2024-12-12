package game.domain.level.levels

import game.JBomb
import game.data.data.DataInputOutput
import game.domain.events.game.RoundPassedGameEvent
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.level.behavior.GameBehavior
import game.domain.level.behavior.RespawnDeadPlayersBehavior
import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.level.eventhandler.model.LevelEventHandler
import game.localization.Localization
import game.localization.Localization.WORLD_NAME
import game.presentation.ui.pages.game_over.ArenaGameOverPanel
import game.presentation.ui.pages.game_over.GameOverPanel
import game.properties.RuntimeProperties
import game.utils.ui.ToastUtils
import java.awt.event.ActionEvent
import javax.swing.Timer

abstract class StoryLevel : Level() {
    override val eventHandler: LevelEventHandler
        get() = object : DefaultLevelEventHandler() {
            override fun onEndGame() {
                super.onEndGame()
                if (!RuntimeProperties.dedicatedServer) {
                    JBomb.showActivity(GameOverPanel::class.java)
                }
            }
        }

    override fun endLevel() {
        try {
            DataInputOutput.getInstance().setLastLevel(info.nextLevel!!.getConstructor().newInstance())
            DataInputOutput.getInstance().increaseRounds()
            DataInputOutput.getInstance().updateStoredPlayerData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        RoundPassedGameEvent().invoke(null)
    }

    override fun toString(): String {
        val formattedString = Localization.get(WORLD_NAME)
        return formattedString
                .replace("%world_id%", info.worldId.toString())
                .replace("%level_id%", info.levelId.toString())
    }

    override fun onStartLevel() {}
}