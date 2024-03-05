package game.engine.world.domain.entity.actors.impl.enemies.npcs.clown_nose

import game.engine.world.types.EntityTypes
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.domain.entity.geo.EnhancedDirection
import game.engine.sound.SoundModel
import game.engine.world.domain.entity.actors.abstracts.base.EntityInfo
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityProperties
import game.utils.Paths.enemiesFolder

class ClownNose : Orb {
    constructor(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : super(coordinates, enhancedDirection)
    constructor(coordinates: Coordinates?, direction: Direction?) : super(coordinates, direction)
    constructor() : this(null, null as Direction?)
    constructor(id: Long) : super(id)

    override val info: EntityInfo = EntityInfo()

    override val graphicsBehavior: ICharacterGraphicsBehavior = CharacterGraphicsBehavior(entity = this)

    override val image: CharacterImageModel = object : CharacterImageModel(
            entity = this,
            entitiesAssetsPath = "$enemiesFolder/clown/clown_orb.png"
    ) {
        override fun characterOrientedImages(): Array<String> = arrayOf(entitiesAssetsPath)
    }

    override val properties: OrbEntityProperties = OrbEntityProperties(
            types = EntityTypes.ClownNose,
            deathSound = SoundModel.CLOWN_NOSE_DEATH
    )
}