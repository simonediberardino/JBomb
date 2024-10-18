package game.domain.level.eventhandler.model

import game.domain.world.domain.entity.actors.abstracts.base.Entity

interface LevelEventHandler {
    fun onDefeatGameEvent()
    fun onEnemyDespawned()
    fun onKilledEnemy()
    fun onRoundPassedGameEvent()
    fun onScoreGameEvent(arg: Int)
    fun onPurchaseItem(price: Int)
    fun onUpdateCurrentAvailableBombsEvent(arg: Int)
    fun onUpdateBombsLengthEvent(arg: Int, save: Boolean)
    fun onAllEnemiesEliminated()
    fun onDeathGameEvent()
    fun initBombsVariables()
    fun onUpdateMaxBombsGameEvent(arg: Int, save: Boolean)
    fun resetBombsVariables()
    fun onKill(attacker: Entity, victim: Entity)
    fun onTimeUpdate(time: Long)
}