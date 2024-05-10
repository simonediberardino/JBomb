package game.network.events.process

import game.Bomberman
import game.domain.events.models.HttpEvent
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.types.EntityTypes
import game.mappers.toEntity
import game.network.gamehandler.ClientGameHandler
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class SpawnedEntityHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val entityId = info.getOrTrim("entityId")?.toLong() ?: return
        val entityType = info.getOrTrim("entityType")?.toInt() ?: return
        val locationString = info.getOrTrim("location") ?: return
        val locTokens = locationString.split(" ").map { it.toInt() }
        val location = Coordinates(locTokens[0], locTokens[1]).fromAbsolute()

        Log.i("SpawnedEntityHttpEventProcessor received $entityId, $entityType, $locationString")
        Log.i("Type $entityType $entityId")

        val entity = createEntity(entityId, entityType) ?: return
        entity.info.position = location

        entity.logic.spawn(forceSpawn = true)
    }

    private fun createEntity(
            entityId: Long,
            entityType: Int
    ): Entity? {
        return if (entityId == (Bomberman.match.onlineGameHandler as ClientGameHandler?)?.id) {
            EntityTypes.Player.toEntity(entityId)
        } else {
            EntityTypes.values().getOrElse(entityType) { return null }.toEntity(entityId)
        }
    }

}