package game.engine.world.domain.entity.actors.abstracts.base

import game.engine.events.models.RunnablePar
import game.engine.tasks.GameTickerObserver
import game.engine.world.network.dto.EntityDto
import game.engine.world.domain.entity.actors.impl.bomb.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.models.State
import game.engine.world.dto.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Extensions.toMap
import game.utils.Log
import game.values.DrawPriority
import java.awt.image.BufferedImage
import java.util.*
import java.util.concurrent.atomic.AtomicReference

// Interface defining common behavior for entities
interface IEntityGameBehavior {
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
    fun onGameTick(arg: Any?)
}

// Class representing the state of an entity
open class EntityState(
        var isSpawned: Boolean = false,
        var isImmune: Boolean = false,
        var state: AtomicReference<State>? = AtomicReference(),
        var isInvisible: Boolean = false,
        val size: Int,
        var alpha: Float = 1f,
        val interactionEntities: MutableSet<Class<out Entity>> = mutableSetOf(),
        var lastImageUpdate: Long = 0L
)

data class EntityInfo(
        var id: Long = -1,
        var position: Coordinates = Coordinates(-1, -1),
        var type: EntityTypes = EntityTypes.Entity
)

open class EntityProperties(
        val priority: DrawPriority,
        val type: EntityTypes
)

open class EntityImageModel(
        val entity: Entity,
        val entitiesAssetsPath: String = "",
        var hitboxSizeToWidthRatio: Float = 1f,
        var hitboxSizeToHeightRatio: Float = 1f,
        var paddingTop: Int = 0,
        var paddingWidth: Int = 0,
        val imageRefreshRate: Int = 200,
        var _image: BufferedImage? = null,
        var lastImageIndex: Int = 0,
        var lastImageUpdate: Long = 0,
        var imagePath: String = ""
) {
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
    abstract val logic: IEntityGameBehavior
    abstract val entityInfo: EntityInfo
    abstract val properties: EntityProperties
    abstract val state: EntityState
    abstract val image: EntityImageModel
    abstract val graphicsBehavior: IEntityGraphicsBehavior
    var info = EntityInfo()

    constructor(coordinates: Coordinates? = Coordinates(-1, -1)) {
        info.id = UUID.randomUUID().mostSignificantBits

        if (coordinates != null)
            info.position = coordinates
    }

    constructor(id: Long) {
        info.id = id
    }

    constructor() : this(null)

    init {
        try {
            info.type = properties.type
        } catch (exception: Exception) {
            Log.e(exception.message.toString())
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

    open fun toDto(): EntityDto {
        return EntityDto(
                entityInfo.id,
                entityInfo.position,
                entityInfo.type.ordinal
        )
    }

    fun toMap(): Map<String, String> {
        return toDto().toMap()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Entity) return false
        return entityInfo.id == other.entityInfo.id
    }

    override fun hashCode(): Int = Objects.hash(entityInfo.id)

    override fun toString(): String {
        return "Entity{id=${entityInfo.id}, entityInfo= $entityInfo}"
    }
}