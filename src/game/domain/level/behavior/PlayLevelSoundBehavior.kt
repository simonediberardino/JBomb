package game.domain.level.behavior

import game.domain.level.levels.Level
import game.audio.AudioManager

class PlayLevelSoundBehavior(private val level: Level): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            playLevelSound()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {
            playLevelSound()
        }
    }

    private fun playLevelSound() {
        val soundPath = level.info.levelBackgroundSound
        val classLoader = Thread.currentThread().contextClassLoader
        if (soundPath == null)
            return

        // Attempt to load the resource as an InputStream
        val soundStream = classLoader.getResourceAsStream(soundPath)
        if (soundStream != null) {
            // If the resource exists, play it using AudioManager
            level.currentLevelSound = AudioManager.getInstance().play(soundPath, true)
        }
    }
}