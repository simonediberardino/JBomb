package game.domain.world.domain.entity.pickups.portals.imp.world_base

import game.Bomberman
import game.data.data.DataInputOutput
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.level.levels.Level
import game.domain.level.levels.world1.World1Level1
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.IEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.domain.world.domain.entity.actors.impl.placeable.bomb.Bomb
import game.domain.world.domain.entity.pickups.portals.base.Portal
import game.domain.world.domain.entity.pickups.portals.base.logic.PortalLogic
import game.domain.world.domain.entity.pickups.portals.imp.world_base.state.WorldPortalState
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.utils.file_system.Paths.getWorldSelectorPortalPath
import game.utils.Utility.loadImage
import java.awt.image.BufferedImage
import java.lang.reflect.InvocationTargetException
import java.util.*

abstract class WorldPortal(coordinates: Coordinates?, private val worldId: Int) : Portal(coordinates) {
    constructor(worldId: Int) : this(null, worldId)


    override val logic: PortalLogic = object : PortalLogic(entity = this) {
        override fun onSpawn() {
            super.onSpawn()
            state.defaultCoords?.let {
                info.position = it
            }
        }

        override fun doApply(player: BomberEntity) {
            super.doApply(player)
            try {
                // Get the ID of the last level and last world from the player data object
                val savedLastLevelId = DataInputOutput.getInstance().lastLevelId
                val savedLastWorldId = DataInputOutput.getInstance().lastWorldId

                // Get the class of the first level for the current world from the ID_TO_FIRST_LEVEL_MAP
                val firstLevelOfCurrWorld = Level.ID_TO_FIRST_LEVEL_MAP.getOrDefault(worldId, World1Level1::class.java)

                // Initialize the level to start

                // Check if the saved last world ID matches the current world ID
                val levelToStart: Level = if (savedLastWorldId == worldId) {
                    // Find the class of the last level using the saved last world ID and last level ID
                    val lastLevelOpt = findLastLevel(savedLastWorldId, savedLastLevelId)
                    val levelClass = if (lastLevelOpt.isPresent) lastLevelOpt.get() else firstLevelOfCurrWorld

                    // Create an instance of the level class
                    levelClass.getConstructor().newInstance()
                } else {
                    // Use the class of the first level for the current world
                    firstLevelOfCurrWorld.getConstructor().newInstance()
                }

                Bomberman.destroyLevel()
                // Start the level with the obtained level instance
                Bomberman.startLevel(levelToStart, Bomberman.match.onlineGameHandler)
            } catch (e: InstantiationException) {
                // Print the stack trace if there is an exception
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }
        }

        /**
         * Finds the class of the last level based on the saved last world ID and last level ID.
         *
         * @param savedLastWorldId the saved last world ID
         * @param savedLastLevelId the saved last level ID
         * @return an optional containing the class of the last level, if found
         */
        private fun findLastLevel(savedLastWorldId: Int, savedLastLevelId: Int): Optional<Class<out Level>> {
            return Level.findLevel(savedLastWorldId, savedLastLevelId)
        }
    }

    abstract override val state: WorldPortalState

    override val graphicsBehavior: IEntityGraphicsBehavior = object : DefaultEntityGraphicsBehavior() {
        override fun getImage(entity: Entity): BufferedImage? {
            return loadImage(getWorldSelectorPortalPath(worldId))
        }
    }

    internal object DEFAULT {
        val SIZE = PowerUp.DEFAULT.SIZE * 3
    }
}