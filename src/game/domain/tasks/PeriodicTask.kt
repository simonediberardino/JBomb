package game.domain.tasks

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

class PeriodicTask(private val callback: Runnable, private val delay: Int) {
    private val timer: Timer
    private val actionListener: ActionListener = ActionListener { e: ActionEvent? -> callback.run() }

    init {
        timer = Timer(delay, actionListener)
        timer.isRepeats = true
    }

    fun start() {
        timer.start()
    }

    fun setDelay(delay: Int) {
        timer.delay = delay
    }

    fun resume() {
        try {
            timer.start()
        } catch (ignored: Exception) {
        }
    }

    fun stop() {
        timer.stop()
    }
}