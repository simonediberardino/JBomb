package game.domain.world.domain.entity.actors.abstracts.enemy

import game.JBomb
import game.presentation.ui.panels.game.PitchPanel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb

abstract class Enemy : Character {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
    }

    override val state: EnemyEntityState = EnemyEntityState(entity = this)

    internal object DEFAULT {
        val SIZE = PitchPanel.GRID_SIZE
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>>  // Or player?
            get() = hashSetOf(BomberEntity::class.java, AbstractExplosion::class.java)
        val OBSTACLES: MutableSet<Class<out Entity>>
            get() = mutableSetOf(Bomb::class.java, Enemy::class.java, HardBlock::class.java, DestroyableBlock::class.java)
    }
}