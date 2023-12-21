package game.hardwareinput

import game.Bomberman
import game.entity.models.Coordinates
import game.entity.models.Direction
import game.entity.models.Entity
import game.tasks.PeriodicTask
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import java.util.*

/**
 * The MouseControllerManager class handles mouse interactions and controls player movement based on mouse input.
 */
class MouseControllerManager : MouseAdapter(), MouseMotionListener {
    private val DELAY = 300
    private var mouseDraggedInteractionOccured = false
    var isMouseDraggedInteractionInterrupted = false
    var isMouseDragInteractionEntered = false
    var isMouseDragged = false
        private set
    var isMouseClicked = false
        private set
    private var mouseClickQueue = LinkedList<MouseEvent>()

    // First and last directions from the player are differentiated so the task can be terminated when they have no direction in common,
    // in order to avoid an infinite loop
    private var firstDirectionsFromPlayer: List<Direction> = ArrayList()
    private var latestDirectionsFromPlayer: List<Direction> = ArrayList()
    var mouseCoords: Coordinates? = null
        private set
    var entity: Entity? = null
        private set

    /**
     * Retrieves and removes the next command in the mouseClickQueue.
     */
    private fun nextCommandInQueue() {
        mouseClickQueue.poll()

        if (!mouseClickQueue.isEmpty())
            onMouseClicked(mouseClickQueue.peekFirst())
    }

    private val movementTask = Runnable {
        val match = Bomberman.getMatch()
        val currPlayer = match.player ?: return@Runnable
        onCooldown()

        if (!currPlayer.aliveState) return@Runnable

        val entityToInteract = entity
        if (entityToInteract != null && match.player.listClassInteractWithMouseClick.contains(entityToInteract.javaClass)) {
            entityToInteract.mouseInteractions()
            onMovementPeriodicTaskEnd()
            nextCommandInQueue()
            return@Runnable
        }

        latestDirectionsFromPlayer = mouseCoords?.fromCoordinatesToDirection(currPlayer.coords) ?: emptyList()

        latestDirectionsFromPlayer.forEach {
            match.controllerManager.onKeyPressed(it.toCommand())
        }

        if (firstDirectionsFromPlayer.isNotEmpty() && firstDirectionsFromPlayer.none { it !in latestDirectionsFromPlayer }) {
            return@Runnable
        }
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
        if (!mouseDraggedInteractionOccured) mouseClicked(event)
        isMouseDragInteractionEntered = false
        isMouseDragged = false
        mouseDraggedInteractionOccured = false
        isMouseDraggedInteractionInterrupted = false
    }

    /**
     * Called when the mouse button is pressed.
     *
     * @param event The MouseEvent representing the press of the mouse button.
     */
    override fun mousePressed(event: MouseEvent) {
        if (isMouseClicked) return
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

    fun onMouseClicked(e: MouseEvent) {
        if (isMouseClicked) return
        isMouseClicked = true
        val player = Bomberman.getMatch().player
        if (player == null || !player.aliveState) {
            return
        }

        //isMouseClicked = true;
        mouseCoords = Coordinates(e.x, e.y)
        firstDirectionsFromPlayer = mouseCoords!!.fromCoordinatesToDirection(Bomberman.getMatch().player.coords)
        entity = Coordinates.getEntityOnCoordinates(mouseCoords)
        startMouseTasks()
    }


    private val playerMovementTask = PeriodicTask(movementTask, DELAY)

    // Directions are refreshed and will be replaced in task
    fun onCooldown() {
        for (d in firstDirectionsFromPlayer) {
            Bomberman.getMatch().controllerManager.onKeyReleased(d.toCommand())
        }
    }

    fun setMouseDraggedInteractionOccured(mouseDraggedInteractionOccured: Boolean) {
        this.mouseDraggedInteractionOccured = mouseDraggedInteractionOccured
    }

    fun stopMovementTask() {
        mouseClickQueue = LinkedList()
        onMovementPeriodicTaskEnd()
    }

    private fun onMovementPeriodicTaskEnd() {
        if (!isMouseClicked) return
        playerMovementTask.stop()
        onCooldown()
        isMouseClicked = false
    }

    override fun mouseDragged(event: MouseEvent) {
        if (isMouseClicked) return
        isMouseDragged = true
        mouseCoords = Coordinates(event.x, event.y)
        if (entity == null) return
        entity!!.mouseInteractions()
    }
    fun startMouseTasks(){
        playerMovementTask.resume()
    }
}