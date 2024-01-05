package game.http.events.process

import game.Bomberman
import game.events.models.HttpEvent
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.PlayerJoinRequestHttpMessage
import game.level.levels.Level
import game.level.online.ClientGameHandler
import game.utils.Extensions.getOrTrim
import java.lang.Exception
import java.lang.RuntimeException

class LevelInfoHttpEventProcessor : HttpEvent {
    override fun invoke(vararg extras: Any) {
        val info = extras[0] as Map<String, String>

        val id = info.getOrTrim("id")?.toIntOrNull() ?: return
        val levelId = info.getOrTrim("levelId")?.toIntOrNull() ?: return
        val worldId = info.getOrTrim("worldId")?.toIntOrNull() ?: return

        val onlineGameHandler = Bomberman.getMatch().onlineGameHandler as? ClientGameHandler
        onlineGameHandler?.id = id.toLong()

        val levelClassOpt = Level.findLevel(worldId, levelId)

        println("LevelInfoHttpEventProcessor: $info, $levelClassOpt")

        Thread.sleep(1500) // TODO

        if (levelClassOpt.isPresent) {
            Bomberman.startLevel(levelClassOpt.get().getConstructor().newInstance(), onlineGameHandler)
        } else {
            throw RuntimeException("Level $worldId, $levelId does not exist")
        }

        // Client confirms joining the match after receiving the ID.
        HttpMessageDispatcher.instance.dispatch(PlayerJoinRequestHttpMessage(id))
    }
}