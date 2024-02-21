package game.engine.world.domain.entity.actors.abstracts.entity_interactable.logic

import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.IEntityGameBehavior
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction

interface IEntityInteractableLogic : IEntityGameBehavior {
    fun moveOrInteract(d: Direction?, stepSize: Int, ignoreMapBorders: Boolean): Boolean
    fun moveOrInteract(d: Direction?, stepSize: Int = PitchPanel.PIXEL_UNIT): Boolean
    fun move(coordinates: Coordinates)
    fun onMove(coordinates: Coordinates)
    fun attack(e: Entity?)
    fun isObstacle(e: Entity?): Boolean
    fun canInteractWith(e: Entity?): Boolean
}