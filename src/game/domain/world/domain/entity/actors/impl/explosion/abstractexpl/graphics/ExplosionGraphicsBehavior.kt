package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.graphics

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import java.awt.image.BufferedImage

class ExplosionGraphicsBehavior : DefaultEntityGraphicsBehavior() {
    override fun getImage(entity: Entity): BufferedImage?{
        entity as AbstractExplosion
        val imageName = when (entity.state.distanceFromExplosive) {
            0 -> {
                val centralImage = "_central${entity.state._state}.png"
                "${entity.image.entitiesAssetsPath}$centralImage"
            }

            else -> {
                val isLast = if (entity.state.canExpand) "" else "_last"
                val imageFileName = "_${entity.state.direction.toString().lowercase()}"
                val expandedImage = "${entity.image.entitiesAssetsPath}$imageFileName$isLast${entity.state._state}.png"
                expandedImage
            }
        }

        return loadAndSetImage(entity, imageName)
    }
}