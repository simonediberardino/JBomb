package game.input

import game.JBomb
import game.JBomb.match
import game.domain.tasks.PeriodicTask
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.impl.bomber_entity.player.Player
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.geo.Direction
import kotlinx.coroutines.CoroutineScope
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import java.util.*

/**
 * The MouseControllerManager class handles mouse interactions and controls player movement based on mouse input.
 */
class MouseControllerManager(
        scope: CoroutineScope
) : MouseAdapter(), MouseMotionListener {
    private val DELAY = 300L

    // First and last directions from the player are differentiated so the task can be terminated when they have no direction in common,
    // in order to avoid an infinite loop
    private var firstDirectionsFromPlayer: List<Direction> = ArrayList()
    private var latestDirectionsFromPlayer: List<Direction> = ArrayList()
    private var entity: Entity? = null
    private val playerMovementTask = PeriodicTask({ movementTask() }, DELAY, scope)
    private var isMouseDragged = false
    private var isMouseClicked = false
    private var mouseClickQueue = LinkedList<MouseEvent>()
    private val player: Player
        get() = match.player!!

    var mouseCoords: Coordinates? = null
        private set

    /**
     * Retrieves and removes the next command in the mouseClickQueue.
     */
    private fun nextCommandInQueue() {
        mouseClickQueue.poll()

        if (!mouseClickQueue.isEmpty())
            onMouseClicked(mouseClickQueue.peekFirst())
    }

    private fun movementTask() {
        // Check if there is a player and the player is alive
        if (!player.logic.isAlive()) {
            return // If not, exit the task
        }

        onCooldown()

        entity?.let {
            // Perform mouse interactions on the entity
            it.logic.mouseClickedInteraction()
            onMovementPeriodicTaskEnd()
            nextCommandInQueue()
            return // Exit the task
        }

        // Calculate directions based on mouse coordinates
        latestDirectionsFromPlayer = mouseCoords?.fromCoordinatesToDirection(
                player.info.position
        ) ?: emptyList()

        // Press keys corresponding to calculated directions
        latestDirectionsFromPlayer.forEach {
            match.controllerManager?.onKeyPressed(it.toCommand())
        }

        // Check if first directions are not empty and all are in the latest directions
        if (firstDirectionsFromPlayer.isNotEmpty() && firstDirectionsFromPlayer.all { it in latestDirectionsFromPlayer }) {
            return // Exit the task
        }

        // Perform end-of-movement task operations
        onMovementPeriodicTaskEnd()
        nextCommandInQueue()
    }


    /**
     * Called when the mouse button is released.
     *
     * @param event The MouseEvent representing the release of the mouse button.
     */
    override fun mouseReleased(event: MouseEvent) {
        // If a mouse dragged interaction was not successful and the mouse is released, call mouseClicked
        // so that click-dependent interactions can be called.
        // This is necessary because mouseClicked is never called when mouseDragged is called, unlike mousePressed.
        // mouseClicked was still chosen because the two methods shouldn't be called at exactly the same time.
        if (!isMouseDragged) {
            return
        }

        mouseClicked(event)
        isMouseDragged = false
    }

    /**
     * Called when the mouse button is pressed.
     *
     * @param event The MouseEvent representing the press of the mouse button.
     */
    override fun mousePressed(event: MouseEvent) {
        if (isMouseClicked)
            return

        mouseCoords = Coordinates(event.x, event.y)
        entity = Coordinates.getEntityOnCoordinates(mouseCoords)
    }

    override fun mouseClicked(e: MouseEvent) {
        if (!mouseClickQueue.isEmpty()) {
            mouseClickQueue.add(e)
            return
        }

        mouseClickQueue.add(e)
        onMouseClicked(e)
    }

    private fun onMouseClicked(e: MouseEvent) {
        if (isMouseClicked)
            return

        isMouseClicked = true

        mouseCoords = Coordinates(e.x, e.y)
        firstDirectionsFromPlayer = mouseCoords!!.fromCoordinatesToDirection(
                player.info.position
        )

        entity = Coordinates.getEntityOnCoordinates(mouseCoords)
        startMouseTasks()
    }

    // Directions are refreshed and will be replaced in task
    private fun onCooldown() {
        val controllerManager = JBomb.match.controllerManager ?: return

        for (direction in firstDirectionsFromPlayer) {
            controllerManager.onKeyReleased(direction.toCommand())
        }
    }

    fun stopMovementTask() {
        mouseClickQueue = LinkedList()
        onMovementPeriodicTaskEnd()
    }

    private fun onMovementPeriodicTaskEnd() {
        if (!isMouseClicked)
            return

        playerMovementTask.stop()
        onCooldown()

        isMouseClicked = false
    }

    override fun mouseDragged(event: MouseEvent) {
        if (isMouseClicked)
            return

        isMouseDragged = true
        mouseCoords = Coordinates(event.x, event.y)

        entity?.logic?.mouseDraggedInteraction()
    }

    private fun startMouseTasks() {
        playerMovementTask.resume()
    }
}