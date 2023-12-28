package game.entity.player

import game.Bomberman
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.HardBlock
import game.entity.bomb.AbstractExplosion
import game.entity.bomb.Bomb
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

    override fun onSpawn() {
        super.onSpawn()
        Bomberman.getMatch().give(this, BombItem())
    }

    override fun move(coordinates: Coordinates) {
        super.move(coordinates)
        handleInteractionWithBombs()
    }

    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(HardBlock::class.java, DestroyableBlock::class.java)

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(DestroyableBlock::class.java, Enemy::class.java, Bomb::class.java)

    override val maxExplosionDistance: Int
        get() = currExplosionLength

    private val minDistanceToBomb: Double
        get() = Bomberman.getMatch().bombs.minOfOrNull { bomb -> bomb.coords.distanceTo(coords) } ?: 0.0

    val maxBombs: Int
        get() = Bomberman.getMatch().currentLevel?.info?.maxBombs ?: 0

    override fun doInteract(e: Entity?) {}

    override fun getInteractionsEntities(): Set<Class<out Entity>> = HashSet()

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> =
            hashSetOf(AbstractExplosion::class.java, Enemy::class.java, PowerUp::class.java)

    override fun getStepSound(): SoundModel = SoundModel.STEP_SOUND

    override fun getSpawnOffset(): Coordinates = SPAWN_OFFSET

    private fun handleInteractionWithBombs() {
        if (bombsSolid || forceBombsSolid) {
            return
        }
        if (minDistanceToBomb > PitchPanel.GRID_SIZE / 2f) setBombsSolid(true)
    }

    fun forceSetBombsNotSolid(bombsNotSolid: Boolean) {
        forceBombsSolid = bombsNotSolid
    }

    fun setBombsSolid(bombsSolid: Boolean) {
        this.bombsSolid = bombsSolid

        if (!bombsSolid) {
            addWhiteListObstacle(Bomb::class.java)
        } else if (!forceBombsSolid) {
            removeWhiteListObstacle(Bomb::class.java)
        }
    }

    fun isMouseClickInteractable(cls: Class<out Entity>): Boolean = entitiesClassListMouseClick.contains(cls)

    fun isMouseDragInteractable(cls: Class<out Entity>): Boolean = entitiesClassListMouseDrag.contains(cls)

    fun addClassInteractWithMouseClick(cls: Class<out Entity>) {
        entitiesClassListMouseClick.add(cls)
    }

    fun addClassInteractWithMouseDrag(cls: Class<out Entity>) {
        entitiesClassListMouseDrag.add(cls)
    }

    fun removeClassInteractWithMouseClick(cls: Class<out Entity>) {
        entitiesClassListMouseClick.remove(cls)
    }

    fun removeClassInteractWithDrag(cls: Class<out Entity>) {
        entitiesClassListMouseDrag.remove(cls)
    }

    fun removeActivePowerUp(p: PowerUp?) {
        activePowerUps.removeIf { e: Class<out PowerUp> -> e.isInstance(p) }
    }

    companion object {
        const val MAX_BOMB_CAN_HOLD = 10
        val SPAWN_OFFSET = Coordinates((PitchPanel.GRID_SIZE - SIZE) / 2, PitchPanel.GRID_SIZE - SIZE)
    }
}
