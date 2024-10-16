package game.input.terminal

import game.domain.level.levels.level_editor.LevelEditor
import game.input.terminal.commands.*

object Terminal {
    private val helpCommand = object: TerminalCommand {
        override val name: String
            get() = "help"

        override val description: String
            get() = "Show all the available commands"

        override suspend fun execute(args: List<String>) {
            commands.forEach {
                println("> ${it.name}: ${it.description}")
            }
        }
    }

    private val commands: List<TerminalCommand> = listOf(
        SpawnCommand(),
        GetPositionCommand(),
        LevelEditorCommand(),
        EntityIdsCommand(),
        DespawnCommand(),
        ShowNearEntities(),
        StartLevelCommand(),
        helpCommand
    )

    suspend fun start() {
        println("Starting terminal")

        while (true) {
            val input = readlnOrNull() ?: continue
            val toks = input.lowercase().split(" ")
            exec(toks[0], toks.subList(1, toks.size))
        }
    }

    private suspend fun exec(command: String, args: List<String>) {
        val cmdObject = commands.firstOrNull { it.name == command } ?: return
        cmdObject.execute(args)
    }
}