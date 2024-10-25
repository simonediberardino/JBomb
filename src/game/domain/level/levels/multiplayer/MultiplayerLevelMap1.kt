package game.domain.level.levels.multiplayer

import game.domain.level.info.model.LevelInfo
import game.localization.Localization

class MultiplayerLevelMap1 : MultiplayerLevel() {
    override val mapIdXml: String = "map1.xml"
    override val info: LevelInfo = MultiplayerLevelInfo(1, this@MultiplayerLevelMap1)

    override fun toString(): String {
        return Localization.get(Localization.MP_CASTLE_UI_NAME)
    }
}