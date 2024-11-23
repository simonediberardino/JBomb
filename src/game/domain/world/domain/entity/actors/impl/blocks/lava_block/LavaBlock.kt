package game.domain.world.domain.entity.actors.impl.blocks.lava_block

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.PeriodicGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.logic.LavaBlockLogic
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.properties.LavaEntityProperties
import game.domain.world.domain.entity.actors.impl.blocks.lava_block.state.LavaBlockState
import game.domain.world.domain.entity.geo.Coordinates
import game.utils.file_system.Paths.blocksFolder
import java.awt.image.BufferedImage

open class LavaBlock(
    hasSpawnDelay: Boolean,
    coordinates: Coordinates? = null,
    id: Long? = null,
    canExpand: Boolean = false
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
    override val properties: LavaEntityProperties = LavaEntityProperties()

    override val logic = LavaBlockLogic(
        entity = this
    )

    override val image: EntityImageModel = EntityImageModel(
        entity = this,
        entitiesAssetsPath = "$blocksFolder/lava/lavablock%format%.png"
    )

    /*override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            TODO("Not yet implemented")
        }

    }*/

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadAndSetImage(
                entity = entity,
                imagePath = "$blocksFolder/lava/lavablock_0.png"
            )
        }
    }

    override val state: LavaBlockState = LavaBlockState(entity = this, canExpand = canExpand)

    override fun countFrame() {
        super.countFrame()
        val spawnDelay = (maxExpansionRadius - expansionRadius) * minSpawnDelay

        if (frame >= spawnDelay) {
            if (!state.isSpawned) {
                logic.spawn(forceSpawn = true)
            }
            JBomb.match.gameTickerObservable?.unregister(this)
        }
    }
}
