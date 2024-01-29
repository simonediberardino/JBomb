package game.engine.world.entity.impl.blocks

import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity
import java.awt.image.BufferedImage

class InvisibleBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun doInteract(e: Entity?) {}

    override fun getImage(): BufferedImage? = null

    override val type: EntityTypes = EntityTypes.InvisibleBlock
}
