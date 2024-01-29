package game.engine.sound

import game.utils.Paths.soundsPath
import java.util.*

enum class SoundModel {
    EXPLOSION, BOMBERMAN_STEP, POWERUP, MOUSE_HOVER, CLICK, PLAYER_DEATH, BONUS_ALERT, BOMB_CLOCK, AXE_HIT, LIGHT_GLITCH, ENTITY_DEATH, EXPLOSION_CONFETTI, STEP_SOUND, BOSS_DEATH, CLOWN_NOSE_DEATH;

    override fun toString(): String {
        return "$soundsPath/${super.toString().lowercase(Locale.getDefault())}.wav"
    }
}