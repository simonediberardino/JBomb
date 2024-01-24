package game.utils

object Log {
    private val baseLogsEnabled = XMLUtils.readConfig("logs_base").toBoolean()
    private val logsErrorEnabled = XMLUtils.readConfig("logs_error").toBoolean()
    private val logsWarningEnabled = XMLUtils.readConfig("logs_warning").toBoolean()

    private val red = rgb(255, 0, 0)
    private val yellow = rgb(255, 255, 0)
    private val reset = "\u001b[0m"

    private fun rgb(r: Int, g: Int, b: Int): String {
        return "\u001b[38;2;$r;$g;${b}m"
    }

    fun i(message: Any) {
        if (!baseLogsEnabled)
            return

        println(message)
    }

    fun e(message: Any) {
        if (!logsErrorEnabled)
            return

        print(red, message)
    }

    fun w(message: Any) {
        if (!logsWarningEnabled)
            return

        print(yellow, message)
    }

    private fun print(color: String, message: Any) {
        println("${color}${message}")
    }
}