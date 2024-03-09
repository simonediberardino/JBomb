package game.domain.level.behavior

import game.domain.level.levels.Level
import game.audio.AudioManager

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