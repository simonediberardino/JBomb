package game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic

import game.Bomberman
import game.domain.events.game.DeathGameEvent
import game.domain.events.game.HealthUpdatedEvent
import game.domain.level.behavior.PlayerDeathBehavior
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.input.Command
import game.utils.dev.Log
import game.utils.time.now

class PlayerLogic(override val entity: Player) : BomberEntityLogic(entity = entity) {
    override fun onSpawn() {
        super.onSpawn()
        updateBombs()
        Log.e("Spawning $this")
        Bomberman.match.gameTickerObservable?.register(entity)
        Bomberman.match.controllerManager?.register(entity)
        Bomberman.bombermanFrame.matchPanel.refreshPowerUps(entity.state.activePowerUps)
    }

    override fun onDespawn() {
        super.onDespawn()

        if (entity.state.eliminated) {
            PlayerDeathBehavior().invoke()
        }
    }

    override fun onEliminated() {
        super.onEliminated()
        DeathGameEvent().invoke(null)
    }

    override fun doAttack() {
        Bomberman.match.useItem(entity)
        Bomberman.match.updateInventoryWeaponController()
    }

    override fun onRemoved() {
        super.onRemoved()
        Bomberman.match.controllerManager?.unregister(entity)
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