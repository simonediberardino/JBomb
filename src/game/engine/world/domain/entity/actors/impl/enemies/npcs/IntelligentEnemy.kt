package game.engine.world.domain.entity.actors.impl.enemies.npcs

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.models.*
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.events.game.KilledEnemyEvent
import game.engine.events.game.ScoreGameEvent
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.utils.XMLUtils
import java.util.stream.Collectors

abstract class IntelligentEnemy : Enemy, CPU {
    private val CHANGE_DIRECTION_RATE = 10 // percentage

    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    init {
        hitboxSizeToHeightRatio = 0.733f
    }

    override fun doInteract(e: Entity?) {
        if (e is BomberEntity) {
            super.doInteract(e)
        } else if (isObstacle(e) || e == null) {
            changeDirection()
        }
    }

    /**
     * Chooses a new direction for the agent to move in, and sends the corresponding command to the game engine.
     *
     * @param forceChange If true, the agent will be forced to change direction even if it just changed directions.
     * If false, there is a chance the agent will keep its current direction.
     * @return new direction
     */
    override fun chooseDirection(forceChange: Boolean): Direction {
        // Get the current time in milliseconds
        val currentTime = System.currentTimeMillis()
        // If it hasn't been long enough since the last direction update, keep moving in the same direction, unless last move was blocked
        if (currentTime - lastDirectionUpdate < DIRECTION_REFRESH_RATE && !forceChange) {
            return currDirection
        }

        // Get a list of all the available directions the agent can move in
        val availableDirections = availableDirections
                .stream()
                .filter { e: Direction? -> supportedDirections.contains(e) }
                .collect(Collectors.toList())

        // If forceChange is true, remove the current direction from the list of available directions
        if (availableDirections.isEmpty()) {
            return currDirection
        }
        // Choose a new direction randomly, or keep the current direction with a certain probability
        var newDirection: Direction? = null
        if (Math.random() * 100 > CHANGE_DIRECTION_RATE && availableDirections.size != 1) {
            newDirection = currDirection
        }

        // If a new direction hasn't been chosen, choose one randomly from the available options
        if (newDirection == null) {
            newDirection = availableDirections[(Math.random() * availableDirections.size).toInt()]
        }

        // Send the command corresponding to the new direction to the game engine
        return newDirection!!
    }

    override fun changeDirection() {
        updateMovementDirection(chooseDirection(true))
    }

    override fun doUpdate(gameState: Boolean) {
        if (!canMove || !gameState) {
            return
        }

        if ("true" == XMLUtils.readConfig("bots_move")) {
            if (Bomberman.getMatch().isServer) {
                commandQueue.add(chooseDirection(false).toCommand())
                executeCommandQueue()
            }
        }
    }

    override fun onEliminated() {
        super.onEliminated()
        KilledEnemyEvent().invoke(this)
        ScoreGameEvent().invoke(maxHp)
    }

    companion object {
        const val DIRECTION_REFRESH_RATE = 500
    }
}