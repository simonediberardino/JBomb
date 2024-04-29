package game

import game.audio.AudioManager
import game.data.data.DataInputOutput
import game.domain.level.levels.Level
import game.domain.match.BomberManMatch
import game.domain.tasks.GarbageCollectorTask
import game.localization.Localization
import game.network.gamehandler.OnlineGameHandler
import game.presentation.ui.frames.BombermanFrame
import game.presentation.ui.pages.loading.LoadingPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.panels.game.CustomSoundMode
import game.presentation.ui.panels.game.MatchPanel
import game.presentation.ui.panels.game.PagePanel
import game.presentation.ui.viewelements.misc.ToastHandler
import kotlinx.coroutines.launch
import java.awt.Component
import java.util.*

object Bomberman {
    lateinit var match: BomberManMatch

    @JvmStatic
    lateinit var bombermanFrame: BombermanFrame

    /**
     * Starts the Java Application;
     */
    @JvmStatic
    fun main(args: Array<String>) {
        retrievePlayerData()
        startGarbageCollectorTask()
        start()
    }

    private fun start() {
        bombermanFrame = BombermanFrame()
        bombermanFrame.create()
        showActivity(MainMenuPanel::class.java)
        ToastHandler.getInstance().show(Localization.get(Localization.WELCOME_TEXT).replace("%user%", DataInputOutput.getInstance().username))
    }

    private fun startGarbageCollectorTask() {
        GarbageCollectorTask().start()
    }

    private fun retrievePlayerData() {
        DataInputOutput.getInstance().retrieveData()
    }

    @JvmStatic
    fun quitMatch() {
        destroyLevel()
        showActivity(MainMenuPanel::class.java)
    }

    fun destroyLevel() {
        bombermanFrame.removeKeyListener(match.controllerManager)
        match.destroy()
    }

    /**
     * Starts a new level and destroys the previous one;
     *
     */
    private fun doStartLevel(level: Level, onlineGameHandler: OnlineGameHandler?) {
        if (this::match.isInitialized) {
            destroyLevel()
        }

        match = BomberManMatch(level, onlineGameHandler)

        match.scope.launch {
            bombermanFrame.initGamePanel()
            match.currentLevel.start(bombermanFrame.pitchPanel)
            bombermanFrame.addKeyListener(match.controllerManager)
            bombermanFrame.pitchPanel.addMouseListener(match.mouseControllerManager)
            bombermanFrame.pitchPanel.addMouseMotionListener(match.mouseControllerManager)
            showActivity(MatchPanel::class.java)
        }
    }

    @JvmStatic
    fun startLevel(level: Level, onlineGameHandler: OnlineGameHandler?) {
        bombermanFrame.loadingPanel.initialize()
        bombermanFrame.loadingPanel.updateText(level)
        bombermanFrame.loadingPanel.setCallback {
            doStartLevel(level, onlineGameHandler)
        }

        showActivity(LoadingPanel::class.java)
    }

    /**
     * Shows a new page and starts its background sound;
     *
     * @param page
     */
    @JvmStatic
    fun showActivity(page: Class<out PagePanel?>) {
        bombermanFrame.cardLayout.show(bombermanFrame.parentPanel, page.simpleName)

        // Gets the component with the passed class and fires its onShowCallback;
        val shownComponentOpt = Arrays.stream(
                bombermanFrame.parentPanel.components
        ).filter {
            c: Component? -> c!!.javaClass == page
        }.findFirst()

        val shownComponent = shownComponentOpt.orElse(null)

        if (shownComponent is PagePanel)
            shownComponent.onShowCallback()

        if (shownComponent !is CustomSoundMode) {
            AudioManager.getInstance().playBackgroundSong()
        }
    }

    val isGameEnded: Boolean
        get() = !match.gameState

    val isInGame: Boolean
        get() {
            val match = match
            val currentLevel = match.currentLevel
            return match.gameState && currentLevel.info.worldId > 0
        }
}
