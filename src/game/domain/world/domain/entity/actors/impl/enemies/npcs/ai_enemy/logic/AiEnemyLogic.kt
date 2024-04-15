package game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic

import game.Bomberman
import game.domain.events.game.KilledEnemyEvent
import game.domain.events.game.ScoreGameEvent
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.abstracts.enemy.logic.EnemyEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.geo.Direction
import game.input.Command
import game.utils.Utility
import game.utils.dev.XMLUtils

open class AiEnemyLogic(override val entity: Enemy) : EnemyEntityLogic(entity = entity), IAiEnemyLogic {
    private val CHANGE_DIRECTION_RATE = 10 // percentage

    override fun onSpawn() {
        super.onSpawn()
        Bomberman.match.gameTickerObservable?.register(entity)
    }

    override fun doInteract(e: Entity?) {
        if (isObstacle(e) || e == null) {
            changeDirection()
        } else {
            super.doInteract(e)
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

    override fun onEliminated() {
        super.onEliminated()
        KilledEnemyEvent().invoke(entity)
        ScoreGameEvent().invoke(entity.state.maxHp)
    }

    override fun executeCommandQueue() {
        entity.state.enemyCommandQueue.forEach { c ->
            handleCommand(c)
        }
    }

    override fun addCommand(command: Command) {
        entity.state.enemyCommandQueue.add(command)
    }

    override fun removeCommand(command: Command) {
        entity.state.enemyCommandQueue.remove(command)
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {
        super.observerUpdate(arg)

        when (arg.identifier) {
            Observable2.ObserverParamIdentifier.GAME_TICK -> {
                val gameState = Bomberman.isInGame

                if (!entity.state.canMove || !gameState) {
                    return
                }

                process()
            }

            else -> {}
        }
    }

    override fun process() {
        if ("true" == XMLUtils.readConfig("bots_move")) {
            if (Bomberman.match.isServer) {
                move(chooseDirection(false))
                //entity.state.enemyCommandQueue.add(chooseDirection(false).toCommand())
                //executeCommandQueue()
            }
        }
    }
}