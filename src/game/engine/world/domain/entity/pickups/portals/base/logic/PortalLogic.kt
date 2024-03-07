package game.engine.world.domain.entity.pickups.portals.base.logic

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.pickups.portals.base.Portal
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic

abstract class PortalLogic(override val entity: Portal) : PowerUpLogic(entity = entity) {
    override fun doApply(player: BomberEntity) {
        Bomberman.getMatch().toggleGameState()
    }

    override fun cancel(player: BomberEntity) {}
}