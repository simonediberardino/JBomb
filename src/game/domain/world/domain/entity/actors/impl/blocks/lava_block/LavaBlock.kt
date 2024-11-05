package game.domain.world.domain.entity.actors.impl.blocks.lava_block

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.PeriodicGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.base_block.properties.BlockEntityProperties
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.logic.LavaBlockLogic
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.properties.LavaEntityProperties
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.utils.file_system.Paths.blocksFolder
open class LavaBlock(
    hasSpawnDelay: Boolean,
    coordinates: Coordinates? = null,
    id: Long? = null
) : HardBlock(coordinates, id) {
    private val maxExpansionRadius = 4
    var expansionRadius = maxExpansionRadius
    private val minSpawnDelay = 60

    constructor(id: Long) : this(
        id = id,
        hasSpawnDelay = false,
        coordinates = null
    )

    constructor(coordinates: Coordinates?) : this(
        coordinates = coordinates,
        hasSpawnDelay = false
    )

    // Use hasSpawnDelay in the properties
    override val properties: LavaEntityProperties = LavaEntityProperties(hasSpawnDelay = hasSpawnDelay)

    override val logic = LavaBlockLogic(
        entity = this
    )

    override val image: EntityImageModel = EntityImageModel(
        entity = this,
        entitiesAssetsPath = "$blocksFolder/lava/lavablock%format%.png"
    )

    override val graphicsBehavior: IEntityGraphicsBehavior = object : PeriodicGraphicsBehavior() {
        override val imagesCount: Int = 3
        override val allowUiState: Boolean = false
    }

    override fun countFrame() {
        super.countFrame()
        val spawnDelay = (maxExpansionRadius - expansionRadius) * minSpawnDelay

        if (frame >= spawnDelay) {
            if (!state.isSpawned) {
                logic.spawn()
            }
            JBomb.match.gameTickerObservable?.unregister(this)
        }
    }
}
