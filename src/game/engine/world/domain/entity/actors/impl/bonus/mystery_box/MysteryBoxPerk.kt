package game.engine.world.domain.entity.actors.impl.bonus.mystery_box

import game.Bomberman
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.level.levels.Level
import game.engine.world.domain.entity.actors.impl.bonus.mystery_box.base.MysteryBox
import game.engine.world.domain.entity.actors.impl.bonus.mystery_box.base.logic.MysteryBoxLogic
import game.engine.world.domain.entity.actors.impl.bonus.mystery_box.base.state.MysteryBoxState
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import java.lang.Exception

class MysteryBoxPerk(level: Level, entity: Entity?) : MysteryBox(level, entity) {
    // TODO Refactor
    constructor(id: Long) : this(Bomberman.getMatch().currentLevel!!, null) {
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

            powerUpInstance.logic.apply(Bomberman.getMatch().player ?: return)
        }
    }

    override val state: MysteryBoxState = object : MysteryBoxState(entity = this) {
        override val price: Int = 200
    }
}
