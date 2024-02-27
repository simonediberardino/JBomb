package game.engine.world.domain.entity.actors.impl.blocks

import game.Bomberman
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
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
