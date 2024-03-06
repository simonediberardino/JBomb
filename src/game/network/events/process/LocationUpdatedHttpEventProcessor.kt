package game.network.events.process

import game.Bomberman
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.events.models.HttpEvent
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class LocationUpdatedHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val locationString = info.getOrTrim("location") ?: return
        val direction = info.getOrTrim("direction")?.toInt() ?: return
        val locTokens = locationString.split(" ").map { it.toInt() }
        val location = Coordinates(locTokens[0], locTokens[1])

        Log.i("LocationUpdatedHttpEventProcessor received $entityId, $locationString")

        val entity: Character = Bomberman.getMatch().getEntityById(entityId) as Character? ?: return
        entity.info.position = location
        entity.logic.updateMovementDirection(Direction.values()[direction])
    }
}