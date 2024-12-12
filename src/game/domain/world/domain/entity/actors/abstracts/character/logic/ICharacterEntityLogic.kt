package game.domain.world.domain.entity.actors.abstracts.character.logic

import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.moving_entity.logic.IMovingEntityLogic
import game.domain.world.domain.entity.geo.Direction
import game.input.game.Command

interface ICharacterEntityLogic : IMovingEntityLogic {
    /**
     * Checks if the entity is alive.
     * @return true if the entity is alive, false otherwise.
     */
    fun isAlive() : Boolean

    /**
     * Sets the alive state of the entity.
     * @param alive true to set the entity as alive, false otherwise.
     */
    fun setAliveState(alive: Boolean)

    /**
     * Updates the entity's movement direction.
     * @param direction the new direction to set for movement.
     */
    fun updateMovementDirection(direction: Direction)

    /**
     * Invoked on every step of the entity's movement cycle.
     */
    fun onStep()

    /**
     * Moves the entity in the specified direction.
     * @param direction the direction to move.
     * @return true if the movement is successful, false otherwise.
     */
    fun move(direction: Direction) : Boolean

    /**
     * Handles logic for when the entity is eliminated.
     */
    fun onEliminated()

    /**
     * Applies damage to the entity.
     * @param damage the amount of damage to apply.
     */
    fun onHit(damage: Int)

    /**
     * Processes a specific command issued to the entity.
     * @param command the command to handle.
     */
    fun handleCommand(command: Command)

    /**
     * Executes the entity's attack action.
     */
    fun doAttack()

    /**
     * Handles a move command, considering possible obstacles or constraints.
     * @param command the move command to handle.
     * @param oppositeDirection1 the first opposite direction to consider.
     * @param oppositeDirection2 the second opposite direction to consider.
     * @return true if the move is successful, false otherwise.
     */
    fun handleMoveCommand(command: Command, oppositeDirection1: Direction, oppositeDirection2: Direction): Boolean

    /**
     * Allows the entity to bypass a blocking obstacle by interacting with adjacent entities.
     * @param entitiesOpposite1 entities in the first opposite direction.
     * @param entitiesOpposite2 entities in the second opposite direction.
     * @param direction1 the primary direction to consider.
     * @param direction2 the secondary direction to consider.
     */
    fun overpassBlock(entitiesOpposite1: List<Entity>, entitiesOpposite2: List<Entity>, direction1: Direction, direction2: Direction)

    /**
     * Executes all commands in the entity's command queue.
     */
    fun executeCommandQueue()

    /**
     * Adds a command to the entity's command queue.
     * @param command the command to add.
     */
    fun addCommand(command: Command)

    /**
     * Removes a command from the entity's command queue.
     * @param command the command to remove.
     */
    fun removeCommand(command: Command)

    /**
     * Triggered when the entity's death animation ends.
     */
    fun onEndedDeathAnimation()

    /**
     * Moves the entity in the specified direction or interacts if movement is blocked.
     * @param direction the direction to move or interact.
     * @return true if successful, false otherwise.
     */
    fun moveOrInteract(direction: Direction): Boolean

    /**
     * Executes an interaction command.
     */
    fun interactionCommand()

    /**
     * Restores the entity's health to its maximum value.
     */
    fun restoreHealth()

    /**
     * Updates the entity's current health value.
     * @param health the new health value to set.
     */
    fun updateHealth(health: Int)

    /**
     * Called when the entity's health value is updated.
     * @param health the updated health value.
     */
    fun onUpdateHealth(health: Int)

    /**
     * Restores the entity's ability to move if it was previously restricted.
     */
    fun restoreCanMove()
}
