package game.http.events.process

import game.Bomberman
import game.entity.EntityTypes
import game.entity.models.Character
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.events.models.HttpEvent
import game.ui.pages.LoadingPanel
import game.utils.Extensions.getOrTrim
import java.awt.event.ActionEvent
import javax.swing.Timer

class LocationUpdatedHttpEventProcessor : HttpEvent {
    override fun invoke(info: Any) {
        info as Map<String, String>
        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val locationString = info.getOrTrim("location") ?: return
        val direction = info.getOrTrim("direction")?.toInt() ?: return
        val locTokens = locationString.split(" ").map { it.toInt() }
        val location = Coordinates(locTokens[0], locTokens[1])

        println("LocationUpdatedHttpEventProcessor received $entityId, $locationString")

        val entity: Character = Bomberman.getMatch().getEntityById(entityId) as Character? ?: return
        entity.coords = location
        entity.updateLastDirection(Direction.values()[direction])
    }
}