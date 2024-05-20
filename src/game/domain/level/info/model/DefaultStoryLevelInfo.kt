package game.domain.level.info.model

import game.data.data.DataInputOutput
import game.domain.level.levels.Level
import game.localization.Localization
import game.domain.world.domain.entity.pickups.powerups.LivesPowerUp
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp

abstract class DefaultStoryLevelInfo(level: Level) : DefaultLevelInfo(level) {
    override val diedMessage: String
        get() {
            val lives = DataInputOutput.getInstance().lives
            return Localization.get(Localization.YOU_DIED).replace("%lives%", lives.toString())
        }

    override val isArenaLevel: Boolean get() = false
}