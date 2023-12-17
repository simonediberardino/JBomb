package game.events.game

import game.Bomberman
import game.events.models.GameEvent
import game.sound.AudioManager
import game.sound.SoundModel

class NewToastGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getBombermanFrame().parentPanel.repaint()
        if (arg as Boolean) {
            AudioManager.getInstance().play(SoundModel.BONUS_ALERT)
        }
    }
}