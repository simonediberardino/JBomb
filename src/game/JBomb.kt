package game

import game.audio.AudioManager
import game.data.data.DataInputOutput
import game.domain.level.levels.Level
import game.domain.match.JBombMatch
import game.domain.tasks.GarbageCollectorTask
import game.input.terminal.Terminal
import game.usecases.CheckUpdateUseCase
import game.localization.Localization
import game.network.gamehandler.OnlineGameHandler
import game.network.usecases.FetchAllServersFromMasterServer
import game.network.usecases.SendServerInfoToMasterServer
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.error.NetworkErrorPage
import game.presentation.ui.pages.init.InitPanel
import game.presentation.ui.pages.loading.LoadingPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.pages.server_browser.ServerInfo
import game.presentation.ui.panels.game.CustomSoundMode
import game.presentation.ui.panels.game.MatchPanel
import game.presentation.ui.panels.game.PagePanel
import game.utils.ui.ToastUtils
import game.properties.RuntimeProperties
import game.usecases.GetInetAddressUseCase
import game.utils.dev.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.Component
import java.util.*
import javax.swing.SwingUtilities
import javax.xml.crypto.Data

object JBomb {
    lateinit var match: JBombMatch

    @JvmStatic
    lateinit var JBombFrame: JBombFrame
    private var currentPage: Class<out PagePanel>? = null
    val scope = CoroutineScope(Dispatchers.IO)

    /**
     * Starts the Java Application;
     */
    @JvmStatic
    fun main(args: Array<String>) {
        handleArgs(args)
        initUiSettings()
        retrievePlayerData()
        startGarbageCollectorTask()
        start()
        initTerminal()
    }

    private fun handleArgs(args: Array<String>) {
        if (args.contains("-dedicatedserver")) {
            RuntimeProperties.dedicatedServer = true
        }

        val port = args.find { it .startsWith("-port=") }?.replace("-port=", "")?.toInt()
        RuntimeProperties.port = port
    }

    private fun initTerminal() {
        if (RuntimeProperties.dedicatedServer) {
            runBlocking {
                Terminal.start()
            }
        } else {
            scope.launch {
                Terminal.start()
            }
        }
    }

    private fun initUiSettings() {
        // disable DPI scaling
        System.setProperty("sun.java2d.uiScale", "1");
        System.setProperty("sun.java2d.dpiaware", "true");
    }

    private fun start() {
        if (!RuntimeProperties.dedicatedServer) {
            JBombFrame = JBombFrame()
            JBombFrame.create()
            showActivity(InitPanel::class.java)
        }
    }

    private fun startGarbageCollectorTask() {
        GarbageCollectorTask().start()
    }

    private fun retrievePlayerData() {
        DataInputOutput.getInstance().retrieveData()
    }

    @JvmStatic
    fun quitMatch() {
        destroyLevel(true)
        showActivity(MainMenuPanel::class.java)
    }

    fun destroyLevel(disconnect: Boolean, ended: Boolean = false) {
        if (!RuntimeProperties.dedicatedServer)
            JBombFrame.removeKeyListener(match.controllerManager)
        match.destroy(disconnect)
    }

    /**
     * Starts a new level and destroys the previous one;
     *
     */
    private fun doStartLevel(level: Level, disconnect: Boolean, onlineGameHandler: OnlineGameHandler?) {
        if (this::match.isInitialized) {
            destroyLevel(disconnect)
        }

        match = JBombMatch(level, onlineGameHandler)

        match.scope.launch {
            if (!RuntimeProperties.dedicatedServer)
                JBombFrame.initGamePanel()

            match.currentLevel.start()

            if (!RuntimeProperties.dedicatedServer) {
                JBombFrame.addKeyListener(match.controllerManager)
                JBombFrame.pitchPanel.addMouseListener(match.mouseControllerManager)
                JBombFrame.pitchPanel.addMouseMotionListener(match.mouseControllerManager)
                showActivity(MatchPanel::class.java)
            }

            match.connect()
        }
    }

    @JvmStatic
    fun startLevel(level: Level, onlineGameHandler: OnlineGameHandler?) {
        startLevel(level, onlineGameHandler, true) {}
    }

    @JvmStatic
    fun startLevel(
        level: Level,
        onlineGameHandler: OnlineGameHandler?,
        disconnect: Boolean = true,
        callback: () -> Unit,
    ) {
        if (RuntimeProperties.dedicatedServer) {
            doStartLevel(level, disconnect, onlineGameHandler)
        } else {
            JBombFrame.loadingPanel.initialize()
            JBombFrame.loadingPanel.updateText(level)
            JBombFrame.loadingPanel.setCallback {
                doStartLevel(level, disconnect, onlineGameHandler)
                callback()
            }

            showActivity(LoadingPanel::class.java)
        }
    }

    fun networkError(error: String?) {
        if (!isInGame) return

        destroyLevel(true)

        val formattedError = error?.let {
            Localization.get(Localization.ERROR_NETWORK_DEFAULT).replace("%error%", error)
        } ?: Localization.get(Localization.GAME_ENDED)

        NetworkErrorPage.setError(formattedError)

        SwingUtilities.invokeLater {
            showActivity(NetworkErrorPage::class.java)
        }
    }

    /**
     * Shows a new page and starts its background sound;
     *
     * @param page
     */
    @JvmStatic
    fun showActivity(page: Class<out PagePanel?>) {
        if (RuntimeProperties.dedicatedServer) {
            return
        }

        if (page == currentPage)
            return

        JBombFrame.cardLayout.show(JBombFrame.parentPanel, page.simpleName)
        currentPage = page

        // Gets the component with the passed class and fires its onShowCallback;
        val shownComponentOpt = Arrays.stream(
            JBombFrame.parentPanel.components
        ).filter { c: Component? ->
            c!!.javaClass == page
        }.findFirst()

        val shownComponent = shownComponentOpt.orElse(null)

        if (shownComponent is PagePanel)
            shownComponent.onShowCallback()

        if (shownComponent !is CustomSoundMode) {
            AudioManager.instance.playBackgroundSong()
        }
    }

    val isGameEnded: Boolean
        get() = !match.gameState || !JBomb.isInGame

    val isInGame: Boolean
        get() = match.gameState
}
