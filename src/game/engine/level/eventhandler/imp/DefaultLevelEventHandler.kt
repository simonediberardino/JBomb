package game.engine.level.eventhandler.imp

import game.Bomberman
import game.storage.data.DataInputOutput
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.events.game.AllEnemiesEliminatedGameEvent
import game.engine.events.game.UpdateCurrentAvailableItemsEvent
import game.engine.level.eventhandler.model.LevelEventHandler
import game.engine.sound.AudioManager
import game.engine.sound.SoundModel

open class DefaultLevelEventHandler : LevelEventHandler {
    override fun onDefeatGameEvent() {
        DataInputOutput.getInstance().increaseLost()
    }

    override fun onEnemyDespawned() {
        if (Bomberman.getMatch().enemiesAlive == 0) {
            AllEnemiesEliminatedGameEvent().invoke(null)
        }
    }

    override fun onKilledEnemy() {
        DataInputOutput.getInstance().increaseKills()
    }

    override fun onRoundPassedGameEvent() {
        DataInputOutput.getInstance().increaseRounds()
    }

    override fun onScoreGameEvent(arg: Int) {
        DataInputOutput.getInstance().increaseScore(arg)
        Bomberman.getMatch().inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onPurchaseItem(price: Int) {
        AudioManager.getInstance().play(SoundModel.BONUS_ALERT)
        DataInputOutput.getInstance().decreaseScore(price)
        Bomberman.getMatch().inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onUpdateCurrentAvailableBombsEvent(arg: Int) {
        Bomberman.getMatch().player?.currentBombs = arg
    }

    override fun onUpdateMaxBombsGameEvent(arg: Int) {
        DataInputOutput.getInstance().increaseObtainedBombs()
        UpdateCurrentAvailableItemsEvent().invoke(arg)
    }

    override fun onUpdateBombsLengthEvent(entity: BomberEntity, arg: Int) {
        entity.currExplosionLength = arg
        DataInputOutput.getInstance().explosionLength = arg
    }

    override fun onDeathGameEvent() {
        DataInputOutput.getInstance().increaseDeaths()
        DataInputOutput.getInstance().decreaseLives()
        DataInputOutput.getInstance().decreaseScore(1000)
    }

    override fun onAllEnemiesEliminated() {}
}