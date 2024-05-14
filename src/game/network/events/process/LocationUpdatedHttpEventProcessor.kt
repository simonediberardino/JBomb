package game.network.events.process

import game.JBomb
import game.domain.world.domain.entity.actors.abstracts.character.Character
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import game.domain.events.models.HttpEvent
import game.mappers.dtoToEntityNetwork
import game.network.dispatch.HttpMessageDispatcher
import game.network.messages.LocationHttpMessage
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class LocationUpdatedHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val locationString = info.getOrTrim("location") ?: return
        val direction = info.getOrTrim("direction")?.toInt() ?: return
        val locTokens = locationString.split(" ").map { it.toInt() }
        val location = Coordinates(locTokens[0], locTokens[1]).fromAbsolute()
        val sentByClient = info.getOrTrim("sentByClient")?.toBoolean() ?: false // if true, the message is sent by a client and must be forwarded to all the other clients

        Log.i("LocationUpdatedHttpEventProcessor received $entityId, $locationString")

        val entity: Character = JBomb.match.getEntityById(entityId) as Character? ?: return
        entity.info.position = location
        entity.logic.updateMovementDirection(Direction.values()[direction])

        if (sentByClient) { // if message is sent by client, the message must be forwarded to all the other clients
            val newMap = info.toMutableMap()
            newMap["sentByClient"] = false.toString()

            HttpMessageDispatcher.instance.dispatch(
                    httpMessage = LocationHttpMessage(entity.dtoToEntityNetwork()),
                    receiverId = entityId,
                    ignore = true
            )
        }
    }
}