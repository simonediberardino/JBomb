package game.domain.world.domain.entity.pickups.portals

import game.Bomberman
import game.data.data.DataInputOutput
import game.domain.world.types.EntityTypes
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.level.levels.lobby.WorldSelectorLevel
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.EntityProperties
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.pickups.portals.base.Portal
import game.domain.world.domain.entity.pickups.portals.base.logic.PortalLogic
import game.utils.file_system.Paths.powerUpsFolder
import java.awt.image.BufferedImage

class EndLevelPortal : Portal {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage {
            return loadAndSetImage(entity = entity, imagePath = "$powerUpsFolder/end_game.gif")
        }
    }

    override val properties: EntityProperties = EntityProperties(type = EntityTypes.EndLevelPortal)

    override val logic: PortalLogic = object : PortalLogic(entity = this) {
        override fun canPickUp(bomberEntity: BomberEntity): Boolean {
            return Bomberman.getMatch().enemiesAlive <= 0
        }

        override fun doApply(player: BomberEntity) {
            super.doApply(player)

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
    }
}