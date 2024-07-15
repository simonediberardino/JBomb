package game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic

import game.domain.events.game.*
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.utils.Utility
import game.utils.dev.Log

open class AiEnemyLogic(override val entity: Enemy) : AiLogic(entity = entity) {
    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let {
            attack(it)
        }
    }

    override fun onSpawn() {
        super.onSpawn()
        IncreaseEnemiesAliveGameEvent().invoke(null)
    }

    override fun onDespawn() {
        super.onDespawn()
        DecreaseEnemiesAliveGameEvent().invoke(null)
        EnemyDespawnedGameEvent().invoke(null)
    }

    override fun chooseDirection(forceChange: Boolean): Direction {
        // If it hasn't been long enough since the last direction update, keep moving in the same direction, unless last move was blocked
        if (Utility.timePassed(entity.state.lastDirectionUpdate) < AiEnemy.DIRECTION_REFRESH_RATE && !forceChange) {
            return entity.state.direction
        }

        if (entity.state.followPlayers) {
            // Get a list of all the available directions the agent can move in
            val availableDirections = entity.logic.availableDirections()
                    .filter { e: Direction? -> entity.properties.supportedDirections.contains(e) }
                    .ifEmpty {
                        return entity.state.direction
                    }

            //return the first direction that leads to an entity that can be interacted with, or the opposite of the first direction that leads to an entity that can be interacted by. Basically the subject will
            //follow entities it can interact with or escape from entities it can be interacted by.
            availableDirections.forEach {
                Coordinates.getEntitiesOnBlock(
                        Coordinates.getCoordinatesOnDirection(it, entity.info.position, entity.state.size / 3 * 2)
                ).forEach { e ->
                    if (e is BomberEntity && canInteractWith(e))
                        return it

                    // first check canInteractWith, THEN check canBeInteractedBy. If order is flipped and both conditions are true, entities will escape from entities they can
                    //interact with only because they can be interacted by them. Consider making sure only one condition is true at a time. Ex: at the moment bomberEntities can be interacted by enemies,
                    // and for some reason also can interact with them.
                }
            }
        }

        return super.chooseDirection(forceChange)
    }


    override fun onEliminated() {
        super.onEliminated()
        KilledEnemyEvent().invoke(entity)
        ScoreGameEvent().invoke(entity.state.maxHp)
    }
}