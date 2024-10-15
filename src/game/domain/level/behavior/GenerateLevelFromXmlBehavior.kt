package game.domain.level.behavior

import game.domain.level.levels.Level
import game.domain.level.levels.level_editor.LevelGenerationData
import game.domain.world.domain.entity.actors.impl.EntityIds
import java.util.*

class GenerateLevelFromXmlBehavior(private val currLevel: Level?, private val levelData: LevelGenerationData): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            val gameBehavior: GameBehavior = object : GameBehavior() {
                override fun hostBehavior(): () -> Unit = {
                    levelData.data.forEach { (entityName, coordinates) ->
                        val entityClass = EntityIds[entityName] ?: return@forEach

                        coordinates.forEach { coordinate ->
                            val entity = entityClass.getConstructor(Long::class.java).newInstance(UUID.randomUUID().mostSignificantBits)
                            entity.logic.spawn(coordinate)
                        }
                    }

                    currLevel?.info?.mapDimension = levelData.mapDimension
                }

                override fun clientBehavior(): () -> Unit {
                    return {}
                }
            }

            gameBehavior.invoke()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}