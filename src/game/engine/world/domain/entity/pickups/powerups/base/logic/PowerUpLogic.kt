package game.engine.world.domain.entity.pickups.powerups.base.logic

import game.Bomberman
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.logic.EntityInteractableLogic
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp
import java.util.*

abstract class PowerUpLogic(
        override val entity: PowerUp
) : EntityInteractableLogic(entity = entity), IPowerUpLogic {
    override fun doInteract(e: Entity?) {
        apply(e as BomberEntity)
    }

    final override fun apply(player: BomberEntity) {
        if (this.entity.state.applied || !canPickUp(player))
            return

        entity.state.applied = true
        eliminated()
        entity.state.bomberEntity = player

        doApply(player)

        val matchPanel = Bomberman.getBombermanFrame().matchPanel
        AudioManager.getInstance().play(SoundModel.POWERUP)

        player.state.activePowerUps.add(entity.javaClass)

        if (entity.state.isDisplayable)
            matchPanel.refreshPowerUps(player.state.activePowerUps)

        val durationMillis: Long = entity.state.duration * 1000L

        // If the power-up has a duration, schedule a TimerTask to cancel it when the duration is up
        if (durationMillis <= 0)
            return

        val task = object : TimerTask() {
            override fun run() {
                val match = Bomberman.getMatch() ?: return
                if (!match.gameState) return

                player.state.activePowerUps.remove(entity.javaClass)

                if (entity.state.isDisplayable)
                    matchPanel.refreshPowerUps(player.state.activePowerUps)

                cancel(player)
            }
        }

        Timer().schedule(task, durationMillis)
    }

    override fun canPickUp(bomberEntity: BomberEntity): Boolean =
            !bomberEntity.state.activePowerUps.any { p: Class<out PowerUp> ->
                p == this.javaClass || this.entity.state.incompatiblePowerUps.contains(p)
            }

    override fun onAttackReceived(damage: Int) {}

    override fun observerUpdate(arg: Any?) {}

    override fun onMove(coordinates: Coordinates) {}

}