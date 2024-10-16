package game.domain.level.info.imp

import game.domain.world.domain.entity.geo.Coordinates
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.items.UsableItem
import java.awt.Dimension

class IslandLevelInfo(level: Level) : LobbyLevelInfo(level) {
    override val worldId: Int get() = 0
    override val levelId: Int get() = 0
    override val defaultWeapon: UsableItem?
        get() = null

    override val playerSpawnCoordinates: Coordinates
        get() = Coordinates.fromRowAndColumnsToCoordinates(Dimension(5, 2), 0, 0)

    override val startAnimalsCount: Int
        get() = 2

    override val cameraMoveEnabled: Boolean = false
}