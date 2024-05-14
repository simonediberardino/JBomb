package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.audio.AudioManager
import game.audio.SoundModel

class NewToastGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        JBomb.JBombFrame.parentPanel.repaint()
        if (arg as Boolean) {
            AudioManager.getInstance().play(SoundModel.BONUS_ALERT)
        }
    }
}