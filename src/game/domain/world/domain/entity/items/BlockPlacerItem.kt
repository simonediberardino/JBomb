package game.domain.world.domain.entity.items

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb
import game.domain.world.domain.entity.actors.impl.EntityIds
import game.domain.world.domain.entity.geo.Coordinates
import game.input.terminal.commands.SpawnCommand
import game.utils.Utility
import game.utils.file_system.Paths
import game.utils.time.now
import java.util.*

class BlockPlacerItem: UsableItem() {
    override fun use(itemId: Long?): Long {
        val isPlacementValid = Utility.timePassed(
            owner.state.lastPlacedBombTime
        ) >= Bomb.PLACE_INTERVAL

        if (!isPlacementValid)
            return -1

        val entityClass = EntityIds.getOrDefault(SpawnCommand.lastSpawnedEntityId, null) ?: return -1
        val entity = createEntity(entityClass)

        val coordinates = Coordinates.getCoordinatesOnDirection(
            owner.state.direction,
            owner.info.position,
            entity.state.size / 3 * 2
        )

        entity.info.position = coordinates
        entity.logic.spawn(forceSpawn = true, forceCentering = true)

        owner.state.lastPlacedBombTime = now()

        return entity.info.id
    }

    private fun createEntity(entityClass: Class<out Entity>): Entity {
        val entityId = UUID.randomUUID().mostSignificantBits
        return entityClass.getConstructor(Long::class.java).newInstance(entityId)
    }


    override fun combineItems(item: UsableItem) {}

    override val imagePath: String = "${Paths.powerUpsFolder}/blocks_up.gif"

    override val count: Int
        get() = 1
    override val type: ItemsTypes
        get() = ItemsTypes.BlockPlacer
}