package game.engine.level.behavior

import game.engine.level.levels.Level
import game.engine.sound.AudioManager

class PlayLevelSoundTrackBehavior(private val level: Level) : GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            playSoundTrack()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            playSoundTrack()
        }
    }

    private fun playSoundTrack() {
        AudioManager.getInstance().stopBackgroundSong()
        AudioManager.getInstance().playBackgroundSong(level.info.levelSoundtrack)
    }
}