package game.engine.world.domain.entity.actors.abstracts.character.graphics

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.base.EntityImageModel
import java.awt.image.BufferedImage

class CharacterImageModel(
        entity: Entity,
        entitiesAssetsPath: String = "",
        hitboxSizeToWidthRatio: Float = 1f,
        hitboxSizeToHeightRatio: Float = 1f,
        paddingTop: Int = 0,
        paddingWidth: Int = 0,
        imageRefreshRate: Int = 200,
        _image: BufferedImage? = null,
        lastImageIndex: Int = 0,
        lastImageUpdate: Long = 0,
        imagePath: String = "",
        val characterOrientedImages: Array<String>,
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
)