package game.domain.world.domain.entity.actors.abstracts.character

import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.logic.ICharacterEntityLogic
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.domain.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.mappers.dtoToEntityNetwork
import game.network.entity.EntityNetwork
import game.presentation.ui.panels.game.PitchPanel
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log
import game.values.DrawPriority

/**
 * Represents a character in the game, which can move and interact with the environment.
 */
abstract class Character : MovingEntity {
    abstract override val logic: ICharacterEntityLogic
    abstract override val state: CharacterEntityState
    abstract override val graphicsBehavior: ICharacterGraphicsBehavior
    abstract override val properties: CharacterEntityProperties
    abstract override val image: CharacterImageModel

    /**
     * Returns an array of file names for the left-facing icons for this character.
     *
     * @return an array of file names for the left-facing icons
     */
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor(id: Long) : super(id)
    constructor() : super()

    override fun toEntityNetwork(): EntityNetwork {
        return dtoToEntityNetwork()
    }

    override fun updateInfo(info: Map<String, String>) {
        super.updateInfo(info)

        Log.e("Updating info ${this.info.id} $info")

        val name = info.getOrTrim("name")
        name?.let {
            properties.name = it
        }
    }

    companion object {
        val size = PitchPanel.PIXEL_UNIT * 4 * 2
    }

    internal object DEFAULT {
        val LAST_DIRECTION_UPDATE = 0L
        val PREVIOUS_DIRECTION: Direction? = null
        val CAN_MOVE = true
        val MAX_HP = 100
        val SPEED = 1f
        val STEP_SOUND = null
        val IMAGE_DIRECTIONS: List<Direction>
            get() = Direction.values().asList()
        val DEATH_SOUND: SoundModel
            get() = SoundModel.ENTITY_DEATH
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        val DRAW_PRIORITY: DrawPriority
            get() = DrawPriority.DRAW_PRIORITY_2
        val HITBOX_SIZE_TO_HEIGHT_RATIO: Float = 0.733f
        val STEP_SIZE: Int
            get() = PitchPanel.PIXEL_UNIT
    }
}
