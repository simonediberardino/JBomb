package game.engine.world.domain.entity.actors.impl.bomber_entity.base.logic

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.logic.ICharacterEntityLogic
import game.engine.world.domain.entity.pickups.powerups.base.PowerUp

interface IBomberEntityLogic : ICharacterEntityLogic {
    fun isMouseDragInteractable(cls: Class<out Entity>): Boolean
    fun isMouseClickInteractable(cls: Class<out Entity>): Boolean
    fun addClassInteractWithMouseClick(cls: Class<out Entity>)
    fun addClassInteractWithMouseDrag(cls: Class<out Entity>)
    fun removeClassInteractWithMouseClick(cls: Class<out Entity>)
    fun removeClassInteractWithDrag(cls: Class<out Entity>)
    fun removeActivePowerUp(p: PowerUp?)
    fun getMaxBombs(): Int
}