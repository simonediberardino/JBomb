package game.network.models

interface HttpMessage {
    fun serialize() : MutableMap<String, String>
    val senders: Array<HttpActor>
}