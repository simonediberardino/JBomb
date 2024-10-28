package game.domain.level.eventhandler.imp

import game.JBomb
import game.audio.AudioManager
import game.audio.SoundModel
import game.data.data.DataInputOutput
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.events.game.UpdateCurrentBombsLengthEvent
import game.domain.events.game.UpdateMaxBombsEvent
import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.world.domain.entity.actors.abstracts.base.Entity

open class DefaultLevelEventHandler : LevelEventHandler {
    override fun onDefeatGameEventLocal() {
        DataInputOutput.getInstance().increaseLost()
    }

    override fun onEnemyDespawnedLocal() {}

    override fun onKilledEnemyLocal() {
        DataInputOutput.getInstance().increaseKills()
    }

    override fun onRoundPassedGameEventLocal() {
        DataInputOutput.getInstance().increaseRounds()
    }

    override fun onScoreGameEventLocal(arg: Int) {
        DataInputOutput.getInstance().increaseScore(arg)
        JBomb.match.inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onKillsGameEventLocal(arg: Int) {
        if (JBomb.match.currentLevel.info.killCountEnabled) {
            JBomb.match.inventoryElementControllerKills?.setNumItems(arg)
        }
    }

    override fun onPurchaseItemLocal(price: Int) {
        AudioManager.instance.play(SoundModel.BONUS_ALERT)
        DataInputOutput.getInstance().decreaseScore(price)
        JBomb.match.inventoryElementControllerPoints.setNumItems(DataInputOutput.getInstance().score.toInt())
    }

    override fun onUpdateCurrentAvailableBombsEventLocal(arg: Int) {
        JBomb.match.player?.state?.currentBombs = arg
        JBomb.match.updateInventoryWeaponController()
    }

    override fun onUpdateMaxBombsGameEventLocal(arg: Int, save: Boolean) {
        if (save) {
            DataInputOutput.getInstance().obtainedBombs = arg
        }

        JBomb.match.player?.state?.maxBombs = arg
        JBomb.match.player?.state?.maxBombsSaved = arg
        UpdateCurrentAvailableItemsEvent().invoke(arg, save)
    }

    override fun onUpdateBombsLengthEventLocal(arg: Int, save: Boolean) {
        JBomb.match.player?.state?.currExplosionLength = arg
        DataInputOutput.getInstance().explosionLength = arg
    }

    override fun onDeathGameEventLocal() {
        DataInputOutput.getInstance().increaseDeaths()
        DataInputOutput.getInstance().decreaseLives()

        JBomb.match.currentLevel.let {
            if (it.info.scoreEnabled)
                DataInputOutput.getInstance().decreaseScore(1000)
        }
    }

    override fun onEliminated(entity: Entity) {

    }

    // for a default level, reads the bomb data from the storage and applies it
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

    override fun onKill(attacker: Entity, victim: Entity) {}
    override fun onTimeUpdate(time: Long) {}
    override fun onEndGame() {}
}