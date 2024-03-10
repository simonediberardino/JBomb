package game.domain.world.domain.entity.actors.abstracts.enemy

import game.Bomberman
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.logic.EnemyEntityLogic
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

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

    internal object DEFAULT {
        val SIZE = PitchPanel.GRID_SIZE
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>> = hashSetOf(BomberEntity::class.java, AbstractExplosion::class.java) // Or player?
    }
}