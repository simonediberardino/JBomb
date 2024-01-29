package game.engine.events.game

import game.Bomberman
import game.engine.events.models.GameEvent
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel

class NewToastGameEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getBombermanFrame().parentPanel.repaint()
        if (arg as Boolean) {
            AudioManager.getInstance().play(SoundModel.BONUS_ALERT)
        }
    }
}