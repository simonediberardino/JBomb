package game.utils.ui

import game.domain.events.game.NewToastGameEvent
import game.presentation.ui.viewelements.misc.ToastHandler
import game.properties.RuntimeProperties

object ToastUtils {
    fun show(text: String, playSound: Boolean): Double {
        return show(text, false, playSound)
    }

    fun show(text: String): Double {
        return show(text, false, true)
    }

    fun show(text: String, permanent: Boolean, playSound: Boolean): Double {
        if (RuntimeProperties.dedicatedServer)
            return 0.0

        ToastHandler.getInstance().cancel()
        return ToastHandler.getInstance().show(text, permanent, playSound)
    }
    
    fun cancel() {
        if (RuntimeProperties.dedicatedServer)
            return

        ToastHandler.getInstance().cancel()
    }
}