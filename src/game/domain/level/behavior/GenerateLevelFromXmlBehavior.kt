package game.domain.level.behavior

import game.domain.level.levels.Level
import game.domain.level.levels.level_editor.LevelGenerationData
import game.domain.world.domain.entity.geo.Coordinates
import game.repository.RepositoryEntities
import java.util.*

class GenerateLevelFromXmlBehavior(private val currLevel: Level?, private val levelData: LevelGenerationData) :
    GameBehavior() {
    override fun hostBehavior() {
        val gameBehavior: GameBehavior = object : GameBehavior() {
            override fun hostBehavior() {
                levelData.data.forEach { (entityName, coordinates) ->
                    val entityClass = RepositoryEntities.entityIds[entityName] ?: return@forEach

                    coordinates.forEach { coordinate ->
                        val entity = entityClass.getConstructor(
                            Coordinates::class.java
                        ).newInstance(coordinate)

                        entity.logic.spawn()
                    }
                }
                currLevel?.info?.customSpawnpoints = levelData.spawnPoints.toMutableList()
            }

            override fun clientBehavior() {}
        }

        gameBehavior.invoke()
    }

    override fun clientBehavior() {}
}