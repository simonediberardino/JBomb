package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.blocks.movable_block.MovableBlock
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class BlockMoverPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage = loadAndSetImage(entity, "$powerUpsFolder/hand.png")
    }

    override val state: PowerUpState = object : PowerUpState(entity = this) {
        override val duration: Int = 30
    }

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            player.logic.addClassInteractWithMouseDrag(MovableBlock::class.java)
        }

        override fun cancel(player: BomberEntity) {
            player.logic.removeClassInteractWithDrag(MovableBlock::class.java)
        }

    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.BlockMoverPowerUp)

    init {
        state.incompatiblePowerUps += HammerPowerUp::class.java
    }
}
