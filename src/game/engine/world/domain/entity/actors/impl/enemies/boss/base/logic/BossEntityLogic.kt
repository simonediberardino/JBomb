package game.engine.world.domain.entity.actors.impl.enemies.boss.base.logic

import game.engine.level.behavior.GameBehavior
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.AiEnemyLogic
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.portals.EndLevelPortal
import game.engine.world.domain.entity.pickups.powerups.PowerUp

class BossEntityLogic(override val entity: Boss) : AiEnemyLogic(entity = entity), IBossEntityLogic {
    override fun onDespawn() {
        super.onDespawn()

        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior(): () -> Unit {
                return {
                    val endLevelPortal: PowerUp = EndLevelPortal(Coordinates.generateCoordinatesAwayFromPlayer())
                    endLevelPortal.logic.spawn(true)
                }
            }

            override fun clientBehavior(): () -> Unit {
                return {}
            }
        }

        gameBehavior.invoke()

    }

    override fun onEliminated() {
        super.onEliminated()
        AudioManager.getInstance().play(SoundModel.BOSS_DEATH)
    }

    /**
     * Updates the rage status of the Boss, loading and setting the corresponding image.
     *
     * @param status the new rage status to be set.
     */
    override fun updateRageStatus(status: Int) {
        // If the new rage status is the same as the current one, nothing to update.
        if (status == entity.state.currRageStatus)
            return

        entity.state.currRageStatus = status
        // Load and set the image.
        entity.graphicsBehavior.loadAndSetImage(
                entity = entity,
                imagePath = entity.graphicsBehavior.getImageFromRageStatus()
        )
    }
}