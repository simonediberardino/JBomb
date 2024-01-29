package game.engine.world.pickups.portals

import game.Bomberman
import game.storage.data.DataInputOutput
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.world.geo.Coordinates
import game.engine.level.levels.Level
import game.engine.level.levels.world1.World1Level1
import game.utils.Paths.getWorldSelectorPortalPath
import game.utils.Utility.loadImage
import java.awt.image.BufferedImage
import java.lang.reflect.InvocationTargetException
import java.util.*

abstract class WorldPortal(coordinates: Coordinates?, private val worldId: Int) : Portal(coordinates) {
    constructor(worldId: Int) : this(null, worldId)

    init {
        info.position = defaultCoords!!
    }

    override val size: Int
        get() = super.size * 3

    override val duration: Int
        get() {
            return 0
        }

    override fun getImage(): BufferedImage {
        return loadImage(getWorldSelectorPortalPath(worldId))
    }

    override fun doApply(entity: BomberEntity) {
        super.doApply(entity)
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

            // Start the level with the obtained level instance
            Bomberman.startLevel(levelToStart,Bomberman.getMatch().onlineGameHandler)
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

    override fun cancel(entity: BomberEntity) {}
    abstract val defaultCoords: Coordinates?
}