package game.http.events.process

import game.entity.EntityTypes
import game.entity.bonus.mysterybox.MysteryBox
import game.entity.models.Coordinates
import game.events.models.HttpEvent
import game.http.models.HttpMessageTypes
import game.ui.pages.LoadingPanel.LOADING_TIMER
import game.utils.Extensions.getOrTrim
import java.awt.event.ActionEvent
import javax.swing.Timer

class SpawnedEntityHttpEventProcessor : HttpEvent {
    override fun invoke(info: Any) {
        info as Map<String, String>
        val t = Timer(LOADING_TIMER + 1000) { _: ActionEvent? ->
            val entityId = info.getOrTrim("entityId")?.toLong() ?: return@Timer
            val entityType = info.getOrTrim("entityType")?.toInt() ?: return@Timer
            val locationString = info.getOrTrim("location") ?: return@Timer
            val locTokens = locationString.split(" ").map { it.toInt() }
            val location = Coordinates(locTokens[0], locTokens[1])

            println("SpawnedEntityHttpEventProcessor received $entityId, $entityType, $locationString")

            val entity = EntityTypes.values()[entityType].toEntity(entityId)
            entity.coords = location
            entity.spawn(true)
        }
        t.isRepeats = false
        t.start()
    }
}