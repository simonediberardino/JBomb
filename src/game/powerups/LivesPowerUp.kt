package game.powerups

import game.data.DataInputOutput
import game.entity.EntityTypes
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage
class LivesPowerUp : PowerUp {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override fun getImage(): BufferedImage =
            loadAndSetImage("$powerUpsFolder/lives_up.png")

    override val duration: Int
        get() = 0

    override fun doApply(entity: BomberEntity) {
        DataInputOutput.getInstance().increaseLives()
    }

    override fun cancel(entity: BomberEntity) {
        // No need to implement anything here
    }

    override val isDisplayable: Boolean
        get() = false

    override fun canPickUp(entity: BomberEntity): Boolean = true

    override val type: EntityTypes
        get() = EntityTypes.LivesPowerUp
}
