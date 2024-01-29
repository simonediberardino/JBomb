package game.engine.level.info.model

import game.storage.data.DataInputOutput
import game.engine.level.levels.Level
import game.localization.Localization
import game.engine.world.pickups.powerups.LivesPowerUp
import game.engine.world.pickups.powerups.PowerUp

abstract class DefaultStoryLevelInfo(level: Level) : DefaultLevelInfo(level) {
    override val diedMessage: String
        get() {
            val lives = DataInputOutput.getInstance().lives
            return Localization.get(Localization.YOU_DIED).replace("%lives%", Integer.toString(lives))
        }

    override val isArenaLevel: Boolean get() = false
    override val restrictedPerks: Array<Class<out PowerUp?>> get() = arrayOf(LivesPowerUp::class.java)
}