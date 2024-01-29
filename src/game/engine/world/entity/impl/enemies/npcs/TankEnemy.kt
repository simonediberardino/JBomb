package game.engine.world.entity.impl.enemies.npcs

import game.engine.world.entity.types.EntityTypes
import game.engine.world.entity.impl.player.Player
import game.engine.world.entity.impl.bomb.AbstractExplosion
import game.engine.world.entity.impl.bomb.AbstractExplosion.Companion.SPAWN_OFFSET
import game.engine.world.entity.impl.placeable.Bomb
import game.engine.world.entity.impl.bomb.FireExplosion
import game.engine.world.geo.Coordinates
import game.engine.world.geo.Direction
import game.engine.world.entity.impl.models.Entity
import game.engine.world.entity.impl.models.Explosive
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.utils.Paths.enemiesFolder
import game.utils.Utility.timePassed

class TankEnemy : IntelligentEnemy, Explosive {
    private var canShoot = false
    private var _lastUpdate: Long = 0

    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getCharacterOrientedImages(): Array<String> =
            arrayOf("$enemiesFolder/tank/tank_${imageDirection.toString().lowercase()}.png")

    /**
     * Ran every game tick;
     *
     * @param gameState
     */
    override fun doUpdate(gameState: Boolean) {
        val currentTime = System.currentTimeMillis()

        // Check if it's time to update the shooting behavior
        if (timePassed(_lastUpdate) > SHOOTING_REFRESH_RATE) {
            _lastUpdate = currentTime

            // Check if the entity can shoot and if a random probability allows shooting
            if (canShoot && Math.random() * 100 < PROBABILITY_OF_SHOOTING) {
                // Calculate new coordinates with an explosion offset for vertical directions
                var newCoords = Coordinates.getNewTopLeftCoordinatesOnDirection(info.position, currDirection, AbstractExplosion.SIZE)
                if (currDirection == Direction.UP || currDirection == Direction.DOWN) {
                    val x = newCoords.x + SPAWN_OFFSET
                    newCoords = Coordinates(x, newCoords.y)
                }

                // Play the explosion sound and create a new FireExplosion
                AudioManager.getInstance().play(SoundModel.EXPLOSION)
                FireExplosion(this, newCoords, currDirection, this)
                canMove = false
            }
            canShoot = true
        }

        // Call the superclass's doUpdate method
        super.doUpdate(gameState)
    }

    override val explosionObstacles: Set<Class<out Entity>>
        get() = emptySet()

    override val explosionInteractionEntities: Set<Class<out Entity>>
        get() = setOf(
                Player::class.java,
                Bomb::class.java
        )

    override val maxExplosionDistance: Int
        get() = 4

    override val type: EntityTypes
        get() = EntityTypes.TankEnemy

    companion object {
        private const val STANDING_STILL_PERIOD = 1000
        private const val PROBABILITY_OF_SHOOTING = 30
        private const val SHOOTING_REFRESH_RATE = 2000
    }
}