package game.domain.level.levels

import game.Bomberman
import game.data.data.DataInputOutput
import game.domain.events.game.RoundPassedGameEvent
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.events.game.UpdateCurrentBombsLengthEvent
import game.domain.events.game.UpdateMaxBombsEvent
import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.level.gamehandler.imp.DefaultGameHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.info.model.DefaultArenaLevelInfo
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.localization.Localization
import game.presentation.ui.viewelements.misc.ToastHandler
import java.awt.event.ActionEvent
import java.util.concurrent.atomic.AtomicReference
import javax.swing.JPanel
import javax.swing.Timer

abstract class ArenaLevel : Level() {
    override val gameHandler: GameHandler
        get() = object : DefaultGameHandler(this) {
            override fun spawnMysteryBox() {
                if (currentRound.get() != 0) {
                    return
                }

               super.spawnMysteryBox()
            }

            override fun generateDestroyableBlock() {
                if (currentRound.get() != 0) {
                    return
                }

                super.generateDestroyableBlock()
            }

            override fun spawnBoss() {
                if (shouldSpawnBoss())
                    super.spawnBoss()
            }

            override fun spawnEnemies() {
                if (isSpecialRound)
                    super.spawnEnemies((info as DefaultArenaLevelInfo).specialRoundEnemies)
                else
                    super.spawnEnemies()
            }
        }

    override val eventHandler: LevelEventHandler
        get() = object : DefaultLevelEventHandler() {
            override fun onRoundPassedGameEvent() {
                if (currentRound.get() > 1) {
                    super.onRoundPassedGameEvent()
                }
                ToastHandler.getInstance().show(Localization.get(Localization.STARTING_ROUND).replace("%round%", currentRound.get().toString()))
                Bomberman.match.inventoryElementControllerRounds?.setNumItems(currentRound.get())
            }

            override fun onDeathGameEvent() {
                currentRound.set(0)
                DataInputOutput.getInstance().increaseDeaths()
                DataInputOutput.getInstance().decreaseScore(1000)
            }

            override fun onUpdateMaxBombsGameEvent(arg: Int) {
                UpdateCurrentAvailableItemsEvent().invoke(arg)
            }

            override fun onUpdateBombsLengthEvent(entity: BomberEntity, arg: Int) {
                entity.state.currExplosionLength = arg
            }

            override fun onAllEnemiesEliminated() {
                val t = Timer(ARENA_ROUND_LOADING_TIMER) { _: ActionEvent? ->
                    val player: Entity = Bomberman.match.player ?: return@Timer
                    if (player.state.isSpawned)
                        gameHandler.startLevel()
                }

                t.isRepeats = false
                t.start()
            }
        }

    internal val currentRound = AtomicReference(0)
    protected val isSpecialRound: Boolean
        get() = currentRound.get() % 5 == 0 && currentRound.get() > 1 && !shouldSpawnBoss()

    override fun start(field: JPanel) {
        super.start(field)
        if (currentRound.get() == 1) {
            firstStart()
        }
    }

    override fun onStartLevel() {
        currentRound.set(currentRound.get() + 1)
        CURR_ROUND = currentRound.get()
        RoundPassedGameEvent().invoke(null)
    }

    override fun endLevel() {}

    private fun firstStart() {
        UpdateCurrentBombsLengthEvent().invoke(1)
        UpdateMaxBombsEvent().invoke(1)
    }

    protected fun shouldSpawnBoss(): Boolean {
        return currentRound.get() % 10 == 0 && currentRound.get() > 1
    }

    override fun toString(): String {
        return "Arena World ${info.worldId}"
    }

    companion object {
        const val MIN_ENEMIES_COUNT = 3
        private const val MAX_ENEMIES_COUNT = 20
        private const val ARENA_ROUND_LOADING_TIMER = 5000
        var CURR_ROUND = 0
    }
}