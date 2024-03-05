package game.engine.world.domain.entity.actors.impl.enemies.npcs.zombie

import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ghost_enemy.properties.GhostEnemyState
import game.engine.world.domain.entity.actors.impl.enemies.npcs.yellow_ball.properties.YellowBallProperties
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.Paths.enemiesFolder

class Zombie : AiEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/zombie"
    ) {
        override fun characterOrientedImages(): Array<String> = Array(4) {
            "$entitiesAssetsPath/zombie_${state.imageDirection.toString().lowercase()}_$it.png"
        }
    }

    override val info: EntityInfo = EntityInfo()
    override val state: GhostEnemyState = GhostEnemyState(entity = this)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val properties: CharacterEntityProperties = CharacterEntityProperties(
            types = EntityTypes.Zombie
    )

    internal object DEFAULT {
        const val SPEED = 0.5f
        const val MAX_HP = 300
    }
}