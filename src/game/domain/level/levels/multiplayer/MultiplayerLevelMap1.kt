package game.domain.level.levels.multiplayer

import game.domain.level.info.model.LevelInfo
import java.awt.Dimension

class MultiplayerLevelMap1 : MultiplayerLevel() {
    override val info: LevelInfo
        get() = object : MultiplayerLevelInfo(1, this@MultiplayerLevelMap1) {
            override val mapDimension: Dimension
                get() = Dimension(2000, 2000)
        }
}