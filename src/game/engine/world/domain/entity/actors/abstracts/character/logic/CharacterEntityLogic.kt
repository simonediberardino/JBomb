package game.engine.world.domain.entity.actors.abstracts.character.logic

import game.Bomberman
import game.engine.hardwareinput.Command
import game.engine.level.behavior.LocationChangedBehavior
import game.engine.sound.AudioManager
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.EntityInteractable
import game.engine.world.domain.entity.actors.impl.bomb.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.models.State
import game.engine.world.domain.entity.actors.abstracts.moving_entity.logic.MovingEntityLogic
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.utils.Utility
import java.awt.event.ActionEvent
import java.util.*

abstract class CharacterEntityLogic(
        override val entity: Character
) : MovingEntityLogic(entity), ICharacterEntityLogic {
    override fun onSpawn() {
        super.onSpawn()
        LocationChangedBehavior(entity.toDto()).invoke()
        setAliveState(true)
        entity.state.hp = entity.state.maxHp
    }

    override fun onDespawn() {
        super.onDespawn()
        setAliveState(false)
    }

    override fun isAlive() : Boolean {
        return entity.state.state?.get() == State.SPAWNED
    }

    override fun setAliveState(alive: Boolean) {
        entity.state.state!!.set(if (alive) State.SPAWNED else State.DIED)
    }

    override fun updateMovementDirection(direction: Direction) {
        directionUpdateLogic(direction)
        val previousDirection = entity.state.previousDirection
        entity.graphicsBehavior.directionUpdateGraphics(previousDirection, direction)
    }

    private fun directionUpdateLogic(direction: Direction) {
        // Update previousDirection and currDirection to reflect the new direction.
        entity.state.previousDirection = entity.state.currDirection
        entity.state.currDirection = direction
    }

    override fun onStep() {
        val stepSound = entity.properties.stepSound
        if (stepSound != null)
            AudioManager.getInstance().play(stepSound, false)
    }

    /**
     * Attempts to move the entity in the specified direction and updates the last direction if the move was successful.
     *
     * @param direction The direction to move the entity in.
     * @return true if the move was successful, false otherwise.
     */
    override fun move(direction: Direction): Boolean {
        // If the entity is not alive, return true.
        if (entity.state.state?.get() == State.DIED)
            return true

        // Try to move or interact with the entity in the specified direction.
        if (moveOrInteract(direction)) {
            // If the move or interaction was successful, update the last direction and return true.
            updateMovementDirection(direction)
            return true
        }

        // Otherwise, return false.
        return false
    }

    /**
     * Removes the specified amount of damage from the entity's health points.
     * If the health points reach 0 or below, the entity is despawned.
     * Otherwise, a damage animation is started.
     *
     * @param damage The amount of damage to remove from the entity's health points.
     */
    override fun onAttackReceived(damage: Int) {
        synchronized((entity.state.lastDamageTime as Any?)!!) {
            if (Utility.timePassed(entity.state.lastDamageTime) < EntityInteractable.INTERACTION_DELAY_MS)
                return

            entity.state.lastDamageTime = System.currentTimeMillis()

            // Reduce the health points by the specified amount
            entity.state.hp -= damage
            damageAnimation()

            // If the health points reach 0 or below, despawn the entity
            if (entity.state.hp <= 0) {
                entity.state.hp = 0
                eliminated()
            } else {
                onHit(damage)
            }
        }
    }

    override fun eliminated() {
        super.eliminated() // TODO Changed
        if (entity.state.state?.get() == State.DIED) {
            return
        }
        onEliminated()
    }
    /**
     * Starts a damage animation that makes the entity invisible and visible
     * repeatedly for a certain number of iterations with a delay between each iteration.
     * The animation lasts for a duration of `EntityInteractable.INTERACTION_DELAY_MS` milliseconds.
     */
    override fun damageAnimation() {
        // Duration of each iteration of the animation
        val durationMs = 100
        // Calculate the number of iterations required to reach the total duration
        val iterations = (EntityInteractable.INTERACTION_DELAY_MS / durationMs).toInt()

        // Create a Timer object to schedule the animation iterations
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            // Counter to keep track of the number of iterations
            var count = 0
            override fun run() {
                // If the number of iterations has been reached, cancel the timer and return
                if ((count >= iterations) || (entity.state.state?.get() != State.SPAWNED)) {
                    timer.cancel()
                    return
                }
                try {
                    // Make the entity invisible and wait for the specified duration
                    entity.state.isInvisible = (true)
                    Thread.sleep(durationMs.toLong())
                    // Make the entity visible again and wait for the specified duration
                    entity.state.isInvisible = (false)
                    Thread.sleep(durationMs.toLong())
                } catch (ignored: InterruptedException) {
                }

                // Increment the counter to keep track of the number of iterations
                count++
            }
        }, 0, (durationMs * 2).toLong()) // Schedule the timer to repeat with a fixed delay of durationMs * 2 between iterations
    }

    override fun onEliminated() {
        entity.state.canMove = false
        AudioManager.getInstance().play(entity.properties.deathSound)

        // Create a Timer object to schedule the animation iterations
        val timer = javax.swing.Timer(EntityInteractable.INTERACTION_DELAY_MS.toInt()) { e: ActionEvent? ->
            onEndedDeathAnimation()
            entity.logic.despawn()
            entity.logic.notifyDespawn()
        }

        timer.isRepeats = false
        timer.start()
    }

    override fun onEndedDeathAnimation() {}

    override fun onHit(damage: Int) {}

    override fun onExplosion(explosion: AbstractExplosion) {
        explosion.logic.attack(entity)
    }

    override fun handleCommand(command: Command) {
        if (!Bomberman.getMatch().gameState) {
            return
        }

        if (entity.state.canMove) {
            when (command) {
                Command.MOVE_UP, Command.MOVE_DOWN -> handleMoveCommand(command, Direction.LEFT, Direction.RIGHT)
                Command.MOVE_LEFT, Command.MOVE_RIGHT -> handleMoveCommand(command, Direction.UP, Direction.DOWN)
                else -> {}
            }
        }

        when (command) {
            Command.ATTACK -> doAttack()
            else -> {}
        }
    }

    override fun doAttack() {}

    override fun handleMoveCommand(command: Command, oppositeDirection1: Direction, oppositeDirection2: Direction) {
        val moveSuccessful = command.commandToDirection()?.let { move(it) } ?: false
        if (moveSuccessful) {
            return
        }
        val oppositeBlocksCoordinates = Coordinates.getNewCoordinatesOnDirection(
                /* position = */ entity.info.position,
                /* d = */ command.commandToDirection(),
                /* steps = */ PitchPanel.PIXEL_UNIT,
                /* offset = */ Character.size,
                /* size = */ Character.size
        )
        val entitiesOpposite1 = Coordinates.getEntitiesOnBlock(oppositeBlocksCoordinates[0])
        val entitiesOpposite2 = Coordinates.getEntitiesOnBlock(oppositeBlocksCoordinates[1])
        overpassBlock(entitiesOpposite1, entitiesOpposite2, oppositeDirection1, oppositeDirection2)
    }

    override fun overpassBlock(entitiesOpposite1: List<Entity>, entitiesOpposite2: List<Entity>, direction1: Direction, direction2: Direction) {
        val oppositeCommand1 = direction2.toCommand()
        val oppositeCommand2 = direction1.toCommand()
        val controllerManager = Bomberman.getMatch().controllerManager

        val doubleClick1 = controllerManager!!.isCommandPressed(oppositeCommand1)
        val doubleClick2 = controllerManager.isCommandPressed(oppositeCommand2)

        if (doubleClick2 || doubleClick1)
            return

        // If the first direction has no obstacles and the second does, and the second direction is not double-clicked, move in the second direction.
        if (entitiesOpposite1.isNotEmpty()
                && (entitiesOpposite2.isEmpty()
                        || entitiesOpposite2.stream().allMatch(this::canInteractWith))) {
            move(direction2)
        } else if (entitiesOpposite2.isNotEmpty() && (entitiesOpposite1.isEmpty() || entitiesOpposite1.stream().allMatch(this::canInteractWith))) {
            move(direction1)
        }
    }

    override fun executeCommandQueue() {
        for (c in entity.state.commandQueue) {
            handleCommand(c)
        }
        entity.state.commandQueue.clear()
    }

    override fun onMove(coordinates: Coordinates) {
        LocationChangedBehavior(entity.toDto()).invoke()
    }
}