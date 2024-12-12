package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.audio.AudioManager
import game.audio.SoundModel
import game.utils.dev.Log

class NewToastGameEvent : GameEvent {
    override fun invoke(vararg arg: Any?) {
        Log.i("New toast game event")
        JBomb.JBombFrame.parentPanel.repaint()
        if (arg[0] as Boolean) {
            AudioManager.instance.play(SoundModel.BONUS_ALERT)
        }
    }
}