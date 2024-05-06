package game.domain.world.domain.entity.actors.abstracts.animal

import game.Bomberman
import game.domain.world.domain.entity.actors.abstracts.ai.AiEntity
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.animal.state.AnimalEntityState
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.DestroyableBlock
import game.domain.world.domain.entity.actors.impl.blocks.hard_block.HardBlock
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.pickups.portals.imp.world_base.WorldPortal

abstract class AnimalEntity: AiEntity {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
        if (Bomberman.match.player != null) {
            info.position = Coordinates.randomCoordinatesFromPlayer(Character.DEFAULT.SIZE)
        }
    }

    internal object DEFAULT {
        val INTERACTION_ENTITIES: MutableSet<Class<out Entity>> = hashSetOf(AbstractExplosion::class.java)
        val OBSTACLES = mutableSetOf(
                Bomb::class.java,
                Character::class.java,
                HardBlock::class.java,
                DestroyableBlock::class.java,
                WorldPortal::class.java
        )
    }

    override val logic: AiLogic = object: AiLogic(entity = this) {
        override fun onCollision(e: Entity) {
            super.onTalk(e)

            if (e is BomberEntity && state.freezeOnCollideWithPlayer) {
                state.canMove = false
                graphicsBehavior.resetGraphics(entity)
            }
        }

        override fun onExitCollision(e: Entity) {
            super.onExitCollision(e)

            if (e is BomberEntity && state.freezeOnCollideWithPlayer) {
                state.canMove = true
            }
        }
    }

    override val state: AnimalEntityState = AnimalEntityState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
}