package game.input.terminal.commands

import game.input.terminal.TerminalCommand
import game.repository.RepositoryEntities

class EntityIdsCommand: TerminalCommand {
    override val name: String = "entityids"
    override val description: String = "Show all the available entity ids that can be spawned"

    override suspend fun execute(args: List<String>) {
        RepositoryEntities.entityIds.forEach { (t, u) ->
            println("> $t")
        }
    }
}