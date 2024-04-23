package game.domain.world.domain.entity.actors.impl.placeable.bomb

import game.audio.AudioManager
import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.models.Explosive
import game.domain.world.domain.entity.actors.impl.placeable.base.PlaceableEntity
import game.domain.world.domain.entity.actors.impl.placeable.base.state.PlaceableEntityState
import game.domain.world.domain.entity.actors.impl.placeable.bomb.logic.BombLogic
import game.domain.world.domain.entity.actors.impl.placeable.bomb.state.BombState
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.utils.file_system.Paths
import java.awt.image.BufferedImage

/**
 * Represents a bomb entity that can explode and cause damage in the game.
 *
 * @param caller The entity that spawned the bomb.
 */
open class Bomb(caller: BomberEntity) : PlaceableEntity(), Explosive {
    constructor(id: Long, caller: BomberEntity) : this(caller) {
        info.id = id
    }

    constructor(coordinates: Coordinates?, caller: BomberEntity) : this(caller) {
        coordinates?.let {
            info.position = it
        }
    }

    override val state: PlaceableEntityState = BombState(entity = this, caller = caller)

    init {
        info.position = Coordinates.getCenterCoordinatesOfEntity(state.caller)
    }

    override val image: EntityImageModel = EntityImageModel(
            entity = this,
            entitiesAssetsPath = "${Paths.entitiesFolder}/bomb/"
    )

    override val logic: BombLogic = BombLogic(entity = this)
    override val properties: EntityProperties = EntityProperties(
            type = EntityTypes.Bomb
    )

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            val imagesCount = 3
            val images = Array(imagesCount) { i ->
                "${
                    image.entitiesAssetsPath
                }bomb_$i.png"
            }

            // Check if enough time has passed for an image refresh
            if (Utility.timePassed(state.lastImageUpdate) < image.imageRefreshRate) {
                return image._image!!
            }

            // Load the next image in the sequence
            val img = loadAndSetImage(entity = entity,
                    imagePath = images[image.lastImageIndex]
            )
            AudioManager.getInstance().play(SoundModel.BOMB_CLOCK)
            image.lastImageIndex = (image.lastImageIndex + 1) % images.size

            return img
        }
    }

    /**
     * Gets the maximum explosion distance for the bomb.
     *
     * @return The maximum explosion distance.
     */
    override val maxExplosionDistance: Int
        get() = (state.caller as BomberEntity).state.currExplosionLength

    override val explosionInteractionEntities: Set<Class<out Entity>> = Bomb.DEFAULT.EXPLOSION_INTERACTION_ENTITIES
    override val explosionObstacles: Set<Class<out Entity>> = Bomb.DEFAULT.EXPLOSION_OBSTACLES

    /**
     * Companion object containing constant values for the Bomb class.
     */
    companion object {
        const val PLACE_INTERVAL: Long = 1000
        const val EXPLODE_TIMER = 5000
    }

    internal object DEFAULT {
        val SIZE = PitchPanel.COMMON_DIVISOR * 2

        val EXPLOSION_OBSTACLES: Set<Class<out Entity>> =
                setOf(HardBlock::class.java)

        val EXPLOSION_INTERACTION_ENTITIES: Set<Class<out Entity>> =
                setOf(DestroyableBlock::class.java, Character::class.java, Bomb::class.java)

        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>> = hashSetOf(AbstractExplosion::class.java)
    }
}

