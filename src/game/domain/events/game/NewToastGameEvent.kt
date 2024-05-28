package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.audio.AudioManager
import game.audio.SoundModel

class NewToastGameEvent : GameEvent {
    override fun invoke(vararg arg: Any?) {
        JBomb.JBombFrame.parentPanel.repaint()
        if (arg[0] as Boolean) {
            AudioManager.instance.play(SoundModel.BONUS_ALERT)
        }
    }
}