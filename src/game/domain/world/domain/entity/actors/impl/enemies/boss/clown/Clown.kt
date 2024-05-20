package game.domain.world.domain.entity.actors.impl.enemies.boss.clown

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.boss.base.Boss
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.image.ClownGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.image.ClownImageModel
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.logic.ClownLogic
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.properties.ClownProperties
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.properties.ClownState
import game.domain.world.domain.entity.actors.impl.models.Explosive
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.types.EntityTypes
import java.util.*

/**
 * The Clown class represents a type of Boss entity that implements the Explosive interface.
 * It has a boolean property hasHat that determines whether the Clown is wearing a hat or not.
 * The Clown entity can spawn orbs, enhanced orbs, explosions and throw its hat in random directions.
 */
class Clown : Boss, Explosive {
    /**
     * Constructor for the Clown entity that takes in the clown boss's starting coordinates and sets its initial hasHat value to true.
     *
     * @param coordinates The starting coordinates of the Clown entity.
     */
    constructor(coordinates: Coordinates?) : super(coordinates)

    // TODO REFACTOR
    constructor() : super(null) {
        state.hasHat = true
    }

    override val state: ClownState = ClownState(entity = this)
    override val logic: ClownLogic = ClownLogic(entity = this)
    override val image: ClownImageModel = ClownImageModel(entity = this)
    override val graphicsBehavior: ClownGraphicsBehavior = ClownGraphicsBehavior(entity = this)
    override val properties: ClownProperties = ClownProperties(types = EntityTypes.Clown)


    /**
     * Overrides the getExplosionObstacles method from the Explosive interface to return an empty list.
     *
     * @return An empty List object.
     */
    override val explosionObstacles: Set<Class<out Entity>>
        get() = emptySet()

    /**
     * Returns a list of entity classes that can interact with explosions.
     *
     * @return a list of entity classes that can interact with explosions.
     */
    override val explosionInteractionEntities: Set<Class<out Entity>> = setOf(
            BomberEntity::class.java,
            Bomb::class.java
    )

    /**
     * Returns the maximum distance of an explosion from this entity.
     *
     * @return the maximum distance of an explosion from this entity.
     */
    override val maxExplosionDistance: Int = 10

    companion object {
        const val SKIN_PATH_TEMPLATE = "%s/clown/clown_%s_%s.png"
    }

    internal object DEFAULT {
        const val ATTACK_DELAY = 5000
        const val LAST_ATTACK_TIME = 0L
        const val HAS_HAT = false
        const val RATIO_HEIGHT_WITH_HAT = 0.7517f
        const val RATIO_HEIGHT = 0.87f
        const val RATIO_WIDTH = 0.8739f
        val SUPPORTED_DIRECTIONS: List<Direction>
            get() = listOf(Direction.LEFT, Direction.RIGHT)
        val HEALTH_STATUS_MAP: TreeMap<Int, Int>
            get() {
                // Create a new TreeMap with reverse order.
                val map = TreeMap<Int, Int>(Collections.reverseOrder())
                // Add the health percentages and their corresponding rage statuses to the map.
                map[75] = 0
                map[60] = 1
                map[50] = 2
                map[25] = 3
                return map
            }
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>>
            get() = Enemy.DEFAULT.INTERACTION_ENTITIES.apply {
                add(Hat::class.java)
            }
    }
}
