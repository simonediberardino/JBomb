package game.domain.world.domain.entity.actors.impl.enemies.npcs.tank.logic

import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.logic.AiEnemyLogic
import game.domain.world.domain.entity.actors.impl.enemies.npcs.tank.TankEnemy
import game.domain.world.domain.entity.actors.impl.explosion.FireExplosion
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.utils.Utility

class TankEnemyLogic(override val entity: TankEnemy) : AiEnemyLogic(entity = entity) {
    override fun process() {
        val currentTime = now()

        // Check if it's time to update the shooting behavior
        if (Utility.timePassed(entity.state.lastFire) <= TankEnemy.DEFAULT.SHOOTING_REFRESH_RATE) return


        // Check if the entity can shoot and if a random probability allows shooting
        if (!entity.state.canShoot) {
            return
        }

        Utility.runPercentage(TankEnemy.DEFAULT.PROBABILITY_OF_SHOOTING) {
            entity.state.lastFire = currentTime

            val direction = entity.state.direction
            // Calculate new coordinates with an explosion offset for vertical directions
            var newCoords = Coordinates.getNewTopLeftCoordinatesOnDirection(
                    entity.info.position,
                    direction,
                    AbstractExplosion.SIZE
            )

            if (direction == Direction.UP || direction == Direction.DOWN) {
                val x = newCoords.x + AbstractExplosion.SPAWN_OFFSET
                newCoords = Coordinates(x, newCoords.y)
            }

            ExplosionHandler.instance.process {
                listOf(FireExplosion(
                        owner = entity,
                        coordinates = newCoords,
                        direction = direction,
                        explosive = entity
                ))
            }

            entity.state.canMove = false
        }

        entity.state.canShoot = true
    }
}