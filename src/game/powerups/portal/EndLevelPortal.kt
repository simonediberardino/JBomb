package game.powerups.portal

import game.Bomberman
import game.data.DataInputOutput
import game.entity.EntityTypes
import game.entity.player.BomberEntity
import game.entity.models.Coordinates
import game.level.levels.lobby.WorldSelectorLevel
import game.utils.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class EndLevelPortal(coordinates: Coordinates?) : Portal(coordinates) {
    constructor(id: Long) : this(null) {
        this.id = id
    }

    override fun getImage(): BufferedImage = loadAndSetImage("$powerUpsFolder/end_game.gif")

    override val duration: Int
        get() = 0

    override fun canPickUp(entity: BomberEntity): Boolean {
        return Bomberman.getMatch().enemiesAlive <= 0
    }

    override fun doApply(entity: BomberEntity) {
        super.doApply(entity)

        val match = Bomberman.getMatch()
        val currentLevel = match.currentLevel ?: return

        currentLevel.endLevel()
        DataInputOutput.getInstance().increaseLives()

        try {
            val nextLevelClass = if (currentLevel.info.isLastLevelOfWorld)
                WorldSelectorLevel::class.java
            else currentLevel.info.nextLevel

            Bomberman.startLevel(nextLevelClass!!.getDeclaredConstructor().newInstance(), match.onlineGameHandler)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun cancel(entity: BomberEntity) {}

    override val type: EntityTypes
        get() = EntityTypes.EndLevelPortal
}