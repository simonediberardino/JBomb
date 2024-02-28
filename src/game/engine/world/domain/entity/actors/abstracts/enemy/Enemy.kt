package game.engine.world.domain.entity.actors.abstracts.enemy

import game.Bomberman
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.enemy.logic.EnemyEntityLogic
import game.engine.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

abstract class Enemy : Character {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
        if (Bomberman.getMatch().player != null) {
            info.position = Coordinates.randomCoordinatesFromPlayer(Character.DEFAULT.SIZE)
        }
    }

    override val state: EnemyEntityState = EnemyEntityState(entity = this)
    abstract override val logic: EnemyEntityLogic

    override fun update(arg: Any?) {
        super.update(arg)
        if (arg as Boolean) logic.observerUpdate(arg)
    }

    internal object DEFAULT {
        val SIZE = PitchPanel.GRID_SIZE
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>> = hashSetOf(BomberEntity::class.java, AbstractExplosion::class.java) // Or player?
    }
}