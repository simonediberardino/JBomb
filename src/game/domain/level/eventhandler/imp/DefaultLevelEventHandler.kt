package game.domain.level.eventhandler.imp

import game.JBomb
import game.audio.AudioManager
import game.audio.SoundModel
import game.data.data.DataInputOutput
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.events.game.UpdateCurrentBombsLengthEvent
import game.domain.events.game.UpdateMaxBombsEvent
import game.domain.level.eventhandler.model.LevelEventHandler

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
        JBomb.match.inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onPurchaseItem(price: Int) {
        AudioManager.instance.play(SoundModel.BONUS_ALERT)
        DataInputOutput.getInstance().decreaseScore(price)
        JBomb.match.inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onUpdateCurrentAvailableBombsEvent(arg: Int) {
        JBomb.match.player?.state?.currentBombs = arg
        JBomb.match.updateInventoryWeaponController()
    }

    override fun onUpdateMaxBombsGameEvent(arg: Int, save: Boolean) {
        if (save) {
            DataInputOutput.getInstance().obtainedBombs = arg
        }

        JBomb.match.player?.state?.maxBombs = arg
        JBomb.match.player?.state?.maxBombsSaved = arg
        UpdateCurrentAvailableItemsEvent().invoke(arg, save)
    }

    override fun onUpdateBombsLengthEvent(arg: Int, save: Boolean) {
        JBomb.match.player?.state?.currExplosionLength = arg
        DataInputOutput.getInstance().explosionLength = arg
    }

    override fun onDeathGameEvent() {
        DataInputOutput.getInstance().increaseDeaths()
        DataInputOutput.getInstance().decreaseLives()
        DataInputOutput.getInstance().decreaseScore(1000)
    }

    override fun initBombsVariables() {
        val explosionLength = DataInputOutput.getInstance().explosionLength
        val maxBombs = DataInputOutput.getInstance().obtainedBombs

        val player = JBomb.match.player ?: return
        player.state.bombsLengthSaved = explosionLength
        player.state.maxBombsSaved = maxBombs

        resetBombsVariables()
    }

    // reset bombs to a previously saved state (e.g. after resetting weapon)
    override fun resetBombsVariables() {
        val player = JBomb.match.player ?: return

        val explosionLength = player.state.bombsLengthSaved
        UpdateCurrentBombsLengthEvent().invoke(explosionLength, false)

        val maxBombs = player.state.maxBombsSaved
        UpdateMaxBombsEvent().invoke(maxBombs, false)
    }

    override fun onAllEnemiesEliminated() {}
}