package game.http.models

import game.http.models.HttpActor

interface HttpMessage {
    fun serialize() : String
    val receivers: Array<HttpActor>
}