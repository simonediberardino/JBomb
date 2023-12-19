package game.level.eventhandler.model

import game.Bomberman
import game.data.DataInputOutput
import game.entity.models.BomberEntity
import game.events.game.AllEnemiesEliminatedGameEvent
import game.events.game.UpdateCurrentAvailableBombsEvent
import game.sound.AudioManager
import game.sound.SoundModel

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