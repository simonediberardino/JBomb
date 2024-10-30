package game.domain.level.levels.multiplayer

import game.JBomb
import game.domain.level.behavior.GenerateLevelFromXmlBehavior
import game.domain.level.gamehandler.imp.DefaultGameHandler
import game.domain.world.domain.entity.actors.abstracts.animal.AnimalEntity
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.enemy.Enemy
import game.domain.world.domain.entity.geo.Coordinates

class MultiplayerGameHandler(level: MultiplayerLevel) : DefaultGameHandler(level) {
    override fun generateStone() {
        GenerateLevelFromXmlBehavior(level, (level as MultiplayerLevel).levelGenerationData).invoke()
    }

    override fun chooseSpawnpointLogic(entity: Entity): Coordinates {
        val spawnpoints = level.info.customSpawnpoints
        val players = JBomb.match.players

        if (players.isEmpty())
            return spawnpoints.random()

        // Calculate the minimum distance of each spawn point to any player
        val spawnpointsWithDistance = spawnpoints.map { spawnPointCoordinate ->
            val minDistanceToPlayers = players.minOfOrNull { player ->
                player.info.position.distanceTo(spawnPointCoordinate)
            } ?: Double.MAX_VALUE
            spawnPointCoordinate to minDistanceToPlayers
        }

        // Calculate the average of these minimum distances
        val averageMinDistance = spawnpointsWithDistance.map { it.second }.average()

        // Filter spawn points that have a minimum distance above the average
        val candidates = spawnpointsWithDistance.filter { it.second > averageMinDistance }.map { it.first }

        // Randomly select one of the candidates, or fallback to any spawnpoint if none above average
        return if (candidates.isNotEmpty()) candidates.random() else spawnpoints.random()
    }

    override fun generateDestroyableBlock() {}
    override fun spawnBoss() {}
    override fun spawnEnemies() {}
    override fun spawnEnemies(availableEnemies: Array<Class<out Enemy>>, count: Int) {}
    override fun spawnMysteryBox() {}
    override fun canGameBeEnded(): Boolean = false
    override fun spawnAnimals(availableAnimals: Array<Class<out AnimalEntity>>) {}
    override fun spawnAnimals() {}
}