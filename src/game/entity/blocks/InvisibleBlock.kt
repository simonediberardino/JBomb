package game.entity.blocks

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Entity
import java.awt.image.BufferedImage

class InvisibleBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun doInteract(e: Entity?) {}

    override fun getImage(): BufferedImage? = null

    override val type: EntityTypes = EntityTypes.InvisibleBlock
}
