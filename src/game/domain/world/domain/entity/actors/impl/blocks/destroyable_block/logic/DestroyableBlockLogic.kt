package game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.logic

import game.domain.world.domain.entity.actors.impl.blocks.base_block.logic.BlockEntityLogic
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.portals.EndLevelPortal
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.utils.Utility
import java.lang.Exception

class DestroyableBlockLogic(override val entity: DestroyableBlock) : BlockEntityLogic(entity = entity) {
    companion object {
        const val POWER_UP_SPAWN_CHANGE = 33
    }

    override fun onDespawn() {
        super.onDespawn()

        val powerUpClass = entity.state.powerUpClass ?: return

        val spawnPercentage = if (powerUpClass == EndLevelPortal::class.java) 100 else POWER_UP_SPAWN_CHANGE

        Utility.runPercentage(spawnPercentage) {
            val powerUp: PowerUp
            try {
                powerUp = powerUpClass
                        .getConstructor(Coordinates::class.java)
                        .newInstance(entity.info.position)

                powerUp.logic.spawn(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onExplosion(explosion: AbstractExplosion?) {
        super.onExplosion(explosion ?: return)
        explosion.logic.attack(entity)
    }
}