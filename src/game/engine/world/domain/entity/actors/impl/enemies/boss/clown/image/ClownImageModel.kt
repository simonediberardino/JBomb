package game.engine.world.domain.entity.actors.impl.enemies.boss.clown.image

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterImageModel
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.Clown

class ClownImageModel(entity: Entity) : CharacterImageModel(entity = entity){
    override var hitboxSizeToWidthRatio = Clown.DEFAULT.RATIO_WIDTH

    override var hitboxSizeToHeightRatio: Float = Clown.DEFAULT.RATIO_HEIGHT_WITH_HAT
        get() {
            entity as Clown
            // Set the height to hitbox size ratio based on whether the Boss has a hat or not.
            field = if (entity.state.hasHat) Clown.DEFAULT.RATIO_HEIGHT_WITH_HAT else Clown.DEFAULT.RATIO_HEIGHT
            return field
        }

    override fun characterOrientedImages(): Array<String> {
        entity as Clown
        return arrayOf(entity.graphicsBehavior.getImageFromRageStatus())
    }
}