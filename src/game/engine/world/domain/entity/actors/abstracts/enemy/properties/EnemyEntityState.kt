package game.engine.world.domain.entity.actors.abstracts.enemy.properties

import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.impl.bomb.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.player.BomberEntity

class EnemyEntityState(entity: Entity) : CharacterEntityState(
        entity = entity,
        size = Enemy.DEFAULT.SIZE,
        interactionEntities = Enemy.DEFAULT.INTERACTION_ENTITIES
)