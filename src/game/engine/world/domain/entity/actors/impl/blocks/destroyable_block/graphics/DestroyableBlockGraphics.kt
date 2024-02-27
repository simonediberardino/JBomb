package game.engine.world.domain.entity.actors.impl.blocks.destroyable_block.graphics

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import java.awt.image.BufferedImage

class DestroyableBlockGraphics : DefaultEntityGraphicsBehavior() {
    override fun getImage(entity: Entity): BufferedImage {
        return loadAndSetImage(
                entity = entity,
                imagePath = Bomberman.getMatch().currentLevel!!.info.destroyableBlockImagePath
        )
    }
}