package game.domain.level.levels.multiplayer

import game.domain.level.behavior.GenerateLevelFromXmlBehavior
import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.levels.Level
import game.domain.level.levels.level_editor.LevelGenerationData
import game.usecases.ParseLevelEditorDataUseCase
import game.utils.dev.XMLUtils
import game.utils.file_system.Paths.mapsXml
import kotlinx.coroutines.runBlocking

abstract class MultiplayerLevel : Level() {
    override val gameHandler: GameHandler = MultiplayerGameHandler(this)
    override val eventHandler: LevelEventHandler = MultiplayerEventHandler()
    abstract val mapIdXml: String

    val levelGenerationData: LevelGenerationData by lazy {
        runBlocking {
            ParseLevelEditorDataUseCase(XMLUtils.readXml("$mapsXml/$mapIdXml")).invoke()!!
        }
    }

    override fun onStartLevel() {

    }

    override fun endLevel() {

    }
}