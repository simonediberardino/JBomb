package game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic

import game.JBomb
import game.domain.events.game.DeathGameEvent
import game.domain.events.game.HealthUpdatedEvent
import game.domain.events.game.InitBombsVariablesGameEvent
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.input.Command
import game.localization.Localization
import game.network.events.forward.UpdateInfoEventForwarder
import game.presentation.ui.viewelements.misc.ToastHandler
import game.utils.time.now

class PlayerLogic(override val entity: Player) : BomberEntityLogic(entity = entity) {
    override fun onSpawn() {
        if (JBomb.match.player == null) {
            JBomb.match.player = entity
        }

        super.onSpawn()

        ToastHandler.getInstance().cancel();
        InitBombsVariablesGameEvent().invoke()

        JBomb.match.gameTickerObservable?.register(entity)
        JBomb.match.controllerManager?.register(entity)
        JBomb.JBombFrame.matchPanel.refreshPowerUps(entity.state.activePowerUps)
    }

    override fun onDespawn() {
        super.onDespawn()

        JBomb.JBombFrame.matchPanel.refreshPowerUps(entity.state.activePowerUps)
    }

    override fun onEliminated() {
        super.onEliminated()
        DeathGameEvent().invoke(null)
    }

    override fun doAttack() {
        JBomb.match.useItem(entity)
        JBomb.match.updateInventoryWeaponController()
    }

    override fun onRemoved() {
        super.onRemoved()
        JBomb.match.controllerManager?.unregister(entity)
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
                JBomb.match.toggleGameState()
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
        UpdateInfoEventForwarder().invoke(entity.toEntityNetwork())
    }

    override fun onPowerupApply(powerUp: PowerUp) {
        val baseMessage = Localization.get(Localization.POWERUP_FOUND)
        powerUp.tag?.let {
            val message = baseMessage.replace("%powerup%", it).uppercase()
            ToastHandler.getInstance().cancel();
            ToastHandler.getInstance().show(message)
        }
    }
}