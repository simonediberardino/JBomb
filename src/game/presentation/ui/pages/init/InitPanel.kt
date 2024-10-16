package game.presentation.ui.pages.init

import game.JBomb
import game.data.data.DataInputOutput
import game.usecases.CheckUpdateUseCase
import game.localization.Localization
import game.localization.Localization.*
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.loading.LoadingPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.pages.registration.RegistrationUsername
import game.utils.ui.ToastUtils
import game.properties.RuntimeProperties
import kotlinx.coroutines.launch
import java.awt.CardLayout
import javax.swing.JPanel

class InitPanel(
        cardLayout: CardLayout,
        parent: JPanel,
        frame: JBombFrame
) : LoadingPanel(cardLayout, parent, frame, getInitMessage()) {
    private var loadingFinished = false
    private var stepsExecuted = 0

    companion object {
        private fun getInitMessage(): String? {
            val currUsername = DataInputOutput.getInstance().username

            val message = if (currUsername.isEmpty()) {
                Localization.get(WELCOME_TEXT_ANONYMOUS)
            } else {
                Localization.get(WELCOME_TEXT).replace("%user%", currUsername)
            }

            return message
        }
    }

    init {
        setCallback {
            loadingFinished = true
            proceedIfFinished()
        }
    }

    private val stepsToLoad: Array<suspend () -> Unit>
        get() = arrayOf(
                checkUpdate,
                preloadSounds
        )

    private val checkUpdate : suspend () -> Unit = {
        val needsUpdate = CheckUpdateUseCase().invoke()
        RuntimeProperties.needsUpdate = needsUpdate
        proceedIfFinished()
    }

    private val preloadSounds: suspend () -> Unit = {
       // AudioManager.instance.preloadSounds(SoundModel.values().map { it.toString() })
    }

    override fun onShowCallback() {
        ToastUtils.show(Localization.get(Localization.LOADING_INIT), true, true)

        stepsToLoad.forEach {
            JBomb.scope.launch {
                it.invoke()
                stepsExecuted++
                proceedIfFinished()
            }
        }
    }

    private fun proceedIfFinished() {
        if (stepsExecuted == stepsToLoad.size && loadingFinished) {
            ToastUtils.cancel()

            if (DataInputOutput.getInstance().username.isNotBlank()) {
                JBomb.showActivity(MainMenuPanel::class.java)
            } else {
                JBomb.showActivity(RegistrationUsername::class.java)
            }
        }
    }
}