package game.domain.world.domain.entity.actors.impl.bonus.mystery_box

import game.Bomberman
import game.domain.level.levels.Level
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.logic.MysteryBoxLogic
import game.domain.world.domain.entity.actors.impl.bonus.mystery_box.base.state.MysteryBoxState
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp

class MysteryBoxPerk(
        level: Level,
        entity: Entity?
) : MysteryBox() {
    // TODO Refactor
    constructor(id: Long) : this(Bomberman.match.currentLevel, null) {
        this.info.id = id
    }

    override val logic: MysteryBoxLogic = object : MysteryBoxLogic(entity = this) {
        override fun onPurchaseConfirm() {
            val powerUpClass = level.info.randomPowerUpClass
            val powerUpInstance: PowerUp = try {
                powerUpClass.getConstructor(Coordinates::class.java).newInstance(Coordinates(0, 0))
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

            powerUpInstance.logic.apply(Bomberman.match.player ?: return)
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
