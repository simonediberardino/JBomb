package game.engine.level.levels

import game.storage.data.DataInputOutput
import game.engine.events.game.RoundPassedGameEvent

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