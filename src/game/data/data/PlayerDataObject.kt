package game.data.data

import game.domain.level.levels.Level
import game.localization.Localization
import java.awt.event.KeyEvent
import java.io.Serializable

data class PlayerDataObject(
        @JvmField var name: String,
        @JvmField var lostGames: Int,
        @JvmField var kills: Int,
        @JvmField var deaths: Int,
        @JvmField var rounds: Int,
        @JvmField var points: Long,
        @JvmField var lastLevelId: Int,
        @JvmField var lastWorldId: Int,
        @JvmField var explosionLength: Int,
        @JvmField var obtainedBombs: Int,
        @JvmField var lives: Int,
        @JvmField var forwardKey: Int,
        @JvmField var backKey: Int,
        @JvmField var leftKey: Int,
        @JvmField var rightKey: Int,
        @JvmField var bombKey: Int,
        @JvmField var interactKey: Int,
        @JvmField var volume: Int,
        @JvmField var skin: String
) : Serializable {

    constructor() : this(
            "",
            0,
            0,
            0,
            0,
            0,
            DataInputOutput.START_LIVES,
            0,
            1,
            1,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            ""
    ) {
        resetKeys()
    }

    fun resetKeys() {
        forwardKey = KeyEvent.VK_W
        backKey = KeyEvent.VK_S
        leftKey = KeyEvent.VK_A
        rightKey = KeyEvent.VK_D
        bombKey = KeyEvent.VK_SPACE
        interactKey = KeyEvent.VK_F
    }

    fun setLastLevel(lastLevel: Level) {
        lastLevelId = lastLevel.info.levelId
        lastWorldId = lastLevel.info.worldId
    }



    fun checkData(): Boolean {
        var changed = false
        if (skin.isEmpty()) {
            skin = "skin0"
            changed = true
        }
        if (name.isBlank()) {
            name = Localization.get(Localization.PLAYER)
            changed = true
        }
        return changed
    }
}
