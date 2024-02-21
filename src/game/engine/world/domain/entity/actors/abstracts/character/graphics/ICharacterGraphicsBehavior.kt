package game.engine.world.domain.entity.actors.abstracts.character.graphics

import game.engine.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.engine.world.domain.entity.geo.Direction

interface ICharacterGraphicsBehavior : IEntityGraphicsBehavior {
    fun directionUpdateGraphics(previousDirection: Direction?, currDirection: Direction?)
    fun setImageDirection(direction: Direction?)
    fun playStepSound()
}