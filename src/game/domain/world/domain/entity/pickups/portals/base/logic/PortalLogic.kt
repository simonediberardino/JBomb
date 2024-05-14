package game.domain.world.domain.entity.pickups.portals.base.logic

import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.pickups.portals.base.Portal
import game.domain.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic

abstract class PortalLogic(override val entity: Portal) : PowerUpLogic(entity = entity) {
    override fun doApply(player: BomberEntity) {
    }

    override fun cancel(player: BomberEntity) {}
}