package game.level.info.model

import game.data.DataInputOutput
import game.level.Level
import game.localization.Localization
import game.powerups.LivesPowerUp
import game.powerups.PowerUp

abstract class DefaultStoryLevelInfo(level: Level) : DefaultLevelInfo(level) {
    override val diedMessage: String
        get() {
            val lives = DataInputOutput.getInstance().lives
            return Localization.get(Localization.YOU_DIED).replace("%lives%", Integer.toString(lives))
        }

    override val isArenaLevel: Boolean get() = false
    override val restrictedPerks: Array<Class<out PowerUp?>> get() = arrayOf(LivesPowerUp::class.java)
}