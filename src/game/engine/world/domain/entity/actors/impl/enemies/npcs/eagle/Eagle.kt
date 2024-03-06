package game.engine.world.domain.entity.actors.impl.enemies.npcs.eagle

import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.FlyingEnemy
import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.utils.file_system.Paths.enemiesFolder

class Eagle : FlyingEnemy {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)
    constructor() : super()

    override val info: EntityInfo = EntityInfo()

    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/eagle"
    ) {
        override fun characterOrientedImages(): Array<String> = Array(3) { index ->
            "$entitiesAssetsPath/eagle_${state.imageDirection.toString().lowercase()}_$index.png"
        }
    }

    override val properties: OrbEntityProperties = OrbEntityProperties(
            types = EntityTypes.Eagle
    )
}