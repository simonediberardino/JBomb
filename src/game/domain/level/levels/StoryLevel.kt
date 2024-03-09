package game.domain.level.levels

import game.data.data.DataInputOutput
import game.domain.events.game.RoundPassedGameEvent

abstract class StoryLevel : Level() {
    override fun endLevel() {
        try {
            DataInputOutput.getInstance().setLastLevel(info.nextLevel!!.getConstructor().newInstance())
            DataInputOutput.getInstance().increaseRounds()
            DataInputOutput.getInstance().updateStoredPlayerData()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        RoundPassedGameEvent().invoke(null)
    }

    override fun onStartLevel() {}
}