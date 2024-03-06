package game.engine.world.domain.entity.actors.impl.enemies.npcs.fast_enemy

import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.properties.FastEnemyProperties
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.types.EntityTypes
import game.utils.file_system.Paths.enemiesFolder

/**
 * An enemy with increased speed multiplier;
 */
class FastEnemy : AiEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/fast_enemy/fast_enemy"
    ) {
        override var hitboxSizeToHeightRatio = FastEnemy.DEFAULT.HITBOX_SIZE_TO_HEIGHT_RATIO

        override fun characterOrientedImages(): Array<String> = Array(4) { index ->
            "${entitiesAssetsPath}_${state.imageDirection.toString().lowercase()}_$index.png"
        }
    }

    override val properties: FastEnemyProperties = FastEnemyProperties(types = EntityTypes.FastEnemy)
    override val info: EntityInfo = EntityInfo()
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)

    internal object DEFAULT {
        val SPEED = 1.5f
        val SIZE = Character.DEFAULT.SIZE
        const val HITBOX_SIZE_TO_HEIGHT_RATIO = 0.527f
        val IMAGE_DIRECTIONS: List<Direction> = listOf(Direction.RIGHT, Direction.LEFT)
    }
}