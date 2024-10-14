package game.input.terminal.commands

import game.domain.world.domain.entity.actors.impl.EntityIds
import game.input.terminal.TerminalCommand

class EntityIdsCommand: TerminalCommand {
    override val name: String = "entityids"
    override val description: String = "Show all the available entity ids that can be spawned"

    override suspend fun execute(args: List<String>) {
        EntityIds.forEach { (t, u) ->
            println("> $t")
        }
    }
}