package game.properties

object RuntimeProperties {
    val processors = Runtime.getRuntime().availableProcessors()
    var dedicatedServer: Boolean = false
    var lastConnectedIp: String = ""
    var needsUpdate: Boolean = false
    var port: Int? = null
    var argLevel: Int? = null
    var argWorld: Int? = null
}