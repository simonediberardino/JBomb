package game.domain.world.domain.entity.actors.impl.enemies.npcs.zombie.properties

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.zombie.Zombie

class ZombieState(
        entity: Entity,
        maxHp: Int = Zombie.DEFAULT.MAX_HP,
        speed: Float = Zombie.DEFAULT.SPEED,
        
) : EnemyEntityState(
        entity = entity,
        maxHp = maxHp,
        speed = speed,
)