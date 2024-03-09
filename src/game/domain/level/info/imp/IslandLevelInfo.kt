package game.domain.level.info.imp

import game.domain.world.domain.entity.geo.Coordinates
import game.domain.level.levels.Level
import java.awt.Dimension

class IslandLevelInfo(level: Level) : LobbyLevelInfo(level) {
    override val worldId: Int get() = 0
    override val levelId: Int get() = 0

    override val playerSpawnCoordinates: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(5, 2), 0, 0)
}