package game.engine.world.domain.entity.actors.impl.enemies.npcs.helicopter

import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.npcs.flying_enemy.FlyingEnemy
import game.engine.world.types.EntityTypes
import game.utils.file_system.Paths.enemiesFolder

class Helicopter : FlyingEnemy {
    constructor() : super()
    constructor(id: Long) : super(id)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/heli",
    ) {
        override fun characterOrientedImages(): Array<String> {
            return Array(3) { index ->
                "$entitiesAssetsPath/heli_${state.imageDirection.toString().lowercase()}_$index.gif"
            }
        }
    }

    override val info: EntityInfo = EntityInfo()
    override val properties: CharacterEntityProperties = CharacterEntityProperties(types = EntityTypes.Helicopter)
    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)
}