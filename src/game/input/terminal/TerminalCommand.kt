package game.input.terminal

interface TerminalCommand {
    fun execute(args: List<String>)
}