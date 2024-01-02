package game.entity.bomb

import game.Bomberman
import game.entity.EntityTypes
import game.entity.blocks.DestroyableBlock
import game.entity.blocks.HardBlock
import game.entity.blocks.MovableBlock
import game.entity.models.*
import game.sound.AudioManager
import game.sound.SoundModel
import game.ui.panels.game.PitchPanel
import game.utils.Paths
import game.utils.Utility
import java.awt.image.BufferedImage
import java.util.*

open class Bomb(private var caller: Entity?) : MovableBlock(Coordinates.getCenterCoordinatesOfEntity(caller)), Explosive {
    private var onExplodeCallback: Runnable? = null

    constructor(id: Long) : this(null) {
        this.id = id
    }

    override fun onSpawn() {
        super.onSpawn()

        // TODO SUPER TEMPORARY!!
        if (coords == null) {
            caller = Bomberman.getMatch().player
            coords = Coordinates.getCenterCoordinatesOfEntity(caller)
        }
    }

    override fun getEntitiesAssetsPath(): String = "${Paths.entitiesFolder}/bomb/"

    override fun getImage(): BufferedImage {
        val imagesCount = 3
        val images = arrayOfNulls<String>(imagesCount)
        for (i in images.indices) {
            images[i] = "${entitiesAssetsPath}bomb_$i.png"
        }
        if (Utility.timePassed(lastImageUpdate) < imageRefreshRate) {
            return image
        }
        val img = loadAndSetImage(images[lastImageIndex])
        AudioManager.getInstance().play(SoundModel.BOMB_CLOCK)
        lastImageIndex++
        if (lastImageIndex >= images.size) {
            lastImageIndex = 0
        }
        return img
    }

    fun setOnExplodeListener(runnable: Runnable?) {
        onExplodeCallback = runnable
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    override fun doInteract(e: Entity?) {}

    override fun getType(): EntityTypes = EntityTypes.Bomb

    fun explode() {
        if (!isSpawned) {
            return
        }

        despawn()
        AudioManager.getInstance().play(SoundModel.EXPLOSION)

        for (direction in Direction.values()) {
            FireExplosion(caller!!, coords, direction, this).explode()
        }

        onExplodeCallback?.run()
    }

    fun trigger() {
        val explodeTask: TimerTask = object : TimerTask() {
            override fun run() {
                explode()
            }
        }
        val timer = Timer()
        timer.schedule(explodeTask, EXPLODE_TIMER.toLong())
    }

    override fun getSize(): Int = BOMB_SIZE

    override val explosionObstacles: Set<Class<out Entity>>
        get() = setOf(HardBlock::class.java, DestroyableBlock::class.java)

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(DestroyableBlock::class.java, Character::class.java, Bomb::class.java)

    override val maxExplosionDistance: Int
        get() = Bomberman.getMatch().player?.currExplosionLength ?: 0

    override fun onMouseClickInteraction() {
        eliminated()
    }

    override fun destroy() = explode()

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> =
            hashSetOf(FireExplosion::class.java, AbstractExplosion::class.java)

    override fun onExplosion(explosion: AbstractExplosion) = explode()

    companion object {
        val BOMB_SIZE = PitchPanel.COMMON_DIVISOR * 2
        const val PLACE_INTERVAL: Long = 1000
        private const val EXPLODE_TIMER = 5000
    }
}
