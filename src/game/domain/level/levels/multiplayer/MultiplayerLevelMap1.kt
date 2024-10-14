package game.domain.level.levels.multiplayer

import game.domain.level.info.model.LevelInfo
import game.localization.Localization
import java.awt.Dimension

class MultiplayerLevelMap1 : MultiplayerLevel() {
    override val info: LevelInfo
        get() = object : MultiplayerLevelInfo(1, this@MultiplayerLevelMap1) {
            override var mapDimension: Dimension? = Dimension(1500, 800)
        }

    override fun toString(): String {
        return Localization.get(Localization.MP_DRACULAS_CASTLE_UI_NAME)
    }
}