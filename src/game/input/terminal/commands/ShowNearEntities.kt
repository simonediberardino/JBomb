package game.input.terminal.commands

import game.JBomb
import game.input.terminal.TerminalCommand

class ShowNearEntities: TerminalCommand {
    override val name: String
        get() = "nearentities"
    override val description: String
        get() = "Show near entities ids"

    override suspend fun execute(args: List<String>) {
        if (!JBomb.match.gameState) return

        val player = JBomb.match.player ?: return

        player.state.collidedEntities.forEach {
            println("> ${it.javaClass.name} with id ${it.info.id}")
        }
    }
}