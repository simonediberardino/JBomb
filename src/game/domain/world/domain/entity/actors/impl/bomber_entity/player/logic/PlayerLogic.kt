package game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic

import game.Bomberman
import game.domain.events.game.DeathGameEvent
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.events.game.UpdateCurrentBombsLengthEvent
import game.input.Command
import game.presentation.ui.pages.GameOverPanel
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.data.data.DataInputOutput
import game.domain.tasks.observer.Observable2
import game.domain.tasks.observer.Observer2
import game.utils.dev.Log
import java.awt.event.ActionEvent
import javax.swing.Timer

class PlayerLogic(override val entity: Player) : BomberEntityLogic(entity = entity) {
    override fun onSpawn() {
        super.onSpawn()
        updateBombs()
        Bomberman.getMatch().gameTickerObservable?.register(entity)
        Bomberman.getBombermanFrame().matchPanel.refreshPowerUps(entity.state.activePowerUps)
    }

    override fun onEliminated() {
        super.onEliminated()
        DeathGameEvent().invoke(null)
    }

    override fun doAttack() {
        Bomberman.getMatch().useItem(entity)
        Bomberman.getMatch().updateInventoryWeaponController()
    }

    // TODO!!!
    override fun onEndedDeathAnimation() {
        val t = Timer(EntityInteractable.SHOW_DEATH_PAGE_DELAY_MS.toInt()) { _: ActionEvent? -> showDeathPage() }
        t.isRepeats = false
        t.start()
    }

    private fun showDeathPage() {
        Bomberman.destroyLevel()
        Bomberman.showActivity(GameOverPanel::class.java)
    }

    private fun updateBombs() {
        val maxBombs = DataInputOutput.getInstance().obtainedBombs
        UpdateCurrentAvailableItemsEvent().invoke(maxBombs)
        UpdateCurrentBombsLengthEvent().invoke(DataInputOutput.getInstance().explosionLength)
    }

    override fun observerUpdate(arg: Any?) {
        super.observerUpdate(arg)
        executeCommandQueue()
    }

    override fun executeCommandQueue() {
        entity.state.commandQueue.parallelStream().forEach { c ->
            handleCommand(c)
        }
    }
}