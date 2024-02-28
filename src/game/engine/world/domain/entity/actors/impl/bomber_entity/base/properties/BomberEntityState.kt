package game.engine.world.domain.entity.actors.impl.bomber_entity.base.properties

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.domain.entity.actors.abstracts.enemy.Enemy
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.engine.world.domain.entity.items.UsableItem
import game.engine.world.domain.entity.pickups.powerups.PowerUp

class BomberEntityState(
        entity: BomberEntity
) : CharacterEntityState(
        entity = entity,
        interactionEntities = hashSetOf(AbstractExplosion::class.java, Enemy::class.java, PowerUp::class.java),
        size = Character.DEFAULT.SIZE,
) {
    lateinit var weapon: UsableItem
    val entitiesClassListMouseClick: MutableList<Class<out Entity>> = mutableListOf()
    val entitiesClassListMouseDrag: MutableList<Class<out Entity>> = mutableListOf()
    var currExplosionLength = 0
    var placedBombs = 0
    var lastPlacedBombTime: Long = 0
    var currentBombs = 0

    var bombsSolid: Boolean = true
        set(value) {
            field = value

            // Adjust the whitelist of obstacles based on the bombs' solidity.
            if (!field) {
                whitelistObstacles.add(Bomb::class.java)
            } else if (!forceBombsSolid) {
                whitelistObstacles.remove(Bomb::class.java)
            }
        }

    var forceBombsSolid: Boolean = false
    var activePowerUps: MutableList<Class<out PowerUp>> = mutableListOf()
}
