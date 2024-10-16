package game.input.terminal.commands

import game.JBomb
import game.domain.level.levels.Level
import game.domain.match.JBombMatch.Companion.port
import game.input.terminal.TerminalCommand
import game.network.gamehandler.ServerGameHandler

class StartLevelCommand: TerminalCommand {
    override val name: String = "startlevel"
    override val description: String = "Starts a new level. Syntax <worldid> <levelid>"

    override suspend fun execute(args: List<String>) {
        val worldId = args[0].toIntOrNull() ?: return
        val levelId = args[1].toIntOrNull() ?: return

        val levelClassOpt = Level.findLevel(worldId, levelId)

        JBomb.startLevel(
            level = levelClassOpt.get().getConstructor().newInstance(),
            onlineGameHandler = ServerGameHandler(port),
            disconnect = false
        ) {
        }
    }
}