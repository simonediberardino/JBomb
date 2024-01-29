package game.network.events.process

import game.Bomberman
import game.engine.world.entity.types.EntityTypes
import game.engine.world.geo.Coordinates
import game.engine.world.entity.impl.models.Entity
import game.engine.events.models.HttpEvent
import game.engine.level.online.ClientGameHandler
import game.engine.ui.pages.LoadingPanel.LOADING_TIMER
import game.utils.Extensions.getOrTrim
import game.utils.Log
import java.awt.event.ActionEvent
import javax.swing.Timer

class SpawnedEntityHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val entityType = info.getOrTrim("entityType")?.toInt() ?: return
        val locationString = info.getOrTrim("location") ?: return
        val locTokens = locationString.split(" ").map { it.toInt() }
        val location = Coordinates(locTokens[0], locTokens[1])

        Log.i("SpawnedEntityHttpEventProcessor received $entityId, $entityType, $locationString")
        Log.i("Type $entityType $entityId")

        val entity = createEntity(entityId, entityType, info) ?: return
        entity.info.position = location

        val delay = if (!Bomberman.isInGame()) LOADING_TIMER + 1000 else 0

        val timer = Timer(delay) { _: ActionEvent? ->
            entity.spawn(true)
        }

        timer.isRepeats = false
        timer.start()
    }

    private fun createEntity(
            entityId: Long,
            entityType: Int,
            info: Map<String, String>
    ): Entity? {
        return if (entityId == (Bomberman.getMatch().onlineGameHandler as ClientGameHandler?)?.id) {
            EntityTypes.Player.toEntity(info)
        } else {
            EntityTypes.values().getOrElse(entityType) { return null }.toEntity(info)
        }
    }

}