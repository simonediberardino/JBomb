package game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.graphics

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityImageModel
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import java.awt.image.BufferedImage

class ExplosionImageModel(
        entity: Entity,
        entitiesAssetsPath: String = Entity.DEFAULT.ENTITIES_ASSETS_PATH,
        hitboxSizeToWidthRatio: Float = Entity.DEFAULT.HITBOX_WIDTH_RATIO,
        hitboxSizeToHeightRatio: Float = Entity.DEFAULT.HITBOX_HEIGHT_RATIO,
        paddingTop: Int = Entity.DEFAULT.PADDING_TOP,
        paddingWidth: Int = Entity.DEFAULT.PADDING_WIDTH,
        imageRefreshRate: Int = AbstractExplosion.DEFAULT.IMAGE_REFRESH_RATE,
        _image: BufferedImage? = Entity.DEFAULT.IMAGE,
        lastImageIndex: Int = Entity.DEFAULT.LAST_IMAGE_INDEX,
        lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE,
        imagePath: String = Entity.DEFAULT.IMAGE_PATH,
) : EntityImageModel(
        entity = entity,
        entitiesAssetsPath = entitiesAssetsPath,
        hitboxSizeToWidthRatio = hitboxSizeToWidthRatio,
        hitboxSizeToHeightRatio = hitboxSizeToHeightRatio,
        paddingTop = paddingTop,
        paddingWidth = paddingWidth,
        imageRefreshRate = imageRefreshRate,
        _image = _image,
        lastImageIndex = lastImageIndex,
        lastImageUpdate = lastImageUpdate,
        imagePath = imagePath
) {
}