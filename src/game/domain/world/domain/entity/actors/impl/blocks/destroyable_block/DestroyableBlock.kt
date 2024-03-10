package game.domain.world.domain.entity.actors.impl.blocks.destroyable_block

import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityInfo
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.movable_block.MovableBlock
import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.graphics.DestroyableBlockGraphics
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.logic.DestroyableBlockLogic
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.properties.DestroyableBlockProperties
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.properties.DestroyableBlockState
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp

class DestroyableBlock : MovableBlock {
    constructor(coordinates: Coordinates?, powerUpClass: Class<out PowerUp>? = null) : super(coordinates) {
        this.state.powerUpClass = powerUpClass
    }

    constructor(coordinates: Coordinates?) : this(coordinates, null)

    constructor(id: Long) : super(id)

    override val logic: IBlockEntityLogic = DestroyableBlockLogic(this)
    override val properties: DestroyableBlockProperties = DestroyableBlockProperties()
    override val image: EntityImageModel = EntityImageModel(entity = this)
    override val state: DestroyableBlockState = DestroyableBlockState(entity = this)
    
    override val graphicsBehavior: IEntityGraphicsBehavior = DestroyableBlockGraphics()
}
