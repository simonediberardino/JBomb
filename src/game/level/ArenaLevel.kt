package game.level

import game.Bomberman
import game.data.DataInputOutput
import game.entity.models.BomberEntity
import game.entity.models.Enemy
import game.entity.models.Entity
import game.events.game.RoundPassedGameEvent
import game.events.game.UpdateCurrentAvailableBombsEvent
import game.events.game.UpdateCurrentBombsLengthEvent
import game.events.game.UpdateMaxBombsEvent
import game.localization.Localization
import game.powerups.LivesPowerUp
import game.powerups.PowerUp
import game.ui.viewelements.misc.ToastHandler
import java.awt.event.ActionEvent
import java.util.concurrent.atomic.AtomicReference
import javax.swing.JPanel
import javax.swing.Timer

abstract class ArenaLevel : Level() {
    private val currentRound = AtomicReference(0)
    abstract val specialRoundEnemies: Array<Class<out Enemy>>
    override fun start(jPanel: JPanel) {
        super.start(jPanel)
        if (currentRound.get() == 1) {
            firstStart()
        }
    }

    override fun startLevel() {
        super.startLevel()
        currentRound.set(currentRound.get() + 1)
        CURR_ROUND = currentRound.get()
        RoundPassedGameEvent().invoke(null)
    }

    override val isArenaLevel: Boolean
        get() = true

    override fun generateDestroyableBlock() {
        if (currentRound.get() != 0) {
            return
        }
        spawnMisteryBox()
        super.generateDestroyableBlock()
    }

    override fun spawnBoss() {
        if (shouldSpawnBoss()) super.spawnBoss()
    }

    override val diedMessage: String
        get() = Localization.get(Localization.ARENA_DIED).replace("%rounds%", Integer.toString(CURR_ROUND))
    override val bossMaxHealth: Int
        get() = super.bossMaxHealth / 4

    override val startEnemiesCount: Int
        get() {
            return MIN_ENEMIES_COUNT + currentRound.get()
        }

    override fun onAllEnemiesEliminated() {
        val t = Timer(ARENA_ROUND_LOADING_TIMER) { e: ActionEvent? ->
            val player: Entity? = Bomberman.getMatch().player
            if (player != null && player.isSpawned) startLevel()
        }
        t.isRepeats = false
        t.start()
    }

    override fun onRoundPassedGameEvent() {
        if (currentRound.get() > 1) {
            super.onRoundPassedGameEvent()
        }
        ToastHandler.getInstance().show(Localization.get(Localization.STARTING_ROUND).replace("%round%", currentRound.get().toString()))
        Bomberman.getMatch().inventoryElementControllerRounds.setNumItems(currentRound.get())
    }

    override val restrictedPerks: Array<Class<out PowerUp?>>
        get() = arrayOf(
                LivesPowerUp::class.java
        )

    override fun spawnEnemies() {
        if (isSpecialRound) spawnEnemies(specialRoundEnemies) else super.spawnEnemies()
    }

    override fun onDeathGameEvent() {
        currentRound.set(0)
        DataInputOutput.getInstance().increaseDeaths()
        DataInputOutput.getInstance().decreaseScore(1000)
    }

    override fun onUpdateMaxBombsGameEvent(arg: Int) {
        UpdateCurrentAvailableBombsEvent().invoke(arg)
    }

    override fun onUpdateBombsLengthEvent(entity: BomberEntity, arg: Int) {
        entity.currExplosionLength = arg
    }

    override fun endLevel() {}
    override fun toString(): String {
        return java.lang.String.format("Arena World %d", worldId)
    }

    protected fun firstStart() {
        UpdateCurrentBombsLengthEvent().invoke(1)
        UpdateMaxBombsEvent().invoke(1)
    }

    protected val isSpecialRound: Boolean
        protected get() = currentRound.get() % 5 == 0 && currentRound.get() > 1 && !shouldSpawnBoss()

    protected fun shouldSpawnBoss(): Boolean {
        return currentRound.get() % 10 == 0 && currentRound.get() > 1
    }

    companion object {
        private const val MIN_ENEMIES_COUNT = 3
        private const val MAX_ENEMIES_COUNT = 20
        private const val ARENA_ROUND_LOADING_TIMER = 5000
        private var CURR_ROUND = 0
    }
}