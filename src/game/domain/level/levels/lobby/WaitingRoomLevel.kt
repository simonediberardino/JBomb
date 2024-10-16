package game.domain.level.levels.lobby

import game.localization.Localization
import game.utils.ui.ToastUtils
import game.utils.dev.Log

class WaitingRoomLevel : IslandLevel() {
    override fun generateLevel() {
        super.generateLevel()

        ToastUtils.show(
                Localization.get(Localization.CONNECTING),
                true,
                false
        )
    }

    override fun toString(): String {
        return Localization.get(Localization.WAITING_ROOM)
    }
}