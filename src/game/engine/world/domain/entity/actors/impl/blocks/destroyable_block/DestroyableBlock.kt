package game.engine.world.domain.entity.actors.impl.blocks.destroyable_block

import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.blocks.MovableBlock
import game.engine.world.domain.entity.actors.impl.blocks.base_block.logic.IBlockEntityLogic
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.graphics.DestroyableBlockGraphics
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.logic.DestroyableBlockLogic
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.properties.DestroyableBlockProperties
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.properties.DestroyableBlockState
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.powerups.PowerUp

class DestroyableBlock : MovableBlock {
    constructor(coordinates: Coordinates?, powerUpClass: Class<out PowerUp>? = null) : super(coordinates) {
        this.state.powerUpClass = powerUpClass
    }

    constructor(coordinates: Coordinates?) : this(coordinates, null)

    constructor(id: Long) : super(id)

    override val logic: IBlockEntityLogic = DestroyableBlockLogic(this)
    override val properties: DestroyableBlockProperties = DestroyableBlockProperties()
    override val image: EntityImageModel = EntityImageModel(entity = this)
    override val state: DestroyableBlockState = DestroyableBlockState()
    override val info: EntityInfo = EntityInfo()
    override val graphicsBehavior: IEntityGraphicsBehavior = DestroyableBlockGraphics()
}
