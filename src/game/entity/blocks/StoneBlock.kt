package game.entity.blocks

import game.Bomberman
import game.entity.EntityTypes
import game.entity.models.Coordinates
import game.entity.models.Entity
import java.awt.image.BufferedImage

class StoneBlock : HardBlock {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val entitiesAssetsPath: String?
        get() = null

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

    override val type: EntityTypes
        get() = EntityTypes.StoneBlock
}
