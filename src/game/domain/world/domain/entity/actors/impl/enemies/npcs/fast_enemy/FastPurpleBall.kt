package game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy

import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.properties.FastEnemyProperties
import game.domain.world.domain.entity.actors.impl.enemies.npcs.fast_enemy.properties.FastEnemyState
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.types.EntityTypes
import game.presentation.ui.panels.game.PitchPanel
import game.utils.file_system.Paths.enemiesFolder

/**
 * An enemy with increased speed multiplier;
 */
class FastPurpleBall : AiEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/fast_enemy/fast_enemy"
    ) {
        override var hitboxSizeToHeightRatio = FastPurpleBall.DEFAULT.HITBOX_SIZE_TO_HEIGHT_RATIO

        override fun characterOrientedImages(): Array<String> = Array(4) { index ->
            "${entitiesAssetsPath}_${state.imageDirection.toString().lowercase()}_$index.png"
        }
    }

    override val properties: FastEnemyProperties = FastEnemyProperties(types = EntityTypes.FastEnemy)
    override val state: EnemyEntityState = FastEnemyState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)

    internal object DEFAULT {
        val SPEED = 1.5f
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
        const val HITBOX_SIZE_TO_HEIGHT_RATIO = 0.527f
        val IMAGE_DIRECTIONS: List<Direction> = listOf(Direction.RIGHT, Direction.LEFT)
    }
}