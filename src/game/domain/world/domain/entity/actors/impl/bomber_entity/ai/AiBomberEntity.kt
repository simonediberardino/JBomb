package game.domain.world.domain.entity.actors.impl.bomber_entity.ai

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.graphics.BomberEntityImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.IBomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityProperties
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.repository.RepositoryBomberEntitySkins
import game.repository.RepositoryBotNames

class AiBomberEntity(coordinates: Coordinates?, skinId: Int = RepositoryBomberEntitySkins.ids.random()) :
    BomberEntity(coordinates = coordinates) {
    constructor(coordinates: Coordinates?, id: Long, skinId: Int = RepositoryBomberEntitySkins.ids.random()) : this(
        coordinates = coordinates,
        skinId = skinId
    ) {
        this.info.id = id
    }

    constructor(id: Long) : this(coordinates = null, id = id)

    override val image: BomberEntityImageModel = BomberEntityImageModel(entity = this)

    override val properties: BomberEntityProperties = object : BomberEntityProperties(
        types = EntityTypes.BomberEntity,
        skinId = skinId
    ) {
        override val isBot: Boolean = true
        override var name: String? = RepositoryBotNames.choose()
    }

    override val logic: AiLogic = object:  AiLogic(entity = this) {
        override fun onAdded() {
            super.onAdded()
            JBomb.match.players.add(this@AiBomberEntity)
        }

        override fun onRemoved() {
            super.onRemoved()
            JBomb.match.players.removeIf { e -> e.info.id == entity.info.id}
        }
    }
}