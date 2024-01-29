package game.engine.world.entity.impl.bonus.mystery_box

import game.Bomberman
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity
import game.engine.level.levels.Level
import game.engine.world.pickups.powerups.PowerUp
import java.lang.Exception

class MysteryBoxPerk(level: Level, entity: Entity?) : MysteryBox(level, entity) {
    override val price: Int
        get() = 200

    // TODO Refactor
    constructor(id: Long) : this(Bomberman.getMatch().currentLevel!!, null) {
        this.info.id = id
    }

    override fun onPurchaseConfirm() {
        val powerUpClass = level.info.randomPowerUpClass
        val powerUpInstance: PowerUp = try {
            powerUpClass.getConstructor(Coordinates::class.java).newInstance(Coordinates(0, 0))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        powerUpInstance.apply(Bomberman.getMatch().player ?: return)
    }
}
