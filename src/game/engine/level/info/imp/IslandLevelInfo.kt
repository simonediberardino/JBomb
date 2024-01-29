package game.engine.level.info.imp

import game.engine.world.geo.Coordinates
import game.engine.level.levels.Level
import java.awt.Dimension

class IslandLevelInfo(level: Level) : LobbyLevelInfo(level) {
    override val worldId: Int get() = 0
    override val levelId: Int get() = 0

    override val playerSpawnCoordinates: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(5, 2), 0, 0)
}