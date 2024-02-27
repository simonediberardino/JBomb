package game.engine.world.domain.entity.actors.abstracts.character.graphics

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import java.awt.image.BufferedImage

abstract class CharacterImageModel(
        entity: Entity,
        entitiesAssetsPath: String = Entity.DEFAULT.ENTITIES_ASSETS_PATH,
        hitboxSizeToWidthRatio: Float = Entity.DEFAULT.HITBOX_WIDTH_RATIO,
        hitboxSizeToHeightRatio: Float = Entity.DEFAULT.HITBOX_HEIGHT_RATIO,
        paddingTop: Int = Entity.DEFAULT.PADDING_TOP,
        paddingWidth: Int = Entity.DEFAULT.PADDING_WIDTH,
        imageRefreshRate: Int = Entity.DEFAULT.IMAGE_REFRESH_RATE,
        _image: BufferedImage? = Entity.DEFAULT.IMAGE,
        lastImageIndex: Int = Entity.DEFAULT.LAST_IMAGE_INDEX,
        lastImageUpdate: Long = Entity.DEFAULT.LAST_IMAGE_UPDATE,
        imagePath: String = Entity.DEFAULT.IMAGE_PATH
) : EntityImageModel(
        entity,
        entitiesAssetsPath,
        hitboxSizeToWidthRatio,
        hitboxSizeToHeightRatio,
        paddingTop,
        paddingWidth,
        imageRefreshRate,
        _image,
        lastImageIndex,
        lastImageUpdate,
        imagePath
) {
    abstract fun characterOrientedImages(): Array<String>
}