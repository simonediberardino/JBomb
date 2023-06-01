package game.sound;

import game.utils.Paths;

import java.util.Locale;

public enum SoundModel {
    EXPLOSION,
    BOMBERMAN_STEP,
    POWERUP,
    MOUSE_HOVER,
    CLICK,
    PLAYER_DEATH,
    BONUS_ALERT,
    BOMB_CLOCK,
    AXE_HIT,
    LIGHT_GLITCH,
    ENTITY_DEATH,
    EXPLOSION_CONFETTI,
    STEP_SOUND;

    public String toString() {
        return String.format("%s/%s.wav", Paths.getSoundsPath(), super.toString().toLowerCase());
    }
}
