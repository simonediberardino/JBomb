package game.engine.world.domain.entity.actors.impl.bomber_entity.player.logic

import game.Bomberman
import game.engine.events.game.DeathGameEvent
import game.engine.events.game.UpdateCurrentAvailableItemsEvent
import game.engine.events.game.UpdateCurrentBombsLengthEvent
import game.engine.hardwareinput.Command
import game.engine.ui.pages.GameOverPanel
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.engine.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.storage.data.DataInputOutput
import game.utils.dev.Log
import java.awt.event.ActionEvent
import javax.swing.Timer

class PlayerLogic(override val entity: Player) : BomberEntityLogic(entity = entity) {
    override fun onSpawn() {
        super.onSpawn()
        updateBombs()
        Bomberman.getMatch().controllerManager?.register(entity)
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
        Log.i(maxBombs)
        UpdateCurrentAvailableItemsEvent().invoke(maxBombs)
        UpdateCurrentBombsLengthEvent().invoke(DataInputOutput.getInstance().explosionLength)
    }

    override fun observerUpdate(arg: Any?) {
        super.observerUpdate(arg)
        handleCommand(arg as Command)
    }
}