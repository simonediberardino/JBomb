package game.level

import game.data.DataInputOutput
import game.events.game.RoundPassedGameEvent
import game.localization.Localization
import java.lang.reflect.InvocationTargetException

abstract class StoryLevel : Level() {
    override val diedMessage: String
        get() {
            val lives = DataInputOutput.getInstance().lives
            return Localization.get(Localization.YOU_DIED).replace("%lives%", Integer.toString(lives))
        }

    override fun endLevel() {
        try {
            DataInputOutput.getInstance().setLastLevel(nextLevel!!.getConstructor().newInstance())
            DataInputOutput.getInstance().increaseRounds()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        RoundPassedGameEvent().invoke(null)
        DataInputOutput.getInstance().updateStoredPlayerData()
    }

    override fun onDeathGameEvent() {
        DataInputOutput.getInstance().increaseDeaths()
        DataInputOutput.getInstance().decreaseLives()
        DataInputOutput.getInstance().decreaseScore(1000)
    }

    override val isArenaLevel: Boolean
        get() = false
}