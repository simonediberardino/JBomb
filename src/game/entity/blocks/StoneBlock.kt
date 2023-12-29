package game.entity.blocks

import game.Bomberman
import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Entity
import java.awt.image.BufferedImage

class StoneBlock(coordinates: Coordinates?) : HardBlock(coordinates) {
    constructor(id: Long) : this(null) {
        this.id = id
    }

    override fun getEntitiesAssetsPath(): String? = null

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    override fun doInteract(e: Entity?) {
        if (e == null) return
    }

    override fun getImage(): BufferedImage =
            loadAndSetImage(Bomberman.getMatch().currentLevel!!.info.stoneBlockImagePath)

    override fun getType(): EntityTypes = EntityTypes.StoneBlock
}
