package game.domain.world.domain.entity.actors.abstracts.base

import game.Bomberman
import game.domain.events.models.RunnablePar
import game.domain.tasks.GameTickerObserver
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.actors.impl.models.UiState
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.mappers.dtoToEntityNetwork
import game.network.entity.EntityNetwork
import game.utils.dev.Extensions.getOrTrim
import game.values.DrawPriority
import java.awt.image.BufferedImage
import java.util.*

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
    fun canBeInteractedBy(e: Entity?): Boolean
    fun mouseClickedInteraction()
    fun onMouseClickInteraction()
    fun onMouseDragInteraction()
    fun observerUpdate(arg: Observable2.ObserverParam)
    fun onStateReady() {}
    fun collide(e: Entity)
    fun unCollide(e: Entity)
    fun passiveCollide(e: Entity)
    fun unPassivecollide(e: Entity)
    fun onExitCollision(e: Entity)
    fun onCollision(e: Entity)
    fun onTalk(entity: Entity)
    fun talk(entity: Entity)
    fun unCollideAll()
    fun mouseDraggedInteraction()
    fun onAdded()
    fun onRemoved()
}

// Class representing the state of an entity
open class EntityState(
        open var entity: Entity,
        open var isSpawned: Boolean = Entity.DEFAULT.SPAWNED,
        open var isImmune: Boolean = Entity.DEFAULT.IMMUNE,
        open var state: State? = Entity.DEFAULT.STATE,
        open var isInvisible: Boolean = Entity.DEFAULT.IS_INVISIBLE,
        open val size: Int,
        open var alpha: Float = Entity.DEFAULT.ALPHA,
        open val interactionEntities: MutableSet<Class<out Entity>> = Entity.DEFAULT.INTERACTION_ENTITIES,
        open var lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE,
        open var lastTalkTime: Long = Entity.DEFAULT.LAST_TALK_TIME,
        val collidedEntities: MutableSet<Entity> = mutableSetOf(),
        val passiveCollidedEntities: MutableSet<Entity> = mutableSetOf(),
) {
    open val canRespawn: Boolean = Entity.DEFAULT.CAN_RESPAWN
    open var uiState: UiState = Entity.DEFAULT.UI_STATE
}

data class EntityInfo(val entity: Entity) {
    var id: Long = UUID.randomUUID().mostSignificantBits
    val type: EntityTypes
        get() {
            return entity.properties.type
        }

    var position: Coordinates = Coordinates(0, 0)
}

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

    private val defaultPaddingTopFunction: RunnablePar = object : RunnablePar {
        override fun <T> execute(par: T): Any {
            val temp: Int = (entity.state.size.toDouble() / par as Double - entity.state.size).toInt()
            entity.image.paddingTop = temp
            return temp
        }
    }

    private val defaultPaddingWidthFunction: RunnablePar = object : RunnablePar {
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
    fun loadAndSetImage(entity: Entity, imagePath: String): BufferedImage?
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

    fun resetGraphics(entity: Entity) {}
}

// Main Entity class implementing EntityBehavior
abstract class Entity : GameTickerObserver, Comparable<Entity> {
    abstract val logic: IEntityLogic
    open val info: EntityInfo = EntityInfo(entity = this)
    open val properties: EntityProperties = EntityProperties(type = EntityTypes.Entity)
    open val state: EntityState = EntityState(entity = this, size = 0)
    abstract val image: EntityImageModel
    abstract val graphicsBehavior: IEntityGraphicsBehavior

    constructor(coordinates: Coordinates? = Coordinates(-1, -1)) {
        if (coordinates != null) {
            info.position = coordinates
        }
        // MEGA WORKAROUND! Remove.
        /*while (true) {
            try {
                info.id = UUID.randomUUID().mostSignificantBits

                if (coordinates != null)
                    info.position = coordinates

                break

            } catch (exception: Exception) {
                Log.e(exception.message.toString())
            }
        }*/
    }

    constructor(id: Long) {
        info.id = id
        // MEGA WORKAROUND! Remove.
        /*while (true) {
            try {
                info.id = id
                break

            } catch (exception: Exception) {
                Log.e(exception.message.toString())
            }
        }*/
    }

    constructor() : this(null)

    /*init {
        // MEGA WORKAROUND! Remove.
        while (true) {
            try {
                info.type = properties.type
                break

            } catch (exception: Exception) {
                Log.e(exception.message.toString())
            }
        }
    }*/

    override fun compareTo(other: Entity): Int {
        return Comparator.comparing { obj: Entity ->
            obj.properties.priority
        }.thenComparing { e: Entity ->
            e.info.position.y
        }.thenComparingInt { e: Entity ->
            e.info.id.toInt()
        }.compare(this, other)
    }

    override fun update(arg: Observable2.ObserverParam) {
        super.update(arg)

        if (!Bomberman.match.gameState) {
            return
        }

        logic.observerUpdate(arg)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Entity) return false
        return info.id == other.info.id
    }

    open fun toEntityNetwork(): EntityNetwork {
        return this.dtoToEntityNetwork()
    }

    override fun hashCode(): Int = Objects.hash(info.id)

    open fun updateInfo(info: Map<String, String>) {
        val isInvisible = info.getOrTrim("isInvisible")
        isInvisible?.let {
            state.isInvisible = it.toBoolean()
        }

        val isImmune = info.getOrTrim("isImmune")
        isImmune?.let {
            state.isImmune = it.toBoolean()
        }

        val state = info.getOrTrim("state")
        state?.let {
            this.state.state = State.valueOf(it)
        }
    }

    internal object DEFAULT {
        val ALPHA = 1f
        val LAST_IMAGE_UPDATE = 0L
        val LAST_TALK_TIME = 0L
        val IS_INVISIBLE = false
        val STATE = null
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
        val CAN_RESPAWN = false
        val UI_STATE = UiState.IDLE
        var imagePath: String = Entity.DEFAULT.IMAGE_PATH
    }
}