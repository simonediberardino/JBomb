package game.domain.level.behavior

import game.domain.level.levels.Level
import game.audio.AudioManager

class PlayLevelSoundTrackBehavior(private val level: Level) : GameBehavior() {
    override fun hostBehavior() {
        playSoundTrack()

    }

    override fun clientBehavior() {
        playSoundTrack()
    }

    private fun playSoundTrack() {
        AudioManager.instance.stopBackgroundSong()
        AudioManager.instance.playBackgroundSong(level.info.levelSoundtrack)
    }
}