package game.events.http

import game.Bomberman
import game.entity.EntityTypes
import game.entity.Player
import game.entity.models.Direction
import game.events.models.HttpEvent
import game.http.dao.CharacterDao
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.PlayerJoinRequestHttpMessage
import game.http.messages.SpawnedEntityHttpMessage

class CharacterSpawnedHttpEvent: HttpEvent {
    override fun invoke(info: Map<String, String>) {
        val clientId = info["id"]?.toLong()
        println("CharacterSpawnedHttpEvent: $clientId")

        clientId ?: return
        val match = Bomberman.getMatch() ?: return
        val coordinates = (match.currentLevel ?: return).info.playerSpawnCoordinates

        val player = Player(coordinates, clientId)
        player.spawn()

        // if ID is equal to client id, assign it to BombermanMatch player
        if (Bomberman.getMatch().isClient) {
            if (player.id == match.clientGameHandler?.id?.toLong()) {
                match.player = player
                match.assignPlayerToControllerManager()
            }
        }
    }
}