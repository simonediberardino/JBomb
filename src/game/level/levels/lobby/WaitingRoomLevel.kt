package game.level.levels.lobby

import game.Bomberman
import game.http.dispatch.HttpMessageDispatcher
import game.http.messages.PlayerJoinRequestHttpMessage
import game.level.gamehandler.model.GameHandler
import game.level.online.ClientGameHandler
import game.localization.Localization

class WaitingRoomLevel : IslandLevel() {
    override fun toString(): String {
        return Localization.get(Localization.WAITING_ROOM)
    }
}