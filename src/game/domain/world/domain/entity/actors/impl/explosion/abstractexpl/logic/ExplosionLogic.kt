package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.logic

import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.logic.MovingEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.models.Explosive
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction

class ExplosionLogic(
        override val entity: AbstractExplosion
) : MovingEntityLogic(entity), IExplosionLogic {
    override fun onAttackReceived(damage: Int) {}

    override fun doInteract(e: Entity?) {
        e?.logic?.onExplosion(entity)
    }

    override fun canBeInteractedBy(e: Entity?): Boolean {
        return e == null || entity.state.explosive.explosionInteractionEntities.any { c -> c.isInstance(e) }

    }

    override fun canInteractWith(e: Entity?): Boolean =
            e == null || entity.state.explosive.explosionInteractionEntities.any { c -> c.isInstance(e) }

    override fun onCollision(e: Entity) {
        super.onCollision(e)

        if (canInteractWith(e) || canBeInteractedBy(e)) {
            e.logic.onExplosion(entity)
        }
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {}
    override fun explode() : AbstractExplosion{
        return explode(null)
        //se last explosion è null, l'esplosione non ha un centro (es. le esplosioni dei carri armati)
    }
    override fun explode(lastExplosion : AbstractExplosion? ): AbstractExplosion {
        val allCoordinates = Coordinates.getAllCoordinates(
                entity.info.position,
                entity.state.size
        )
        val collidedEntities = Coordinates.getEntitiesOnCoordinates(allCoordinates)

        if (collidedEntities.all { !entity.logic.isObstacle(it) }) {
            spawn()
            //perché le interazioni venivano gestite al di fuori dell'if?
            collidedEntities.forEach {
                if (entity.logic.canInteractWith(it)) {
                    entity.logic.interact(it)
                }
            }
        } else {
            //non genero la nuova esplosione e aggiungo la direzione che è risultata essere ostacolata a l'ultima esplosione generata, in modo tale che possa cambiare la propria texture di conseguenza
            lastExplosion?.let { explosion -> explosion.state.blockedDirections?.add(explosion.state.direction) }
        }
        return entity
    }

    override fun spawn() {
        if (entity.state.distanceFromExplosive != 0 || entity.state.isCenterVisible) {
            super.spawn(
                    forceSpawn = true,
                    forceCentering = false
            )
        }

        if (canExpand()) {
            expandBomb(entity.state.direction, entity.state.size)
        }
    }

    override fun canExpand(): Boolean {
        val maxExplosionDistance = entity.state.explosive.maxExplosionDistance

        entity.state.canExpand = entity.state.distanceFromExplosive < maxExplosionDistance
        return entity.state.canExpand
    }
    override fun isBlockedOnRight() : Boolean{
        return entity.state.blockedDirections?.contains(Direction.RIGHT)?:false
    }
    override fun isBlockedOnLeft() : Boolean{
        return entity.state.blockedDirections?.contains(Direction.LEFT)?:false
    }
    override fun isBlockedOnDown() : Boolean{
        return entity.state.blockedDirections?.contains(Direction.DOWN)?:false
    }
    override fun isBlockedOnUp() : Boolean{
        return entity.state.blockedDirections?.contains(Direction.UP)?:false
    }


    override fun expandBomb(d: Direction, stepSize: Int) {
        try {
            val constructor = entity.properties.explosionClass.getConstructor(
                    Entity::class.java,
                    Coordinates::class.java,
                    Direction::class.java,
                    Int::class.javaPrimitiveType,
                    Explosive::class.java,
                    Boolean::class.javaPrimitiveType
            )

            val currCoords = entity.info.position

            val newCoords = when (d) {
                Direction.DOWN -> currCoords.plus(Coordinates(0, stepSize))
                Direction.LEFT -> currCoords.plus(Coordinates(-stepSize, 0))
                Direction.UP -> currCoords.plus(Coordinates(0, -stepSize))
                Direction.RIGHT -> currCoords.plus(Coordinates(stepSize, 0))
            }

            constructor.newInstance(
                    entity.state.owner,
                    newCoords,
                    entity.state.direction,
                    entity.state.distanceFromExplosive + 1,
                    entity.state.explosive,
                    false
            )!!.logic.explode(entity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onObstacle(coordinates: Coordinates) {

    }

    override fun isObstacle(e: Entity?): Boolean = e == null || entity.state.explosive.isObstacleOfExplosion(e)

    override fun onMove(coordinates: Coordinates) {}
}