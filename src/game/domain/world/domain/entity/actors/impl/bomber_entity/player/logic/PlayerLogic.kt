package game.domain.world.domain.entity.actors.impl.bomber_entity.player.logic

import game.JBomb
import game.domain.events.game.DeathGameEvent
import game.domain.events.game.HealthUpdatedEvent
import game.domain.events.game.InitBombsVariablesGameEvent
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic.BomberEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.input.game.Command
import game.localization.Localization
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.utils.ui.ToastUtils
import game.utils.time.now

class PlayerLogic(override val entity: Player) : BomberEntityLogic(entity = entity) {
    private var lastChangeWeaponTime = 0L

    override fun onSpawn() {
        if (JBomb.match.player == null) {
            JBomb.match.player = entity
        }

        super.onSpawn()

        entity.state.commandQueue.clear()

        ToastUtils.cancel()
        InitBombsVariablesGameEvent().invoke()

        JBomb.match.controllerManager?.register(entity)
        JBomb.match.refreshPowerUps(entity.state.activePowerUps)
    }

    override fun onDespawn() {
        super.onDespawn()

        JBomb.match.refreshPowerUps(entity.state.activePowerUps)
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

    override fun handleMoveCommand(command: Command, oppositeDirection1: Direction, oppositeDirection2: Direction): Boolean {
        val moveSuccessful = super.handleMoveCommand(command, oppositeDirection1, oppositeDirection2)

        if (moveSuccessful) {
            return true
        }

        val oppositeBlocksCoordinates = Coordinates.getNewCoordinatesListOnDirection(
            /* position = */ entity.info.position,
            /* d = */ command.commandToDirection(),
            /* steps = */ PitchPanel.PIXEL_UNIT,
            /* offset = */ Character.DEFAULT.SIZE,
            /* size = */ Character.DEFAULT.SIZE
        )
        val entitiesOpposite1 = Coordinates.getEntitiesOnBlock(oppositeBlocksCoordinates[0])
        val entitiesOpposite2 = Coordinates.getEntitiesOnBlock(oppositeBlocksCoordinates[1])
        overpassBlock(entitiesOpposite1, entitiesOpposite2, oppositeDirection1, oppositeDirection2)

        return true
    }

    override fun handleCommand(command: Command) {
        when (command) {
            Command.PAUSE -> {
                JBomb.match.toggleGameState()
                removeCommand(command)
            }

            Command.INTERACT -> interactionCommand()
            Command.CHANGE_ITEM -> changeItemCommand()
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

    private fun changeItemCommand() {
        if (Utility.timePassed(lastChangeWeaponTime) > 200) {
            lastChangeWeaponTime = now()
            JBomb.match.switchWeapon(entity)
        }
    }

    override fun onPowerupApply(powerUp: PowerUp) {
        val baseMessage = Localization.get(Localization.POWERUP_FOUND)
        powerUp.tag?.let {
            val message = baseMessage.replace("%powerup%", it).toUpperCase()
            ToastUtils.cancel();
            ToastUtils.show(message)
        }
    }
}