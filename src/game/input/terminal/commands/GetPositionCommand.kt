package game.input.terminal.commands

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.actors.impl.EntityIds
import game.domain.world.domain.entity.geo.Coordinates
import game.input.terminal.TerminalCommand
import java.lang.RuntimeException
import java.util.*

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