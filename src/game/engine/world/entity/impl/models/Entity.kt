package game.engine.world.entity.impl.models

import game.Bomberman
import game.engine.world.entity.build.EntityInfo
import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.bomb.AbstractExplosion
import game.engine.events.models.RunnablePar
import game.engine.world.entity.dto.EntityDto
import game.engine.world.geo.Coordinates
import game.network.events.forward.DespawnEntityEventForwarder
import game.network.events.forward.SpawnEntityEventForwarder
import game.engine.tasks.GameTickerObserver
import game.engine.ui.panels.game.PitchPanel
import game.utils.Extensions.toMap
import game.utils.Log
import game.utils.Utility.ensureRange
import game.utils.Utility.fileExists
import game.utils.Utility.loadImage
import game.values.DrawPriority
import java.awt.image.BufferedImage
import java.lang.Exception
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import java.util.regex.Pattern

/**
 * Represents an entity in the game world, such as a player, enemy, or obstacle.
 */
abstract class Entity : GameTickerObserver, Comparable<Entity> {
    protected abstract val interactionEntities: MutableSet<Class<out Entity>>
    protected var _image: BufferedImage? = null
    protected var lastImageIndex = 0
    protected var lastImageUpdate: Long = 0
    var info = EntityInfo()
    protected abstract val type: EntityTypes
    abstract val size: Int
   // abstract val graphics: EntityGraphics
    var hitboxSizeToWidthRatio = 1f
        protected set

    /**
     * Returns the size of the entity in pixels.
     *
     * @return the size of the entity
     */
    open var hitboxSizeToHeightRatio = 1f
        protected set

    /**
     * Returns true if the entity has been spawned in the game world, false otherwise.
     *
     * @return true if the entity has been spawned, false otherwise
     */
    /**
     * Sets whether the entity has been spawned in the game world.
     *
     * @param s true if the entity has been spawned, false otherwise
     */
    var isSpawned = false
        /**
         * Sets whether the entity has been spawned in the game world.
         *
         * @param s true if the entity has been spawned, false otherwise
         */
        protected set

    var isImmune = false
        set(value) {
            state!!.set(if (value) State.IMMUNE else State.SPAWNED)
            field = value
        }

    @Volatile
    var state: AtomicReference<State>? = AtomicReference()
    var isInvisible = false
        protected set

    var paddingTop = 0

    protected var paddingTopFunction: RunnablePar = object : RunnablePar {
        override fun <T> execute(par: T): Any {
            val temp: Int = (size.toDouble() / par as Double - size).toInt()
            paddingTop = temp
            return temp
        }
    }

    var paddingWidth = 0
    private var paddingWidthFunction: RunnablePar = object : RunnablePar {
        override fun <T> execute(par: T): Any {
            val temp: Int = ((size.toDouble() / par as Double - size) / 2).toInt()
            paddingWidth = temp
            return temp
        }
    }

    var imagePath = ""
        private set

    var alpha = 1f
        set(alpha) {
            field = ensureRange(alpha, 0f, 1f)
        }

    protected open val spawnOffset: Coordinates
        get() = Coordinates((PitchPanel.GRID_SIZE - size) / 2, (PitchPanel.GRID_SIZE - size) / 2)

    open val drawPriority: DrawPriority = DrawPriority.DRAW_PRIORITY_1
    open val imageRefreshRate: Int = 200

    /**
     * Constructs an entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
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
            info.type = type
        } catch (exception: Exception) {
            Log.e(exception.message.toString())
        }
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    protected abstract fun doInteract(e: Entity?)

    /**
     * Returns the image of the entity.
     *
     * @return the image of the entity
     */
    abstract fun getImage(): BufferedImage?

    protected open val entitiesAssetsPath: String?
        get() = ""

    protected open fun onSpawn() {
        state!!.set(State.SPAWNED)
        SpawnEntityEventForwarder(-1).invoke(toDto())
    }

    protected open fun onDespawn() {
        state!!.set(State.DIED)
    }

    fun despawnAndNotify() {
        despawn()
        DespawnEntityEventForwarder().invoke(toDto())
    }

    fun despawn() {
        isSpawned = false
        onDespawn()
        Bomberman.getMatch().removeEntity(this)
    }

    fun spawn(coordinates: Coordinates) {
        info.position = coordinates
        spawn()
    }

    /**
     * Spawns the entity if it is not already spawned and if there is no other entity at the desired coordinates.
     */
    fun spawn() {
        spawn(forceSpawn = false, forceCentering = true)
    }

    /**
     * Spawns the entity if it is not already spawned and if there is no other entity at the desired coordinates.
     */
    fun spawn(forceSpawn: Boolean = false, forceCentering: Boolean = true) {
        if (isSpawned) { // if entity is already spawned, return
            return
        }

        // centers entity on tile
        if (forceCentering)
            info.position = Coordinates.roundCoordinates(info.position, spawnOffset)

        // spawns entity if the spawn point is free, otherwise do nothing
        if (forceSpawn || !Coordinates.isBlockOccupied(info.position)) {
            isSpawned = true // mark entity as spawned
            Bomberman.getMatch().addEntity(this) // add entity to the game state
            onSpawn() // run entity-specific spawn logic
        }
    }

    /**
     * Loads the image at the given file path and sets it as the image of this entity.
     *
     * @param imagePath the file path of the image to load
     * @return the loaded image
     */
    open fun loadAndSetImage(imagePath: String): BufferedImage {
        if (state == null)
            return doLoadAndSetImage(imagePath)

        val toks = imagePath.split(Pattern.quote(".").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val extension = toks[1]
        val fileName = toks[0]
        val imagePathWithStatus = "${fileName}_${state.toString().lowercase(Locale.getDefault())}.$extension"
        val hasImageWithStatus = fileExists(imagePathWithStatus)
        return if (hasImageWithStatus) doLoadAndSetImage(imagePathWithStatus) else doLoadAndSetImage(imagePath)
    }

    private fun doLoadAndSetImage(imagePath: String): BufferedImage {
        lastImageUpdate = System.currentTimeMillis()
        _image = loadImage(imagePath)
        this.imagePath = imagePath
        return _image!!
    }

    open fun getHitboxSizeToHeightRatio(path: String): Float {
        return hitboxSizeToHeightRatio
    }

    open fun getHitboxSizeToWidthRatio(path: String): Float {
        return hitboxSizeToWidthRatio
    }

    open fun onAttackReceived(value: Int) {}

    fun calculateAndGetPaddingTop(ratio: Double = hitboxSizeToHeightRatio.toDouble()): Int {
        return paddingTopFunction.execute(ratio) as Int
    }

    fun calculateAndGetPaddingWidth(ratio: Double = hitboxSizeToWidthRatio.toDouble()): Int {
        return paddingWidthFunction.execute(ratio) as Int
    }

    /**
     * Eliminates the entity by despawning it.
     */
    open fun eliminated() {
        despawnAndNotify()
    }

    /**
     * Handles mouse click interactions.
     * If the entity is within one grid size distance from the player's center coordinates, it gets eliminated.
     */
    protected open fun onMouseClickInteraction() {
        // Retrieve the current match and player
        val match = Bomberman.getMatch()
        val player = match.player

        // Calculate the center coordinates of the player's entity
        val centerCoordinatesOfEntity = Coordinates.roundCoordinates(Coordinates.getCenterCoordinatesOfEntity(player))

        // Check if the entity is within one grid size distance from the player's center coordinates
        if (info.position.distanceTo(centerCoordinatesOfEntity) <= PitchPanel.GRID_SIZE) {
            eliminated()
        }
    }

    /**
     * Handles mouse drag interactions.
     * This method allows dragging the entity to move it around.
     */
    /**
     * Handles the interaction logic when the mouse is dragged.
     */
    private fun onMouseDragInteraction() {
        // Retrieve the current match, mouse controller manager, and player entity
        val match = Bomberman.getMatch()
        val mouseControllerManager = match.mouseControllerManager
        val player: Entity? = match.player

        // Calculate relevant coordinates for the player, entity, and mouse
        val playerCenter = Coordinates.getCenterCoordinatesOfEntity(player)
        val roundedEntityCoords = Coordinates.roundCoordinates(info.position)
        val centerCoordinatesOfEntity = Coordinates.roundCoordinates(playerCenter)
        val mouseCoordinates = Coordinates.roundCoordinates(mouseControllerManager.mouseCoords)

        // Check if the mouse dragged interaction is interrupted
        val isDragInterrupted = mouseControllerManager.isMouseDraggedInteractionInterrupted
        if (isDragInterrupted) {
            return
        }

        // Check if the entity is within one grid size distance from the player's center coordinates
        val isEntityWithinGrid = roundedEntityCoords.distanceTo(centerCoordinatesOfEntity) <= PitchPanel.GRID_SIZE
        val isDragEntered = mouseControllerManager.isMouseDragInteractionEntered

        // Check if the entity is not within one grid size distance and the drag interaction has not been entered yet
        if (!isEntityWithinGrid && !isDragEntered) {
            return
        }

        // Check for other entities on the occupied block during the mouse drag
        val entitiesOnOccupiedBlock = Coordinates.getEntitiesOnBlock(mouseCoordinates)
        val isBlockOccupied = Coordinates.isBlockOccupied(mouseCoordinates)
        val areEntitiesOnBlock = entitiesOnOccupiedBlock.isNotEmpty() && entitiesOnOccupiedBlock.any {
            e: Entity -> e !== this && e !== player
        }

        // If there are other entities on the occupied block, interrupt the mouse dragged interaction
        if (areEntitiesOnBlock) {
            mouseControllerManager.isMouseDraggedInteractionInterrupted = true
            return
        }

        // Check if the block is not occupied and the mouse coordinates are valid
        if (!isBlockOccupied && mouseCoordinates.validate(size)) {
            // Move the entity to the dragged mouse coordinates
            mouseControllerManager.isMouseDragInteractionEntered = true
            mouseControllerManager.setMouseDraggedInteractionOccured(true)
            info.position = Coordinates.roundCoordinates(mouseCoordinates, spawnOffset)
        }
    }

    /**
     * Checks if this entity can be interacted with by another entity.
     *
     * @param e The entity attempting to interact.
     * @return `true` if the entity can be interacted with, `false` otherwise.
     */
    fun canBeInteractedBy(e: Entity?): Boolean {
        return e == null || interactionEntities.any { c: Class<out Entity> -> c.isInstance(e) }
    }

    private fun canEntityInteractWithMouseDrag(): Boolean {
        val match = Bomberman.getMatch()
        val player = match.player

        // Check if the player can interact with mouse drag and if the mouse is being dragged
        return player!!.isMouseDragInteractable(this.javaClass) && match.mouseControllerManager.isMouseDragged
    }

    private fun canEntityInteractWithMouseClick(): Boolean {
        val match = Bomberman.getMatch()
        val player = match.player

        // Check if the player can interact with mouse click and if the mouse is being clicked
        return player!!.isMouseClickInteractable(this.javaClass) && match.mouseControllerManager.isMouseClicked
    }

    /**
     * Handles mouse interactions.
     * This method is responsible for mouse click and mouse drag interactions.
     */
    fun mouseInteractions() {
        val mouseControllerManager = Bomberman.getMatch().mouseControllerManager
        mouseControllerManager.entity ?: return
        if (canEntityInteractWithMouseClick()) {
            onMouseClickInteraction()
            return
        }
        if (canEntityInteractWithMouseDrag()) {
            onMouseDragInteraction()
        }
    }

    open fun onExplosion(explosion: AbstractExplosion?) {}

    override fun compareTo(other: Entity): Int {
        return Comparator.comparing {
            obj: Entity -> obj.drawPriority
        }.thenComparing {
            e: Entity -> e.info.position.y
        }.thenComparingInt {
            e: Entity -> e.info.id.toInt()
        }.compare(this, other)
    }

    open fun toDto(): EntityDto {
        return EntityDto(
                info.id,
                info.position,
                type.ordinal
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
        return "Entity{id=${info.id}, entityInfo= $info}"
    }
}
