package game.input.terminal

interface TerminalCommand {
    val name: String
    val description: String
    suspend fun execute(args: List<String>)
}