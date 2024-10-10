package game.input.terminal.commands

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.EntityIds
import game.domain.world.domain.entity.geo.Coordinates
import game.input.terminal.TerminalCommand
import java.util.*

// Command: spawn <EntityIds> <x> <y> <flags>
class SpawnCommand : TerminalCommand {
    override fun execute(args: List<String>) {
        // Check if the game is active, return early if not.
        if (!isGameActive()) return

        // Validate the input arguments (e.g., entity name, coordinates).
        validateArguments(args)

        // Retrieve the entity class based on the entity name passed in the arguments.
        val entityClass = getEntityClass(args[0])

        val position = getPosition(args)

        // Check for specific flags in the arguments to determine whether the entity should freeze or be force-centered.
        val hasToFreeze = args.contains("freeze=true")
        val hasToForceCenter = !args.contains("center=false")

        // Create the entity object based on the entity class retrieved earlier.
        val entityToSpawn = createEntity(entityClass)

        spawnEntity(entityToSpawn, position, hasToForceCenter)

        // If the spawned entity is a Character and the "freeze=true" flag is set, freeze the entity (i.e., disable movement).
        if (entityToSpawn is Character && hasToFreeze) {
            freezeCharacter(entityToSpawn)
        }

        // Print information about the spawned entity, including its class and position.
        printSpawnInfo(entityClass, entityToSpawn)
    }

    // Checks if the game is currently active by verifying the game state.
    // If the game is not active, we don't want to execute any commands.
    private fun isGameActive(): Boolean = JBomb.match.gameState

    // Validates that the arguments passed to the command are sufficient.
    // The command expects at least 3 arguments (entity name, x coordinate, and y coordinate).
    // If fewer arguments are provided, an IllegalArgumentException is thrown.
    private fun validateArguments(args: List<String>) {
        if (args.size < 3) {
            throw IllegalArgumentException("Wrong arguments: Expected at least 3 arguments")
        }
    }

    // Retrieves the class of the entity to spawn, based on the entity name passed in the arguments.
    // It looks up the entity in a map (EntityIds) and returns the corresponding class.
    // If the entity does not exist in the map, a RuntimeException is thrown.
    private fun getEntityClass(entity: String): Class<out Entity> =
        EntityIds[entity] ?: throw RuntimeException("Entity '$entity' does not exist")

    // Parses the x and y coordinates from the arguments and returns them as a Coordinates object.
    // If the coordinates are not valid integers, it throws an IllegalArgumentException with a message specifying the issue.
    private fun getPosition(args: List<String>): Coordinates {
        val x = args[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid X coordinate")
        val y = args[2].toIntOrNull() ?: throw IllegalArgumentException("Invalid Y coordinate")
        return Coordinates(x, y)
    }

    // Creates a new instance of the entity using reflection.
    // It generates a unique ID for the entity using UUID and calls the appropriate constructor.
    // The constructor takes a Long value (the generated entity ID).
    private fun createEntity(entityClass: Class<out Entity>): Entity {
        val entityId = UUID.randomUUID().mostSignificantBits
        return entityClass.getConstructor(Long::class.java).newInstance(entityId)
    }

    // Spawns the entity at the specified position.
    // The entity's position is set to the given Coordinates, and the spawn logic is invoked.
    // It can optionally force the entity to be centered (based on the "center=false" flag).
    private fun spawnEntity(entity: Entity, position: Coordinates, forceCenter: Boolean) {
        entity.info.position = position
        entity.logic.spawn(forceSpawn = true, forceCentering = forceCenter)
    }

    // If the spawned entity is a Character (a type of entity that can move),
    // this method freezes it by disabling its movement ability (setting canMove to false).
    private fun freezeCharacter(character: Character) {
        character.state.canMove = false
    }

    // Prints information about the spawned entity, including its class name and position (x and y coordinates).
    // This provides feedback to the user, showing where the entity was spawned in the game world.
    private fun printSpawnInfo(entityClass: Class<out Entity>, entity: Entity) {
        val position = entity.info.position
        println("Spawning ${entityClass.simpleName} at ${position.x}, ${position.y}")
    }
}

