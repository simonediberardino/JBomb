package game.engine.world.domain.entity.actors.impl.enemies.npcs.yellow_ball

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.properties.YellowBallProperties
import game.utils.file_system.Paths.enemiesFolder

class YellowBall : AiEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/yellow_ball/yellow_ball"
    ) {
        override fun characterOrientedImages(): Array<String> {
            return Array(4) { index ->
                "$enemiesFolder/yellow_ball/yellow_ball_${state.imageDirection.toString().lowercase()}_$index.png"
            }
        }
    }

    override val info: EntityInfo = EntityInfo()
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val properties: YellowBallProperties = YellowBallProperties(types = EntityTypes.YellowBall)

    internal object DEFAULT {
        val IMAGE_DIRECTIONS = listOf(Direction.RIGHT, Direction.LEFT)
        val SIZE = PitchPanel.COMMON_DIVISOR * 2
    }
}