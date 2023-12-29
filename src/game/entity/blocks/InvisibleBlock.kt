package game.entity.blocks

import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Entity
import java.awt.image.BufferedImage

class InvisibleBlock(coordinates: Coordinates?) : HardBlock(coordinates) {
    constructor(id: Long) : this(null) {
        this.id= id
    }

    override fun doInteract(e: Entity?) {}
    override fun getImage(): BufferedImage? = null
    override fun getType(): EntityTypes = EntityTypes.InvisibleBlock
}
