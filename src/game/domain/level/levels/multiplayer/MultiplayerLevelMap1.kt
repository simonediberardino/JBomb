package game.domain.level.levels.multiplayer

import game.domain.level.info.model.LevelInfo
import game.domain.level.levels.level_editor.LevelGenerationData
import game.localization.Localization
import java.awt.Dimension

class MultiplayerLevelMap1 : MultiplayerLevel() {
    override val mapIdXml: String = "map1.xml"
    override val info: LevelInfo = MultiplayerLevelInfo(1, this@MultiplayerLevelMap1)

    override fun toString(): String {
        return Localization.get(Localization.MP_DRACULAS_CASTLE_UI_NAME)
    }
}