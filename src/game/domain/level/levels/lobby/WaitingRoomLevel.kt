package game.domain.level.levels.lobby

import game.localization.Localization

class WaitingRoomLevel : IslandLevel() {
    override fun toString(): String {
        return Localization.get(Localization.WAITING_ROOM)
    }
}