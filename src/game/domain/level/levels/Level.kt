package game.domain.level.levels

import game.JBomb
import game.data.data.DataInputOutput
import game.domain.level.behavior.PlayLevelSoundBehavior
import game.domain.level.behavior.PlayLevelSoundTrackBehavior
import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.level.filesystem.LevelFileSystemHandler
import game.domain.level.gamehandler.imp.DefaultStoryLevelHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.info.model.LevelInfo
import game.domain.level.levels.lobby.WorldSelectorLevel
import game.domain.level.levels.world1.*
import game.domain.level.levels.world2.*
import game.utils.dev.Log
import java.util.*
import javax.swing.JPanel

/**
 * Abstract base class representing a game level of a BombermanMatch, handling specific behavior and properties.
 */
abstract class Level {

    abstract val info: LevelInfo
    open val gameHandler: GameHandler
    open val fileSystemHandler: LevelFileSystemHandler = LevelFileSystemHandler()
    open val eventHandler: LevelEventHandler = DefaultLevelEventHandler()

    init {
        gameHandler = DefaultStoryLevelHandler(this)
    }

    lateinit var field: JPanel
    var currentLevelSound: String? = null

    abstract fun endLevel()
    abstract fun onStartLevel()

    open fun start(field: JPanel) {
        Log.e("Starting $this")
        JBomb.match.gameState = true
        this@Level.field = field
        updateLastLevel()
        PlayLevelSoundTrackBehavior(this@Level).invoke()
        PlayLevelSoundBehavior(this@Level).invoke()
        DataInputOutput.getInstance().resetLivesIfNecessary()
        gameHandler.generate()
    }

    private fun updateLastLevel() {
        if (this !is WorldSelectorLevel)
            currLevel = this
    }

    companion object {
        val ID_TO_FIRST_LEVEL_MAP: Map<Int, Class<out Level>> = mapOf(1 to World1Level1::class.java, 2 to World2Level1::class.java)
        val ID_TO_LEVEL: Map<Array<Int>, Class<out Level>> = mapOf(
                arrayOf(1, 0) to World1Arena::class.java,
                arrayOf(2, 0) to World2Arena::class.java,
                arrayOf(1, 1) to World1Level1::class.java,
                arrayOf(1, 2) to World1Level2::class.java,
                arrayOf(1, 3) to World1Level3::class.java,
                arrayOf(1, 4) to World1Level4::class.java,
                arrayOf(1, 5) to World1Level5::class.java,
                arrayOf(2, 1) to World2Level1::class.java,
                arrayOf(2, 2) to World2Level2::class.java,
                arrayOf(2, 3) to World2Level3::class.java,
                arrayOf(2, 4) to World2Level4::class.java,
                arrayOf(2, 5) to World2Level5::class.java
        )

        fun findLevel(worldId: Int, levelId: Int): Optional<Class<out Level>> {
            return Level.ID_TO_LEVEL.entries
                    .firstOrNull { (key, _) -> key[0] == worldId && key[1] == levelId }
                    ?.value
                    ?.let { Optional.of(it) }
                    ?: Optional.empty()
        }

        var currLevel: Level? = null
            private set
    }
}
