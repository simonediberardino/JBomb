package game.network.events.process

import game.JBomb
import game.domain.events.models.HttpEvent
import game.domain.level.levels.Level
import game.network.dispatch.HttpMessageDispatcher
import game.network.gamehandler.ClientGameHandler
import game.network.messages.PlayerJoinRequestHttpMessage
import game.utils.dev.Extensions.getOrTrim
import game.utils.dev.Log

class LevelInfoHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val id = info.getOrTrim("id")?.toIntOrNull() ?: return
        val levelId = info.getOrTrim("levelId")?.toIntOrNull() ?: return
        val worldId = info.getOrTrim("worldId")?.toIntOrNull() ?: return
        val skinId = JBomb.match.player?.properties?.skinId ?: 0

        val onlineGameHandler = JBomb.match.onlineGameHandler as? ClientGameHandler
        onlineGameHandler?.id = id.toLong()

        val levelClassOpt = Level.findLevel(worldId, levelId)

        Log.i("LevelInfoHttpEventProcessor: $info, $levelClassOpt")

        Thread.sleep(1500) // TODO

        if (levelClassOpt.isPresent) {
            JBomb.startLevel(
                    level = levelClassOpt.get().getConstructor().newInstance(),
                    onlineGameHandler = onlineGameHandler,
                    disconnect = false
            ) {
                // Client confirms joining the match after receiving the ID.
                HttpMessageDispatcher.instance.dispatch(PlayerJoinRequestHttpMessage(id, skinId))
            }
        } else {
            throw RuntimeException("Level $worldId, $levelId does not exist")
        }
    }
}