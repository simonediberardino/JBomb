package game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.logic.ICharacterEntityLogic
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp

interface IBomberEntityLogic : ICharacterEntityLogic {
    fun isMouseDragInteractable(entity: Entity): Boolean
    fun isMouseClickInteractable(e: Entity): Boolean
    fun addClassInteractWithMouseClick(cls: Class<out Entity>)
    fun addClassInteractWithMouseDrag(cls: Class<out Entity>)
    fun removeClassInteractWithMouseClick(cls: Class<out Entity>)
    fun removeClassInteractWithDrag(cls: Class<out Entity>)
    fun removeActivePowerUp(p: PowerUp?)
    fun initBombVariables()
}