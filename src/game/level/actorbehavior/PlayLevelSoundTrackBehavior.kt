package game.level.actorbehavior

import game.level.Level
import game.sound.AudioManager

class PlayLevelSoundTrackBehavior(private val level: Level) : GameBehavior {
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