package game.domain.world.domain.entity.actors.impl.bonus.mystery_box

import game.JBomb
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.logic.MysteryBoxLogic
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.state.MysteryBoxState
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.powerups.LivesPowerUp

class MysteryBoxPerk(
        level: () -> Level?,
        entity: () -> BomberEntity?
) : MysteryBox() {
    // TODO Refactor
    constructor(id: Long) : this({ JBomb.match.currentLevel }, { JBomb.match.player }) {
        this.info.id = id
    }

    override val logic: MysteryBoxLogic = object : MysteryBoxLogic(entity = this) {
        override fun onPurchaseConfirm() {
            val level = level() ?: return
            val player = state.buyer() ?: return

            val pickablePerks = level.info.allowedPerks.map {
                it.getConstructor(Coordinates::class.java).newInstance(Coordinates(0, 0))
            }.filter { it.logic.canPickUp(player) }

            val powerUp = pickablePerks.random()
            powerUp.logic.apply(player)
        }
    }

    override val state: MysteryBoxState = object : MysteryBoxState(
            entity = this,
            buyer = entity,
            level = level
    ) {
        override val price: Int = 200
    }
}
