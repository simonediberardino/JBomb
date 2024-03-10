package game.domain.world.domain.entity.actors.impl.enemies.npcs.clown_nose

import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.geo.EnhancedDirection
import game.audio.SoundModel
import game.domain.world.domain.entity.actors.abstracts.base.EntityInfo
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.domain.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties.OrbEntityProperties
import game.utils.file_system.Paths.enemiesFolder

class ClownNose : Orb {
    constructor(coordinates: Coordinates?, enhancedDirection: EnhancedDirection?) : super(coordinates, enhancedDirection)
    constructor(coordinates: Coordinates?, direction: Direction?) : super(coordinates, direction)
    constructor() : this(null, null as Direction?)
    constructor(id: Long) : super(id)

    

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