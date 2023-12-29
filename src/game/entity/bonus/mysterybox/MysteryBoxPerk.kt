package game.entity.bonus.mysterybox

import game.Bomberman
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.level.levels.Level
import game.powerups.PowerUp
import java.lang.Exception

class MysteryBoxPerk(level: Level, entity: Entity) : MysteryBox(level, entity) {
    override val price: Int
        get() = 200

    // TODO Refactor
    constructor(id: Long) : this(Bomberman.getMatch().currentLevel!!, Bomberman.getMatch().player!!) {
        this.id = id
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
