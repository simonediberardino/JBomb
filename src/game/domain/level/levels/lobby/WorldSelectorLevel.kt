package game.domain.level.levels.lobby

import game.data.data.DataInputOutput
import game.localization.Localization
import game.domain.world.domain.entity.pickups.portals.World1Portal
import game.domain.world.domain.entity.pickups.portals.World2Portal

class WorldSelectorLevel : IslandLevel() {
    override fun generateLevel() {
        generatePortals()
    }

    private fun generatePortals() {
        val lastWorldId = 1.coerceAtLeast(DataInputOutput.getInstance().lastWorldId)

        val worldPortals = WORLDS_ID_TO_PORTAL
                .filterKeys { it <= lastWorldId }
                .values
                .mapNotNull {
                    try {
                        it.getDeclaredConstructor().newInstance()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }
                }

        worldPortals.forEach { it.logic.spawn(forceSpawn = true, forceCentering = false) }
    }

    override fun toString(): String {
        return Localization.get(Localization.ISLAND)
    }

    companion object {
        val WORLDS_ID_TO_PORTAL = hashMapOf(
                1 to World1Portal::class.java,
                2 to World2Portal::class.java
        )
    }
}