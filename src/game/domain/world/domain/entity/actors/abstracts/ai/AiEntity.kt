package game.domain.world.domain.entity.actors.abstracts.ai

import game.domain.world.domain.entity.actors.abstracts.ai.logic.AiLogic
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.geo.Coordinates

abstract class AiEntity : Character {
    constructor() : super()
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val logic: AiLogic = AiLogic(entity = this)
}