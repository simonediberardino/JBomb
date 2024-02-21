package game.engine.world.domain.entity.actors.impl.blocks

import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import java.awt.image.BufferedImage

class InvisibleBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun doInteract(e: Entity?) {}

    override fun getImage(): BufferedImage? = null

    override val type: EntityTypes = EntityTypes.InvisibleBlock
}
