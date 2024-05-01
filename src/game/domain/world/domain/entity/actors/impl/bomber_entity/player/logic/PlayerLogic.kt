package game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic

import game.Bomberman
import game.data.data.DataInputOutput
import game.domain.events.game.DeathGameEvent
import game.domain.events.game.HealthUpdatedEvent
import game.domain.events.game.UpdateCurrentAvailableItemsEvent
import game.domain.events.game.UpdateCurrentBombsLengthEvent
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.input.Command
import game.presentation.ui.pages.game_over.GameOverPanel
import game.utils.time.now
import java.awt.event.ActionEvent
import javax.swing.Timer

class PlayerLogic(override val entity: Player) : BomberEntityLogic(entity = entity) {
    override fun onSpawn() {
        super.onSpawn()
        updateBombs()
        Bomberman.match.gameTickerObservable?.register(entity)
        Bomberman.match.controllerManager?.register(entity)
        Bomberman.bombermanFrame.matchPanel.refreshPowerUps(entity.state.activePowerUps)
    }

    override fun onEliminated() {
        super.onEliminated()
        DeathGameEvent().invoke(null)
    }

    override fun doAttack() {
        Bomberman.match.useItem(entity)
        Bomberman.match.updateInventoryWeaponController()
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

    override fun onMove(coordinates: Coordinates) {
        super.onMove(coordinates)
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {
        super.observerUpdate(arg)

        when (arg.identifier) {
            Observable2.ObserverParamIdentifier.GAME_TICK -> executeCommandQueue()
            Observable2.ObserverParamIdentifier.INPUT_COMMAND -> addCommand(command = arg.value as Command)
            Observable2.ObserverParamIdentifier.DELETE_COMMAND -> removeCommand(command = arg.value as Command)
        }

        entity.state.previousObserverUpdate = now()
    }

    override fun handleCommand(command: Command) {
        when (command) {
            Command.PAUSE -> {
                Bomberman.match.toggleGameState()
                removeCommand(command)
            }

            Command.INTERACT -> interactionCommand()
            else -> {}
        }

        super.handleCommand(command)
    }

    override fun executeCommandQueue() {
        val commandQueue = entity.state.commandQueue

        try {
            commandQueue.forEach { c ->
                handleCommand(c)
            }
        } catch (exception: ConcurrentModificationException) {
            exception.printStackTrace()
        }
    }

    override fun addCommand(command: Command) {
        super.addCommand(command)
        entity.state.commandQueue.add(command)
    }

    override fun removeCommand(command: Command) {
        super.removeCommand(command)
        entity.state.commandQueue.remove(command)
    }

    override fun onUpdateHealth(health: Int) {
        super.onUpdateHealth(health)
        HealthUpdatedEvent().invoke(null)
    }
}