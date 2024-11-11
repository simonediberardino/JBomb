package game.domain.world.domain.entity.actors.impl.bomber_entity.ai

import com.sun.org.apache.xpath.internal.operations.Bool
import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.ai.logic.AiBomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.graphics.BomberEntityImageModel
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.IBomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.properties.BomberEntityProperties
import game.domain.world.domain.entity.actors.impl.enemies.npcs.ai_enemy.AiEnemy
import game.domain.world.domain.entity.actors.impl.explosion.PistolExplosion
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.explosion.handler.ExplosionHandler
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.items.PistolItem
import game.domain.world.types.EntityTypes
import game.presentation.ui.panels.game.PitchPanel.GRID_SIZE
import game.repository.RepositoryBomberEntitySkins
import game.repository.RepositoryBotNames
import game.utils.Utility
import game.utils.time.now
import kotlin.math.abs
import kotlin.math.round

class AiBomberEntity(
    coordinates: Coordinates?,
    skinId: Int = RepositoryBomberEntitySkins.ids.random()
) : BomberEntity(coordinates = coordinates) {
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

    override val logic: AiBomberEntityLogic = AiBomberEntityLogic(this)
}