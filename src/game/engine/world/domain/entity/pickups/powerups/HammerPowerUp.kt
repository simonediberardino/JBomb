package game.engine.world.domain.entity.pickups.powerups

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityProperties
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class HammerPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: PowerUpLogic = object : PowerUpLogic(entity = this) {
        override fun doApply(player: BomberEntity) {
            player.state.entitiesClassListMouseClick.add(DestroyableBlock::class.java)
        }

        override fun cancel(player: BomberEntity) {
            player.state.entitiesClassListMouseClick.remove(DestroyableBlock::class.java)
        }
    }

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadAndSetImage(entity, "$powerUpsFolder/hammer.png")
        }
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.HammerPowerUp)

    override val state: PowerUpState = object : PowerUpState(entity = this) {
        override val duration: Int
            get() = 30
    }

    init {
        state.incompatiblePowerUps += BlockMoverPowerUp::class.java
    }
}