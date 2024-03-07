package game.engine.world.domain.entity.pickups.powerups.base

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.logic.IEntityInteractableLogic
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.state.EntityInteractableState
import game.engine.world.domain.entity.pickups.powerups.*
import game.engine.world.domain.entity.pickups.powerups.base.logic.PowerUpLogic
import game.engine.world.domain.entity.pickups.powerups.base.state.PowerUpState
import java.util.*

/**
 * The abstract PowerUp class is a superclass for all power-ups in the game.
 */
abstract class PowerUp : EntityInteractable {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val state: PowerUpState = PowerUpState(entity = this)
    override val info: EntityInfo = EntityInfo()
    override val image: EntityImageModel = EntityImageModel(entity = this)
    abstract override val logic: PowerUpLogic

    companion object {
        // A static array of power-up classes
        val POWER_UPS: Array<Class<out PowerUp>> = arrayOf(
                PistolPowerUp::class.java,
                ArmorPowerUp::class.java,
                FirePowerUp::class.java,
                SpeedPowerUp::class.java,
                TransparentDestroyableBlocksPowerUp::class.java,
                LivesPowerUp::class.java,
                RemoteControlPowerUp::class.java,
                HammerPowerUp::class.java,
                BlockMoverPowerUp::class.java,
                IncreaseMaxBombsPowerUp::class.java,
                TransparentBombsPowerUp::class.java
        )
    }

    internal object DEFAULT {
        // The default duration for a power-up, in seconds
        const val DURATION_SEC: Int = 15
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        const val IS_DISPLAYABLE = true
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>> = hashSetOf(Player::class.java)
        val OBSTACLES: MutableSet<Class<out Entity>> = hashSetOf(Player::class.java)
        val INCOMPATIBLE_POWER_UPS = mutableListOf<Class<out PowerUp>>()
    }
}