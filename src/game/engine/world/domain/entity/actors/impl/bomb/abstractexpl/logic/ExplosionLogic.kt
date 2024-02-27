package game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.logic

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.moving_entity.logic.MovingEntityLogic
import game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.models.Explosive
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import java.lang.Exception

class ExplosionLogic(override val entity: AbstractExplosion) : MovingEntityLogic(entity), IExplosionLogic {
    override fun onAttackReceived(damage: Int) {}

    override fun doInteract(e: Entity?) {
        e?.logic?.onExplosion(entity)
    }

    override fun onGameTick(arg: Any?) {}

    override fun explode() {
        spawn(forceSpawn = true, forceCentering = false)
    }

    override fun canExpand(): Boolean {
        if (entity.state.distanceFromExplosive >= entity.state.explosive.maxExplosionDistance) entity.state.canExpand = false
        return entity.state.canExpand
    }

    override fun expandBomb(d: Direction, stepSize: Int) {
        moveOrInteract(d, stepSize, true)
    }

    override fun onObstacle(coordinates: Coordinates) {
        try {
            val constructor = entity.properties.explosionClass.getConstructor(
                    Entity::class.java,
                    Coordinates::class.java,
                    Direction::class.java,
                    Int::class.javaPrimitiveType,
                    Explosive::class.java,
                    Boolean::class.javaPrimitiveType
            )
            constructor.newInstance(
                    entity.state.owner,
                    coordinates,
                    entity.state.direction,
                    entity.state.distanceFromExplosive + 1,
                    entity.state.explosive,
                    false
            )!!.logic.explode()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun isObstacle(e: Entity?): Boolean {
        return e == null || entity.state.explosive.isObstacleOfExplosion(e)
    }

    override fun onMove(coordinates: Coordinates) {}

    init {
        // On first (center) explosion
        if (entity.state.distanceFromExplosive == 0) {
            val desiredCoords = Coordinates.getAllCoordinates(entity.info.position, entity.state.size)
            Bomberman.getMatch().getEntities()
                    .parallelStream()
                    .filter { e: Entity -> desiredCoords.any { coord: Coordinates? -> Coordinates.doesCollideWith(coord, e) } }
                    .forEach { e: Entity -> entity.logic.interact(e) }
        }

        if (canExpand()) {
            expandBomb(entity.state.direction, entity.state.size)
        }
    }
}