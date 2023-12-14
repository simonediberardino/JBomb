package game.entity.bonus.mysterybox

import game.Bomberman
import game.entity.models.Coordinates
import game.entity.models.Entity
import game.level.Level
import game.powerups.PowerUp
import java.lang.Exception
import java.lang.reflect.InvocationTargetException

class MysteryBoxPerk(level: Level, entity: Entity) : MysteryBox(level, entity) {
    override val price: Int
        get() = 200

    override fun onPurchaseConfirm() {
        val powerUpClass = level.randomPowerUpClass
        val powerUpInstance: PowerUp = try {
            powerUpClass.getConstructor(Coordinates::class.java).newInstance(Coordinates(0, 0))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        powerUpInstance.apply(Bomberman.getMatch().player)
    }
}
