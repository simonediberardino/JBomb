package game.entity.placeable

import game.entity.blocks.MovableBlock
import game.entity.models.Character
import game.entity.models.Coordinates
import game.http.dao.EntityDao

abstract class PlaceableEntity(protected val user: Character) : MovableBlock(Coordinates.getCenterCoordinatesOfEntity(user)) {
    override val extras: Map<String, String>
        get() = hashMapOf(
                "callerId" to user.id.toString()
        )
}