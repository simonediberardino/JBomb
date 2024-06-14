package game.domain.world.domain.entity.actors.abstracts.ai.logic

import game.JBomb
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.IAiLogic
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.utils.Utility
import game.utils.dev.XMLUtils

open class AiLogic(override val entity: Character) : CharacterEntityLogic(entity = entity), IAiLogic {
    private val CHANGE_DIRECTION_RATE = 10 // percentage

    override fun onSpawn() {
        super.onSpawn()
        JBomb.match.gameTickerObservable?.register(entity)
    }

    override fun onDespawn() {
        super.onDespawn()
        val match = JBomb.match
        (match.gameTickerObservable ?: return).unregister(entity)
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

        //return the first direction that leads to an entity that can be interacted with, or the opposite of the first direction that leads to an entity that can be interacted by. Basically the subject will
        //follow entities it can interact with or escape from entities it can be interacted by.
        availableDirections.forEach {
            Coordinates.getEntitiesOnBlock(Coordinates.getCoordinatesOnDirection(it, entity.info.position, entity.state.size / 3 * 2)).forEach { e ->
                if (canInteractWith(e)) return it else if (canBeInteractedBy(e)) return it.opposite()
                // first check canInteractWith, THEN check canBeInteractedBy. If order is flipped and both conditions are true, entities will escape from entities they can
                //interact with only because they can be interacted by them. Consider making sure only one condition is true at a time. Ex: at the moment bomberEntities can be interacted by enemies,
                // and for some reason also can interact with them.
            }
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
        if ("true" == XMLUtils.readConfig("bots_move")) {
            move(chooseDirection(false))
        }
    }

    override fun doInteract(e: Entity?) {}

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