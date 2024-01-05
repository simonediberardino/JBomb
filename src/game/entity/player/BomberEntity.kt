package game.entity.player

import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.HardBlock
import game.entity.bomb.AbstractExplosion
import game.entity.placeable.Bomb
import game.entity.bonus.mysterybox.MysteryBoxPerk
import game.entity.models.*
import game.items.BombItem
import game.items.UsableItem
import game.powerups.PowerUp
import game.sound.SoundModel
import game.ui.panels.game.PitchPanel
import java.util.*

abstract class BomberEntity(coordinates: Coordinates?) : Character(coordinates), Explosive {
    val activePowerUps: MutableList<Class<out PowerUp>> = ArrayList()
    var currExplosionLength = 0
    var placedBombs = 0
    var lastPlacedBombTime: Long = 0
    var currentBombs = 0
    lateinit var weapon: UsableItem
    private var bombsSolid = true
    private var forceBombsSolid = false
    private val entitiesClassListMouseClick: MutableList<Class<out Entity>> = mutableListOf()
    private val entitiesClassListMouseDrag: MutableList<Class<out Entity>> = mutableListOf()

    init {
        entitiesClassListMouseClick.add(MysteryBoxPerk::class.java)
        hitboxSizeToHeightRatio = 0.733f
    }

    // Defines the set of obstacle entities that block the propagation of explosions.
    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(HardBlock::class.java, DestroyableBlock::class.java)

    // Defines the set of entities that can interact with explosions.
    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(DestroyableBlock::class.java, Enemy::class.java, Bomb::class.java)

    // Defines the maximum distance an explosion can propagate.
    override val maxExplosionDistance: Int
        get() = currExplosionLength

    // Calculates the minimum distance to any bomb from the current entity. If there are no bombs, returns 0.0.
    private val minDistanceToBomb: Double
        get() = Bomberman.getMatch().bombs.minOfOrNull { bomb -> bomb.coords.distanceTo(coords) } ?: 0.0

    // Retrieves the maximum number of bombs the current level allows.
    val maxBombs: Int
        get() = Bomberman.getMatch().currentLevel?.info?.maxBombs ?: 0


    override fun onSpawn() {
        super.onSpawn()

        // Give the current entity a BombItem when it is spawned in the match.
        Bomberman.getMatch().give(this, BombItem())
    }

    override fun onMove(coordinates: Coordinates) {
        // Handle interactions with bombs after the entity has moved.
        handleInteractionWithBombs()
    }

    override fun doInteract(e: Entity?) {}

    override fun getInteractionsEntities(): Set<Class<out Entity>> = HashSet()

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> =
            hashSetOf(AbstractExplosion::class.java, Enemy::class.java, PowerUp::class.java)

    override fun getStepSound(): SoundModel = SoundModel.STEP_SOUND

    override fun getSpawnOffset(): Coordinates = SPAWN_OFFSET

    // Handles the interaction of the entity with bombs, determining whether it should be solid based on bomb proximity.
    private fun handleInteractionWithBombs() {
        // If bombs are forced solid or bombs are already solid, no further action is needed.
        if (bombsSolid || forceBombsSolid) {
            return
        }

        // If the minimum distance to a bomb is greater than half the grid size, make bombs solid.
        if (minDistanceToBomb > PitchPanel.GRID_SIZE / 2f) {
            setBombsSolid(true)
        }
    }

    // Forces the entity's interaction with bombs to be not solid or vice versa.
    fun forceSetBombsNotSolid(bombsNotSolid: Boolean) {
        forceBombsSolid = bombsNotSolid
    }

    // Sets the interaction of the entity with bombs to be solid or not based on the specified boolean.
    fun setBombsSolid(bombsSolid: Boolean) {
        this.bombsSolid = bombsSolid

        // Adjust the whitelist of obstacles based on the bombs' solidity.
        if (!bombsSolid) {
            addWhiteListObstacle(Bomb::class.java)
        } else if (!forceBombsSolid) {
            removeWhiteListObstacle(Bomb::class.java)
        }
    }

    // Checks if the entity is interactable with a mouse click based on the entity's class.
    fun isMouseClickInteractable(cls: Class<out Entity>): Boolean = entitiesClassListMouseClick.contains(cls)

    // Checks if the entity is interactable with a mouse drag based on the entity's class.
    fun isMouseDragInteractable(cls: Class<out Entity>): Boolean = entitiesClassListMouseDrag.contains(cls)

    // Adds the specified class to the list of entities interactable with a mouse click.
    fun addClassInteractWithMouseClick(cls: Class<out Entity>) {
        entitiesClassListMouseClick.add(cls)
    }

    // Adds the specified class to the list of entities interactable with a mouse drag.
    fun addClassInteractWithMouseDrag(cls: Class<out Entity>) {
        entitiesClassListMouseDrag.add(cls)
    }

    // Removes the specified class from the list of entities interactable with a mouse click.
    fun removeClassInteractWithMouseClick(cls: Class<out Entity>) {
        entitiesClassListMouseClick.remove(cls)
    }

    // Removes the specified class from the list of entities interactable with a mouse drag.
    fun removeClassInteractWithDrag(cls: Class<out Entity>) {
        entitiesClassListMouseDrag.remove(cls)
    }

    // Removes an active power-up from the list of active power-ups.
    fun removeActivePowerUp(p: PowerUp?) {
        activePowerUps.removeIf { e: Class<out PowerUp> -> e.isInstance(p) }
    }

    companion object {
        const val MAX_BOMB_CAN_HOLD = 10
        val SPAWN_OFFSET = Coordinates((PitchPanel.GRID_SIZE - SIZE) / 2, PitchPanel.GRID_SIZE - SIZE)
    }
}
