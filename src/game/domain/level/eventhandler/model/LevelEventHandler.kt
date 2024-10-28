package game.domain.level.eventhandler.model

import game.domain.world.domain.entity.actors.abstracts.base.Entity

interface LevelEventHandler {
    fun onDefeatGameEventLocal()
    fun onEnemyDespawnedLocal()
    fun onKilledEnemyLocal()
    fun onRoundPassedGameEventLocal()
    fun onScoreGameEventLocal(arg: Int)
    fun onKillsGameEventLocal(arg: Int)
    fun onPurchaseItemLocal(price: Int)
    fun onUpdateCurrentAvailableBombsEventLocal(arg: Int)
    fun onUpdateBombsLengthEventLocal(arg: Int, save: Boolean)
    fun onAllEnemiesEliminated()
    fun onDeathGameEventLocal()
    fun initBombsVariables()
    fun onUpdateMaxBombsGameEventLocal(arg: Int, save: Boolean)
    fun resetBombsVariables()
    fun onKill(attacker: Entity, victim: Entity)
    fun onTimeUpdate(time: Long)
    fun onEndGame()
    fun onEliminated(entity: Entity)
}