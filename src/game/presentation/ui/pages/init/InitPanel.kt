package game.presentation.ui.pages.init

import game.JBomb
import game.data.data.DataInputOutput
import game.usecases.CheckUpdateUseCase
import game.localization.Localization
import game.localization.Localization.*
import game.presentation.ui.frames.JBombFrame
import game.presentation.ui.pages.loading.LoadingPanel
import game.presentation.ui.pages.main_menu.MainMenuPanel
import game.presentation.ui.viewelements.misc.ToastHandler
import game.properties.RuntimeProperties
import kotlinx.coroutines.launch
import java.awt.CardLayout
import javax.swing.JPanel

class InitPanel(
        cardLayout: CardLayout,
        parent: JPanel,
        frame: JBombFrame
) : LoadingPanel(cardLayout, parent, frame, getInitMessasge()) {
    private var responseReceived = false
    private var loadingFinished = false

    companion object {
        private fun getInitMessasge(): String? {
            val defaultUsername = Localization.get(PLAYER)
            val currUsername = DataInputOutput.getInstance().username

            val message = if (defaultUsername == currUsername) {
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

    override fun onShowCallback() {
        ToastHandler.getInstance().show(Localization.get(Localization.LOADING_INIT))

        JBomb.scope.launch {
            val needsUpdate = CheckUpdateUseCase().invoke()
            RuntimeProperties.needsUpdate = needsUpdate
            responseReceived = true
            proceedIfFinished()
        }
    }

    private fun proceedIfFinished() {
        if (loadingFinished && responseReceived) {
            JBomb.showActivity(MainMenuPanel::class.java)
        }
    }
}