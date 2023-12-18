package game.http.models

interface HttpMessage {
    fun serialize() : String
    val senders: Array<HttpActor>
}