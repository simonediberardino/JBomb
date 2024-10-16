package game.domain.level.levels

import game.JBomb
import game.data.data.DataInputOutput
import game.domain.events.game.RoundPassedGameEvent
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.level.behavior.GameBehavior
import game.domain.level.behavior.RespawnDeadPlayersBehavior
import game.domain.level.eventhandler.imp.DefaultLevelEventHandler
import game.domain.level.eventhandler.model.LevelEventHandler
import game.domain.level.gamehandler.imp.DefaultStoryLevelHandler
import game.domain.level.gamehandler.model.GameHandler
import game.domain.level.info.model.DefaultArenaLevelInfo
import game.localization.Localization
import game.utils.dev.Log
import game.utils.ui.ToastUtils
import java.awt.event.ActionEvent
import java.util.concurrent.atomic.AtomicReference
import javax.swing.JPanel
import javax.swing.Timer

abstract class ArenaLevel : Level() {
    override val gameHandler: GameHandler
        get() = object : DefaultStoryLevelHandler(this) {
            override fun spawnMysteryBox() {
                Log.e("Destroyable blocks current round ${currentRound.get()}")

                if (currentRound.get() != 0) {
                    return
                }

                super.spawnMysteryBox()
            }

            override fun generateDestroyableBlock() {
                Log.e("Destroyable blocks current round ${currentRound.get()}")
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
                if (isSpecialRound) {
                    val minSpecialEnemiesCount = 4
                    super.spawnEnemies((info as DefaultArenaLevelInfo).specialRoundEnemies, minSpecialEnemiesCount + currentRound.get() / 5)
                } else {
                    val enemiesCount = if (shouldSpawnBoss()) {
                        level.info.startEnemiesCount / 2
                    } else level.info.startEnemiesCount

                    super.spawnEnemies(level.info.availableEnemies, enemiesCount)
                }
            }
        }

    override val eventHandler: LevelEventHandler
        get() = object : DefaultLevelEventHandler() {
            override fun initBombsVariables() {
                // force initial bomb states to 1, saves do not count on arena
                val player = JBomb.match.player ?: return
                player.state.bombsLengthSaved = 1
                player.state.maxBombsSaved = 1

                resetBombsVariables()
            }

            override fun onUpdateBombsLengthEvent(arg: Int, save: Boolean) {
                JBomb.match.player?.state?.currExplosionLength = arg
            }

            override fun onUpdateMaxBombsGameEvent(arg: Int, save: Boolean) {
                JBomb.match.player?.state?.maxBombs = arg
                JBomb.match.player?.state?.maxBombsSaved = arg

                UpdateCurrentAvailableItemsEvent().invoke(arg, false)
            }

            override fun onDefeatGameEvent() {
                super.onDefeatGameEvent()
                currentRound.set(0)
            }

            override fun onRoundPassedGameEvent() {
                if (currentRound.get() > 1) {
                    super.onRoundPassedGameEvent()
                }
                ToastUtils.show(Localization.get(Localization.STARTING_ROUND).replace("%round%", currentRound.get().toString()))
                JBomb.match.inventoryElementControllerRounds?.setNumItems(currentRound.get())
            }

            override fun onDeathGameEvent() {
                DataInputOutput.getInstance().increaseDeaths()
                DataInputOutput.getInstance().decreaseScore(1000)
            }

            override fun onAllEnemiesEliminated() {
                val gameBehavior: GameBehavior = object : GameBehavior() {
                    override fun hostBehavior(): () -> Unit = {
                        if (!gameHandler.canGameBeEnded()) {
                            RespawnDeadPlayersBehavior().invoke()
                        }
                    }

                    override fun clientBehavior(): () -> Unit {
                        return {}
                    }
                }

                gameBehavior.invoke()

                val t = Timer(ARENA_ROUND_LOADING_TIMER) { _: ActionEvent? ->
                    gameHandler.startLevel()
                }

                t.isRepeats = false
                t.start()
            }
        }

    internal val currentRound = AtomicReference(0)
    protected val isSpecialRound: Boolean
        get() = currentRound.get() % 5 == 0 && currentRound.get() > 1 && !shouldSpawnBoss()

    override fun start() {
        super.start()
        if (currentRound.get() == 1) {
            firstStart()
        }
    }

    override fun onStartLevel() {
        currentRound.set(currentRound.get() + 1)
        CURR_ROUND = currentRound.get()
        RoundPassedGameEvent().invoke(null)
    }

    override fun endLevel() {
        currentRound.set(0)
    }

    private fun firstStart() {

    }

    protected fun shouldSpawnBoss(): Boolean {
        return currentRound.get() % 10 == 0 && currentRound.get() > 1
    }

    override fun toString(): String {
        val formattedString = Localization.get(Localization.ARENA_NAME)
        return formattedString
                .replace("%world_id%", info.worldId.toString())
    }

    companion object {
        const val MIN_ENEMIES_COUNT = 3
        const val MAX_ENEMIES_COUNT = 10
        private const val ARENA_ROUND_LOADING_TIMER = 5000
        var CURR_ROUND = 0
    }
}