package game.presentation.ui.panels.menu.username

import game.data.data.DataInputOutput
import game.domain.events.models.RunnablePar
import java.util.function.Consumer

class UsernameMenuPanelFactory {
    fun build(): UsernameProfilePanel {
        val getUsernameRunnable: RunnablePar = object : RunnablePar {
            override fun <T> execute(par: T): Any? {
                return DataInputOutput.getInstance().username
            }
        }

        // Define a Consumer to handle the username change

        val usernameChangeConsumer = Consumer { username: String ->
            if (username.isBlank()) return@Consumer
            DataInputOutput.getInstance().username = username.trim { it <= ' ' }
        }

        return UsernameProfilePanel(getUsernameRunnable, usernameChangeConsumer)
    }
}