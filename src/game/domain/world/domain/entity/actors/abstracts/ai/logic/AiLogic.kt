package game.domain.world.domain.entity.actors.abstracts.ai.logic

import game.JBomb
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.IAiLogic
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.utils.Utility
import game.utils.dev.XMLUtils
import kotlin.math.abs

open class AiLogic(override val entity: Character) : CharacterEntityLogic(entity = entity), IAiLogic {
    private val CHANGE_DIRECTION_RATE = 10 // percentage
    protected var destination: Coordinates? = null

    override fun onCollision(e: Entity) {
        super.onCollision(e)
    }
    /**
     * Chooses a new direction for the agent to move in, and sends the corresponding command to the game engine.
     *
     * @param forceChange If true, the agent will be forced to change direction even if it just changed directions.
     * If false, there is a chance the agent will keep its current direction.
     * @return new direction
     */
    override fun chooseDirection(forceChange: Boolean): Direction {
        // If it hasn't been long enough since the last direction update, keep moving in the same direction, unless last move was blocked
        if (Utility.timePassed(entity.state.lastDirectionUpdate) < AiEnemy.DIRECTION_REFRESH_RATE && !forceChange) {
            return entity.state.direction
        }

        // Get a list of all the available directions the agent can move in
        val availableDirections = entity.logic.availableDirections()
            .filter { e: Direction? -> entity.properties.supportedDirections.contains(e) }
            .ifEmpty {
                return entity.state.direction
            }

        // Choose a new direction randomly, or keep the current direction with a certain probability
        return if (Math.random() * 100 > CHANGE_DIRECTION_RATE && availableDirections.size != 1) {
            entity.state.direction
        } else {
            availableDirections[(Math.random() * availableDirections.size).toInt()]
        }
    }

    override fun changeDirection() {
        updateMovementDirection(chooseDirection(true))
    }

    override fun process() {
        if (!isBotsMoveEnabled()) return

        val destination = this.destination

        if (destination != null) {
            processMovementTowardsDestination(destination)
        } else {
            moveInRandomDirection()
        }
    }

    private fun isBotsMoveEnabled(): Boolean {
        return XMLUtils.readConfig("bots_move") == "true"
    }

    private fun processMovementTowardsDestination(destination: Coordinates) {
        val position = entity.info.position
        val stepSize = Character.DEFAULT.STEP_SIZE

        var moved = false
        var direction: Direction? = null

        if (abs(position.x - destination.x) > stepSize / 2) {
            direction = if (position.x < destination.x) {
                Direction.RIGHT
            } else {
                Direction.LEFT
            }
            moved = true
        }

        if (abs(position.y - destination.y) > stepSize / 2) {
            direction = if (position.y < destination.y) {
                Direction.DOWN
            } else {
                Direction.UP
            }
            moved = true
        }

        if (!moved) {
            this.destination = null
        } else {
            direction?.let {
                if (!moveOrInteract(it)) {
                    this.destination = null
                }

                updateMovementDirection(it)
            }
        }
    }

    private fun moveInRandomDirection() {
        val randomDirection = chooseDirection(false)
        move(randomDirection)
    }

    override fun doInteractWith(e: Entity?, spawnInteract: Boolean) {
        if (e is BomberEntity)
            return

        e?.logic?.interactWith(entity)
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {
        when (arg.identifier) {
            Observable2.ObserverParamIdentifier.GAME_TICK -> {
                val gameState = JBomb.isInGame

                if (!entity.state.canMove || !gameState) {
                    return
                }

                if (JBomb.match.isServer) {
                    process()
                }
            }

            else -> {}
        }
    }
}