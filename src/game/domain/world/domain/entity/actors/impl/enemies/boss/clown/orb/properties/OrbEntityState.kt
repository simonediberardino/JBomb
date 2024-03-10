package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.domain.world.domain.entity.geo.EnhancedDirection

open class OrbEntityState(
        entity: Entity,
        interactionEntities: MutableSet<Class<out Entity>> = Orb.DEFAULT.INTERACTION_ENTITIES,
        speed: Float = Orb.DEFAULT.SPEED,
        size: Int = Orb.DEFAULT.SIZE,
        maxHp: Int = Character.DEFAULT.MAX_HP
) : EnemyEntityState(
        entity = entity,
        size = size,
        speed = speed,
        interactionEntities = interactionEntities,
        maxHp = maxHp
) {
    /**
     * Only one field between enhancedDirection and direction can be instantiated at a time.
     * The enhancedDirection represents a direction that has been enhanced with additional directions.
     */
    var enhancedDirection: EnhancedDirection? = null
}