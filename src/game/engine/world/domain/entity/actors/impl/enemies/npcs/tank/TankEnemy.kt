package game.engine.world.domain.entity.actors.impl.enemies.npcs.tank

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.engine.world.domain.entity.actors.impl.placeable.Bomb
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.models.Explosive
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.engine.world.domain.entity.actors.impl.enemies.npcs.tank.logic.TankEnemyLogic
import game.engine.world.domain.entity.actors.impl.enemies.npcs.tank.properties.TankEnemyState
import game.utils.file_system.Paths.enemiesFolder

class TankEnemy : AiEnemy, Explosive {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: TankEnemyLogic = TankEnemyLogic(entity = this)
    override val state: TankEnemyState = TankEnemyState(entity = this)
    override val properties: CharacterEntityProperties = CharacterEntityProperties(types = EntityTypes.TankEnemy)
    override val info: EntityInfo = EntityInfo()
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
    override val image: CharacterImageModel = object : CharacterImageModel(entity = this) {
        override fun characterOrientedImages(): Array<String> =
                arrayOf("$enemiesFolder/tank/tank_${state.imageDirection.toString().lowercase()}.png")
    }

    override val explosionObstacles: Set<Class<out Entity>> = emptySet()
    override val explosionInteractionEntities: Set<Class<out Entity>> = setOf(
            Player::class.java,
            Bomb::class.java
    )
    override val maxExplosionDistance: Int = 4

    internal object DEFAULT {
        const val CAN_SHOOT = true
        const val LAST_FIRE = 0L
        const val PROBABILITY_OF_SHOOTING = 30
        const val SHOOTING_REFRESH_RATE = 2000
    }
}