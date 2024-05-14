package game.domain.world.domain.entity.actors.impl.blocks.destroyable_block.graphics

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import java.awt.image.BufferedImage

class DestroyableBlockGraphics : DefaultEntityGraphicsBehavior() {
    override fun getImage(entity: Entity): BufferedImage? {
        return loadAndSetImage(
                entity = entity,
                imagePath = JBomb.match.currentLevel!!.info.destroyableBlockImagePath
        )
    }
}