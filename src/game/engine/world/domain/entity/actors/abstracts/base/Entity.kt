package game.engine.world.domain.entity.actors.abstracts.base

import game.engine.events.models.RunnablePar
import game.engine.tasks.GameTickerObserver
import game.network.entity.EntityNetwork
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.models.State
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.dev.Extensions.toMap
import game.utils.dev.Log
import game.values.DrawPriority
import java.awt.image.BufferedImage
import java.util.*
import java.util.concurrent.atomic.AtomicReference

// Interface defining common behavior for entities
interface IEntityLogic {
    fun onAttackReceived(damage: Int)
    fun interact(e: Entity?)
    fun doInteract(e: Entity?)
    fun eliminated()
    fun despawn()
    fun spawn()
    fun spawn(coordinates: Coordinates)
    fun spawn(forceSpawn: Boolean = false, forceCentering: Boolean = true)
    fun notifySpawn()
    fun notifyDespawn()
    fun onSpawn()
    fun onDespawn()
    fun onExplosion(explosion: AbstractExplosion?)
    fun onImmuneChangedState()
    fun spawnOffset(): Coordinates
    fun canEntityInteractWithMouseDrag(): Boolean
    fun canBeInteractedBy(e: Entity?): Boolean
    fun canEntityInteractWithMouseClick(): Boolean
    fun mouseInteractions()
    fun onMouseClickInteraction()
    fun onMouseDragInteraction()
    fun observerUpdate(arg: Any?)
}

// Class representing the state of an entity
open class EntityState(
        open var entity: Entity,
        open var isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        open var isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        open var state: AtomicReference<State>? = Entity.DEFAULT.STATE,
        open var isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        open val size: Int,
        open var alpha: Float = Entity.DEFAULT.ALPHA,
        open val interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        open var lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE
)

data class EntityInfo(
        var id: Long = -1,
        var position: Coordinates = Coordinates(-1, -1),
        var type: EntityTypes = EntityTypes.Entity
)

open class EntityProperties(
        val priority: DrawPriority = DrawPriority.DRAW_PRIORITY_1,
        val type: EntityTypes
)

open class EntityImageModel(
        val entity: Entity,
        val entitiesAssetsPath: String = Entity.DEFAULT.ENTITIES_ASSETS_PATH,
        var paddingTop: Int = Entity.DEFAULT.PADDING_TOP,
        var paddingWidth: Int = Entity.DEFAULT.PADDING_WIDTH,
        val imageRefreshRate: Int = Entity.DEFAULT.IMAGE_REFRESH_RATE,
        var _image: BufferedImage? = Entity.DEFAULT.IMAGE,
        var lastImageIndex: Int = Entity.DEFAULT.LAST_IMAGE_INDEX,
        var lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE,
        var imagePath: String = Entity.DEFAULT.IMAGE_PATH
) {
    open var hitboxSizeToWidthRatio: Float = Entity.DEFAULT.HITBOX_WIDTH_RATIO
    open var hitboxSizeToHeightRatio: Float = Entity.DEFAULT.HITBOX_HEIGHT_RATIO

    private val defaultPaddingTopFunction : RunnablePar = object : RunnablePar {
        override fun <T> execute(par: T): Any {
            val temp: Int = (entity.state.size.toDouble() / par as Double - entity.state.size).toInt()
            entity.image.paddingTop = temp
            return temp
        }
    }

    private val defaultPaddingWidthFunction : RunnablePar = object : RunnablePar {
        override fun <T> execute(par: T): Any {
            val temp: Int = ((entity.state.size.toDouble() / par as Double - entity.state.size) / 2).toInt()
            entity.image.paddingWidth = temp
            return temp
        }
    }

    open var paddingWidthFunction: RunnablePar = defaultPaddingWidthFunction
    open var paddingTopFunction: RunnablePar = defaultPaddingTopFunction
}

interface IEntityGraphicsBehavior {
    fun loadAndSetImage(entity: Entity, imagePath: String): BufferedImage
    fun getImage(entity: Entity): BufferedImage?

    fun getHitboxSizeToHeightRatio(entity: Entity, path: String): Float {
        return entity.image.hitboxSizeToHeightRatio
    }

    fun getHitboxSizeToWidthRatio(entity: Entity, path: String): Float {
        return entity.image.hitboxSizeToWidthRatio
    }

    fun calculateAndGetPaddingTop(entity: Entity, ratio: Double = entity.image.hitboxSizeToHeightRatio.toDouble()): Int {
        return entity.image.paddingTopFunction.execute(ratio) as Int
    }

    fun calculateAndGetPaddingWidth(entity: Entity, ratio: Double = entity.image.hitboxSizeToWidthRatio.toDouble()): Int {
        return entity.image.paddingWidthFunction.execute(ratio) as Int
    }
}

// Main Entity class implementing EntityBehavior
abstract class Entity : GameTickerObserver, Comparable<Entity> {
    abstract val logic: IEntityLogic
    abstract val info: EntityInfo
    abstract val properties: EntityProperties
    abstract val state: EntityState
    abstract val image: EntityImageModel
    abstract val graphicsBehavior: IEntityGraphicsBehavior

    constructor(coordinates: Coordinates? = Coordinates(-1, -1)) {
        // MEGA WORKAROUND! Remove.
        while (true) {
            try {
                info.id = UUID.randomUUID().mostSignificantBits

                if (coordinates != null)
                    info.position = coordinates

                break

            } catch (exception: Exception) {
                Log.e(exception.message.toString())
            }
        }
    }

    constructor(id: Long) {
        // MEGA WORKAROUND! Remove.
        while (true) {
            try {
                info.id = id
                break

            } catch (exception: Exception) {
                Log.e(exception.message.toString())
            }
        }
    }

    constructor() : this(null)

    init {
        // MEGA WORKAROUND! Remove.
        while (true) {
            try {
                info.type = properties.type
                break

            } catch (exception: Exception) {
                Log.e(exception.message.toString())
            }
        }
    }

    override fun compareTo(other: Entity): Int {
        return Comparator.comparing { obj: Entity ->
            obj.properties.priority
        }.thenComparing { e: Entity ->
            e.info.position.y
        }.thenComparingInt { e: Entity ->
            e.info.id.toInt()
        }.compare(this, other)
    }

    open fun toDto(): EntityNetwork {
        return EntityNetwork(
                info.id,
                info.position,
                info.type.ordinal
        )
    }

    fun toMap(): Map<String, String> {
        return toDto().toMap()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Entity) return false
        return info.id == other.info.id
    }

    override fun hashCode(): Int = Objects.hash(info.id)

    override fun toString(): String {
        return "Entity{id=${info.id}, info= $info}"
    }

    internal object DEFAULT {
        val ALPHA = 1f
        val LAST_IMAGE_UPDATE = 0L
        val IS_INVISIBLE = false
        val STATE = AtomicReference<State>()
        val SPAWNED: Boolean = false
        val IMMUNE: Boolean = false
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>> = mutableSetOf()
        val ENTITIES_ASSETS_PATH = ""
        val HITBOX_WIDTH_RATIO = 1f
        val HITBOX_HEIGHT_RATIO = 1f
        val PADDING_TOP = 0
        val PADDING_WIDTH = 0
        val IMAGE_REFRESH_RATE = 200
        val LAST_IMAGE_INDEX = 0
        val IMAGE_PATH = ""
        val IMAGE = null
        
        var imagePath: String = Entity.DEFAULT.IMAGE_PATH
    }
}