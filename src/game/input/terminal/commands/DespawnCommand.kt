package game.input.terminal.commands

import game.JBomb
import game.input.terminal.TerminalCommand

class DespawnCommand: TerminalCommand {
    override val name: String = "despawn"
    override val description: String =
        "Despawn an entity given the entity id"


    override suspend fun execute(args: List<String>) {
        // Check if the game is active, return early if not.
        if (!JBomb.match.gameState) return

        val entityId = args.getOrNull(0)?.toLongOrNull() ?: return

        val entity = JBomb.match.getEntityById(entityId = entityId)
        entity?.logic?.despawn()
    }
}