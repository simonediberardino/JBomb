package game.entity.enemies.npcs

import game.entity.EntityTypes
import game.entity.Player
import game.entity.bomb.AbstractExplosion
import game.entity.bomb.AbstractExplosion.Companion.SPAWN_OFFSET
import game.entity.bomb.Bomb
import game.entity.bomb.FireExplosion
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.entity.models.Entity
import game.entity.models.Explosive
import game.sound.AudioManager
import game.sound.SoundModel
import game.utils.Paths.enemiesFolder
import game.utils.Utility.timePassed
import java.util.*

class TankEnemy : IntelligentEnemy, Explosive {
    private var canShoot = false
    private var _lastUpdate: Long = 0

    constructor() : super()
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getCharacterOrientedImages(): Array<String> {
        return arrayOf("$enemiesFolder/tank/tank_${imageDirection.toString().lowercase()}.png")
    }

    /**
     * Ran every game tick;
     *
     * @param arg
     */
    override fun doUpdate(arg: Boolean) {
        val currentTime = System.currentTimeMillis()

        // Check if it's time to update the shooting behavior
        if (timePassed(_lastUpdate) > SHOOTING_REFRESH_RATE) {
            _lastUpdate = currentTime

            // Check if the entity can shoot and if a random probability allows shooting
            if (canShoot && Math.random() * 100 < PROBABILITY_OF_SHOOTING) {
                // Calculate new coordinates with an explosion offset for vertical directions
                var newCoords = getNewTopLeftCoordinatesOnDirection(currDirection, AbstractExplosion.SIZE)
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

        // Check if it's time to allow movement again after standing still
        if (timePassed(_lastUpdate) > STANDING_STILL_PERIOD) {
            canMove = true
        }

        // Call the superclass's doUpdate method
        super.doUpdate(arg)
    }

    override val explosionObstacles: Set<Class<out Entity?>>
        get() {
            return emptySet()
        }

    override val explosionInteractionEntities: Set<Class<out Entity?>>
        get() {
            return setOf(
                    Player::class.java,
                    Bomb::class.java
            )
        }

    override val maxExplosionDistance: Int
        get() {
            return 4
        }

    override fun getType(): EntityTypes {
        return EntityTypes.TankEnemy
    }

    companion object {
        private const val STANDING_STILL_PERIOD = 1000
        private const val PROBABILITY_OF_SHOOTING = 30
        private const val SHOOTING_REFRESH_RATE = 2000
    }
}