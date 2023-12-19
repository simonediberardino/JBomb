package game.tasks

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

class GarbageCollectorTask {
    private val taskPerformer = ActionListener { evt: ActionEvent? ->
        System.gc()
    }
    private val timer = Timer(DELAY_MS, taskPerformer)
    fun start() {
        timer.start()
    }

    fun stop() {
        timer.stop()
    }

    companion object {
        private const val DELAY_MS = 10 * 1000
    }
}