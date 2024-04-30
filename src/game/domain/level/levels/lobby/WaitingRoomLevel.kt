package game.domain.level.levels.lobby

import game.localization.Localization
import game.presentation.ui.viewelements.misc.ToastHandler
import game.utils.dev.Log

class WaitingRoomLevel : IslandLevel() {
    override fun generateLevel() {
        super.generateLevel()

        ToastHandler.getInstance().show(
                Localization.get(Localization.CONNECTING),
                true,
                false
        )
    }

    override fun toString(): String {
        return Localization.get(Localization.WAITING_ROOM)
    }
}