package game.domain.level.player_count_handler

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.placeable.bomb.Bomb
import game.domain.world.domain.entity.actors.impl.bomber_entity.ai.AiBomberEntity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.utils.dev.Log
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min

interface IPlayerCountHandler {
    fun onStart()
    fun onPlayerCountChanged()
}

// Multiplayer will have a custom player count handler
class DefaultPlayerCountHandler : IPlayerCountHandler {
    override fun onStart() {
        if (!JBomb.match.isServer)
            return
        onPlayerCountChanged()
    }

    private fun spawnBots(n: Int) {
        repeat(n) {
            spawnBot()
        }
    }

    private fun spawnBot() {
        Log.i("[PlayerCountHandler] Spawning bot")

        AiBomberEntity(coordinates = Coordinates()).also {
            JBomb.match.currentLevel.gameHandler.dynamicSpawn(it)
        }
    }

    override fun onPlayerCountChanged() {
        Log.i("[PlayerCountHandler] onPlayerCountChanged start")

        val playerGoal = JBomb.match.currentLevel.info.botsFillCount

        val deadPlayers = JBomb.match.getDeadEntities().values
            .asSequence()
            .map { it.second }
            .filterIsInstance<BomberEntity>()
            .filter { !it.state.disconnected }

        val alivePlayers = JBomb.match.players.filter { !it.state.disconnected }
        val totalPlayers = deadPlayers.toSet() + alivePlayers.toSet()

        val botsToSpawn = playerGoal - totalPlayers.size

        if (botsToSpawn < 0) {
            val bots = totalPlayers.filter { it.properties.isBot }
            val botsToKick = abs(botsToSpawn)
            disconnectBots(n = botsToKick, bots = bots)
        } else if (botsToSpawn > 0) {
            spawnBots(botsToSpawn)
        }

        Log.i("[PlayerCountHandler] onPlayerCountChanged finish")
    }

    private fun disconnectBots(n: Int, bots: Collection<BomberEntity>) {
        Log.i("[PlayerCountHandler] disconnectBots")

        val botsSorted = bots.sortedBy { it.state.kills }
        val botsToKick = botsSorted.take(n)
        botsToKick.forEach {
            JBomb.match.scope.launch {
                Log.i("[PlayerCountHandler] Kicking player")
                JBomb.match.kickPlayer(it)
            }
        }
    }
}