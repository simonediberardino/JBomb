package game.level.eventhandler.imp

import game.Bomberman
import game.data.DataInputOutput
import game.entity.models.BomberEntity
import game.events.game.AllEnemiesEliminatedGameEvent
import game.events.game.UpdateCurrentAvailableItemsEvent
import game.level.eventhandler.model.LevelEventHandler
import game.sound.AudioManager
import game.sound.SoundModel

open class DefaultLevelEventHandler: LevelEventHandler {
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