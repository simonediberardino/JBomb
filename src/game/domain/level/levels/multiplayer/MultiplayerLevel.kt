package game.domain.level.levels.multiplayer

import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.info.model.LevelInfo
import game.domain.level.levels.Level

abstract class MultiplayerLevel: Level() {
    override val gameHandler: GameHandler = MultiplayerGameHandler(this)
    override val eventHandler: LevelEventHandler = MultiplayerEventHandler()

    override fun endLevel() {

    }

    override fun onStartLevel() {
    }
}