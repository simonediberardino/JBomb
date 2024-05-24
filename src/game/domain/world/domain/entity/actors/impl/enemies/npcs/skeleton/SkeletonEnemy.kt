package game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.FiringEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.logic.FiringEnemyLogic
import game.domain.world.domain.entity.actors.impl.enemies.npcs.firing_enemy.state.FiringEnemyState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.skeleton.state.SkeletonEnemyState
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.utils.file_system.Paths

class SkeletonEnemy : FiringEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: FiringEnemyLogic = FiringEnemyLogic(entity = this)
    override val state: SkeletonEnemyState = object: SkeletonEnemyState(entity = this) {}

    override val properties: CharacterEntityProperties = CharacterEntityProperties(types = EntityTypes.Skeleton)

    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val image: CharacterImageModel = object : CharacterImageModel(entity = this) {
        override fun characterOrientedImages(): Array<String> =
                Array(4) { index ->
                    "${Paths.enemiesFolder}/skeleton/skeleton_${state.imageDirection.toString().lowercase()}_$index.png"
                }
    }

    internal object DEFAULT {
        const val SPEED = 0.7f
    }
}