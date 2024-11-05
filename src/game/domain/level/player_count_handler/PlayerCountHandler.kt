package game.domain.level.player_count_handler

import game.JBomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.ai.AiBomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.properties.RuntimeProperties
import game.utils.dev.Log
import kotlin.math.max

interface IPlayerCountHandler {
    fun onStart()
    fun onPlayerCountChanged(players: Int)
}

// Multiplayer will have a custom player count handler
class DefaultPlayerCountHandler : IPlayerCountHandler {
    override fun onStart() {
        if (!JBomb.match.isServer)
            return

        Log.i("DefaultPlayerCountHandler: onStart")

        val levelBotCount = JBomb.match.currentLevel.info.startBotCount

        Log.i("DefaultPlayerCountHandler: bots to spawn $levelBotCount")

        if (levelBotCount > 0)
            repeat(levelBotCount) {
                spawnBot()
            }
    }

    private fun spawnBot() {
        Log.i("Spawning bot")

        AiBomberEntity(coordinates = Coordinates()).also {
            JBomb.match.currentLevel.gameHandler.dynamicSpawn(it)
        }
    }

    override fun onPlayerCountChanged(players: Int) {}
}