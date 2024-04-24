package game.domain.level.eventhandler.imp

import game.Bomberman
import game.audio.AudioManager
import game.audio.SoundModel
import game.data.data.DataInputOutput
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

open class DefaultLevelEventHandler : LevelEventHandler {
    override fun onDefeatGameEvent() {
        DataInputOutput.getInstance().increaseLost()
    }

    override fun onEnemyDespawned() {}

    override fun onKilledEnemy() {
        DataInputOutput.getInstance().increaseKills()
    }

    override fun onRoundPassedGameEvent() {
        DataInputOutput.getInstance().increaseRounds()
    }

    override fun onScoreGameEvent(arg: Int) {
        DataInputOutput.getInstance().increaseScore(arg)
        Bomberman.match.inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onPurchaseItem(price: Int) {
        AudioManager.getInstance().play(SoundModel.BONUS_ALERT)
        DataInputOutput.getInstance().decreaseScore(price)
        Bomberman.match.inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onUpdateCurrentAvailableBombsEvent(arg: Int) {
        Bomberman.match.player?.state?.currentBombs = arg
    }

    override fun onUpdateMaxBombsGameEvent(arg: Int) {
        DataInputOutput.getInstance().obtainedBombs = arg
        Bomberman.match.player?.state?.maxBombs = arg
        UpdateCurrentAvailableItemsEvent().invoke(arg)
    }

    override fun onUpdateBombsLengthEvent(entity: BomberEntity, arg: Int) {
        entity.state.currExplosionLength = arg
        DataInputOutput.getInstance().explosionLength = arg
    }

    override fun onDeathGameEvent() {
        DataInputOutput.getInstance().increaseDeaths()
        DataInputOutput.getInstance().decreaseLives()
        DataInputOutput.getInstance().decreaseScore(1000)
    }

    override fun onAllEnemiesEliminated() {}
}