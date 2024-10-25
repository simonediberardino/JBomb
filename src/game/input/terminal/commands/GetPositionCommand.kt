package game.input.terminal.commands

import game.JBomb
import game.input.terminal.TerminalCommand

class GetPositionCommand: TerminalCommand {
    override val name: String = "getposition"
    override val description: String = "Show the current coordinates"

    override suspend fun execute(args: List<String>) {
        if (!JBomb.match.gameState)
            return

        val player = JBomb.match.player ?: return
        println("Current position is ${player.info.position.x}, ${player.info.position.y}")
    }
}