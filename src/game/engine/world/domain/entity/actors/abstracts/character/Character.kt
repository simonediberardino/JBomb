package game.engine.world.domain.entity.actors.abstracts.character

import game.engine.hardwareinput.Command
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.logic.ICharacterEntityLogic
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.network.dto.CharacterDto
import game.engine.world.domain.entity.actors.abstracts.moving_entity.MovingEntity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import java.util.HashSet

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

    override fun toDto(): CharacterDto {
        return CharacterDto(
                info.id,
                info.position,
                info.type.ordinal,
                state.direction?.ordinal ?: 0
        )
    }

    companion object {
        val size = PitchPanel.PIXEL_UNIT * 4 * 2
    }
    
    internal object DEFAULT {
        val LAST_DIRECTION_UPDATE = 0L
        val COMMAND_QUEUE: MutableSet<Command> = hashSetOf()
        val PREVIOUS_DIRECTION: Direction? = null
        val CAN_MOVE = true
        val MAX_HP = 100
        val SPEED = 1f
        val IMAGE_DIRECTION = null
        val STEP_SOUND = SoundModel.STEP_SOUND
        val IMAGE_DIRECTIONS = Direction.values().asList()
        val DEATH_SOUND = SoundModel.ENTITY_DEATH
    }
}
