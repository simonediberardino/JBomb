package game.network.models

interface HttpMessage {
    fun serialize() : String
    val senders: Array<HttpActor>
}