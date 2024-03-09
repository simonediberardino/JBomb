package game.domain.world.domain.entity.actors.abstracts.character.graphics

import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.geo.Direction

interface ICharacterGraphicsBehavior : IEntityGraphicsBehavior {
    fun directionUpdateGraphics(previousDirection: Direction?, currDirection: Direction?)
    fun setImageDirection(direction: Direction?)
    fun playStepSound()
}