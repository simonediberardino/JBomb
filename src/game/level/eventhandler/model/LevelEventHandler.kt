package game.level.eventhandler.model

import game.entity.player.BomberEntity

interface LevelEventHandler {
    fun onDefeatGameEvent()
    fun onEnemyDespawned()
    fun onKilledEnemy()
    fun onRoundPassedGameEvent()
    fun onScoreGameEvent(arg: Int)
    fun onPurchaseItem(price: Int)
    fun onUpdateCurrentAvailableBombsEvent(arg: Int)
    fun onUpdateMaxBombsGameEvent(arg: Int)
    fun onUpdateBombsLengthEvent(entity: BomberEntity, arg: Int)
    fun onAllEnemiesEliminated()
    fun onDeathGameEvent()
}