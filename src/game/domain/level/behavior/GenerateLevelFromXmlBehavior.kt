package game.domain.level.behavior

import game.domain.level.levels.Level
import game.domain.level.levels.level_editor.LevelGenerationData
import game.repository.RepositoryEntities
import java.util.*

class GenerateLevelFromXmlBehavior(private val currLevel: Level?, private val levelData: LevelGenerationData): GameBehavior() {
    override fun hostBehavior(): () -> Unit {
        return {
            val gameBehavior: GameBehavior = object : GameBehavior() {
                override fun hostBehavior(): () -> Unit = {
                    levelData.data.forEach { (entityName, coordinates) ->
                        val entityClass = RepositoryEntities.entityIds[entityName] ?: return@forEach

                        coordinates.forEach { coordinate ->
                            val entity = entityClass.getConstructor(Long::class.java).newInstance(UUID.randomUUID().mostSignificantBits)
                            entity.logic.spawn(coordinate)
                        }
                    }
                }

                override fun clientBehavior(): () -> Unit {
                    return {
                    }
                }
            }

            gameBehavior.invoke()
        }
    }

    override fun clientBehavior(): () -> Unit {
        return {}
    }
}