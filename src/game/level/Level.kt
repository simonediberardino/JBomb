package game.level

import game.Bomberman
import game.data.DataInputOutput
import game.actorbehavior.*
import game.level.eventhandler.imp.DefaultLevelEventHandler
import game.level.eventhandler.model.LevelEventHandler
import game.level.filesystem.LevelFileSystemHandler
import game.level.gamehandler.imp.DefaultGameHandler
import game.level.gamehandler.model.GameHandler
import game.level.info.model.LevelInfo
import game.level.world1.*
import game.level.world2.*
import javax.sound.sampled.Clip
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
        gameHandler = DefaultGameHandler(this)
    }

    lateinit var field: JPanel
    var currentLevelSound: Clip? = null

    abstract fun endLevel()
    abstract fun onStartLevel()

    open fun start(field: JPanel) {
        Bomberman.getMatch().gameState = true
        this.field = field
        updateLastLevel()
        PlayLevelSoundTrackBehavior(this).invoke()
        PlayLevelSoundBehavior(this).invoke()
        DataInputOutput.getInstance().resetLivesIfNecessary()
        gameHandler.generate()
    }

    private fun updateLastLevel() {
        if (this !is WorldSelectorLevel)
            currLevel = this
    }

    override fun toString(): String {
        return "Level ${info.levelId}, World ${info.worldId}"
    }

    companion object {
        val ID_TO_FIRST_LEVEL_MAP: Map<Int, Class<out Level>> = mapOf(1 to World1Level1::class.java, 2 to World2Level1::class.java)
        val ID_TO_LEVEL: Map<Array<Int>, Class<out Level>> = mapOf(
                arrayOf(1, 1) to World1Level1::class.java, arrayOf(1, 2) to World1Level2::class.java,
                arrayOf(1, 3) to World1Level3::class.java, arrayOf(1, 4) to World1Level4::class.java,
                arrayOf(1, 5) to World1Level5::class.java, arrayOf(2, 1) to World2Level1::class.java,
                arrayOf(2, 2) to World2Level2::class.java, arrayOf(2, 3) to World2Level3::class.java,
                arrayOf(2, 4) to World2Level4::class.java, arrayOf(2, 5) to World2Level5::class.java
        )
        var currLevel: Level? = null
            private set
    }
}
