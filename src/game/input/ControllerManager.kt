package game.input

import game.JBomb
import game.data.data.DataInputOutput
import game.domain.tasks.observer.Observable2
import game.utils.Utility.timePassed
import game.utils.time.now
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

/**
 * This class represents an Observable object that observes key events and notifies its observers of the
 * corresponding command that should be executed based on the key that was pressed.
 */
class ControllerManager : Observable2(), KeyListener {
    private val commandQueue: HashSet<Command> = hashSetOf()

    // Stores the time of the last key event for each command
    private val commandEventsTime: MutableMap<Command?, Long> = mutableMapOf()
    // Key-Command mapping
    private var keyAssignment: Map<Int, Command>? = null

    init {
        instance = this
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
                dataInputOutput.interactKey to Command.INTERACT,
                KEY_ESC to Command.PAUSE
        )
    }

    private fun pressKey(action: Command) {
        // if a button is pressed, mouse movement gets interrupted
        if (action.isMovementKey()) {
            JBomb.match.mouseControllerManager.stopMovementTask()
        }

        onKeyPressed(action)
    }

    fun onKeyPressed(action: Command) {
        // Ignore the event if the time elapsed since the last event is less than KEY_DELAY_MS
        commandEventsTime[action] = now()
        notifyObservers(ObserverParam(ObserverParamIdentifier.INPUT_COMMAND, action))
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
        notifyObservers(ObserverParam(ObserverParamIdentifier.DELETE_COMMAND, action))
        commandQueue.remove(action)
    }

    fun isCommandPressed(c: Command): Boolean {
        return commandQueue.contains(c)
    }

    override fun keyTyped(e: KeyEvent) {}

    companion object {
        private const val KEY_ESC = KeyEvent.VK_ESCAPE
        private var instance: ControllerManager? = null
        private var KEY_DELAY_MS = 30
    }
}