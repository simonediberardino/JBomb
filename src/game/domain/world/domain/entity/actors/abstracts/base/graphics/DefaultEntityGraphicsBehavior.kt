package game.domain.world.domain.entity.actors.abstracts.base.graphics

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.utils.Utility.fileExists
import game.utils.Utility.loadImage
import game.utils.time.now
import java.awt.image.BufferedImage
import java.util.*
import java.util.regex.Pattern

abstract class DefaultEntityGraphicsBehavior : IEntityGraphicsBehavior {
    override fun loadAndSetImage(entity: Entity, imagePath: String): BufferedImage? {
        if (entity.state.state == null)
            return doLoadAndSetImage(entity, imagePath)

        val toks = imagePath.split(Pattern.quote(".").toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val extension = toks[1]
        val fileName = toks[0]
        val imagePathWithStatus = "${fileName}_${entity.state.state.toString().lowercase(Locale.getDefault())}.$extension"
        val hasImageWithStatus = fileExists(imagePathWithStatus)
        return if (hasImageWithStatus) doLoadAndSetImage(entity, imagePathWithStatus) else doLoadAndSetImage(entity, imagePath)
    }

    private fun doLoadAndSetImage(entity: Entity, imagePath: String): BufferedImage? {
        entity.state.lastImageUpdate = now()

        return loadImage(imagePath)?.let {
            entity.image._image = loadImage(imagePath)
            entity.image.imagePath = imagePath
            return entity.image._image
        }
    }
}