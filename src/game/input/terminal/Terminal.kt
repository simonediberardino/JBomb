package game.input.terminal

import game.input.terminal.commands.GetPositionCommand
import game.input.terminal.commands.SpawnCommand

object Terminal {
    fun start() {
        while (true) {
            try {
                val input = readlnOrNull() ?: continue
                val toks = input.lowercase().split(" ")
                exec(toks[0], toks.subList(1, toks.size))
            } catch (exception: Exception) {
                println(exception.message)
            }
        }
    }

    private fun exec(command: String, args: List<String>) {
        val cmdObject = when (command) {
            "spawn" -> SpawnCommand()
            "getposition" -> GetPositionCommand()
            else -> return
        }

        cmdObject.execute(args)
    }
}