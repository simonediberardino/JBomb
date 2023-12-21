package game.hardwareinput

import game.Bomberman
import game.data.DataInputOutput
import game.entity.Player
import game.events.game.Observable2
import game.tasks.PeriodicTask
import game.utils.Utility.timePassed
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

/**
 * This class represents an Observable object that observes key events and notifies its observers of the
 * corresponding command that should be executed based on the key that was pressed.
 */
class ControllerManager : Observable2(), KeyListener {
    // Stores the time of the last key event for each command
    private val commandEventsTime: MutableMap<Command?, Long> = hashMapOf()
    var player: Player? = null

    // Key-Command mapping
    private var keyAssignment: Map<Int, Command>? = null
    private lateinit var task: PeriodicTask

    init {
        instance = this
        setupTask()
        // If illegal keys are found, reset the key map;
        try {
            setKeyMap()
        } catch (e: IllegalArgumentException) {
            DataInputOutput.getInstance().resetKeys()
            DataInputOutput.getInstance().updateStoredPlayerData()
            setKeyMap()
            e.printStackTrace()
        }
    }

    private fun setKeyMap() {
        val dataInputOutput = DataInputOutput.getInstance()

        keyAssignment = mapOf(
                dataInputOutput.forwardKey to Command.MOVE_UP,
                dataInputOutput.leftKey to Command.MOVE_LEFT,
                dataInputOutput.backKey to Command.MOVE_DOWN,
                dataInputOutput.rightKey to Command.MOVE_RIGHT,
                dataInputOutput.bombKey to Command.ATTACK,
                KEY_ESC to Command.PAUSE
        )
    }

    private fun pressKey(action: Command?) {
        // if a button is pressed, mouse movement gets interrupted
        if(action?.isMovementKey()!!) {
            Bomberman.getMatch().mouseControllerManager.stopMovementTask()
        }
        onKeyPressed(action)
    }

    fun onKeyPressed(action: Command){
        // Ignore the event if the time elapsed since the last event is less than KEY_DELAY_MS
        if (player != null) {
            commandEventsTime[action] = System.currentTimeMillis()
            player!!.commandQueue.add(action)
        }

        resume()
    }

    /**
     * Handles key pressed events and notifies observers with the corresponding command that should be executed.
     *
     * @param e the KeyEvent object that contains the information of the key that was pressed.
     */
    override fun keyPressed(e: KeyEvent) {
        val action = keyAssignment?.get(e.keyCode) ?: return

        if (timePassed(commandEventsTime.getOrDefault(action, 0L)) < KEY_DELAY_MS)
            return

        pressKey(action)
    }

    override fun keyReleased(e: KeyEvent) {
        val action = keyAssignment?.get(e.keyCode) ?: return
        onKeyReleased(action)
    }

    fun onKeyReleased(action: Command?) {
        player?.commandQueue?.remove(action)

        if (player?.commandQueue?.isEmpty() == true)
            stop()
    }

    private fun setupTask() {
        task = PeriodicTask(Runnable {
            player ?: return@Runnable

            for (command in HashSet(player!!.commandQueue)) {
                notifyObservers(command)
            }
        }, KEY_DELAY_MS)

        task.start()
    }

    private fun resume() {
        try {
            task.resume()
        } catch (ignored: Exception) { }
    }

    private fun stop() {
        try {
            task.stop()
        } catch (ignored: Exception) { }
    }

    fun isCommandPressed(c: Command): Boolean {
        return player!!.commandQueue.contains(c)
    }

    private fun updateDelay() {
        instance!!.task.setDelay(KEY_DELAY_MS)
    }

    override fun keyTyped(e: KeyEvent) {}

    companion object {
        private const val KEY_ESC = KeyEvent.VK_ESCAPE
        private var instance: ControllerManager? = null
        private var KEY_DELAY_MS = setDefaultCommandDelay()

        fun decreaseCommandDelay(): Int {
            KEY_DELAY_MS = 15
            if (instance != null) {
                instance!!.updateDelay()
            }
            return KEY_DELAY_MS
        }

        
        fun setDefaultCommandDelay(): Int {
            KEY_DELAY_MS = 30
            if (instance != null) {
                instance!!.updateDelay()
            }
            return KEY_DELAY_MS
        }
    }
}