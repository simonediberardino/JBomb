package game.domain.world.domain.entity.actors.abstracts.base.logic

import game.Bomberman
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.IEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Coordinates
import game.network.events.forward.DespawnEntityEventForwarder
import game.network.events.forward.SpawnEntityEventForwarder
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
import game.utils.dev.Log
import game.utils.time.now

abstract class EntityLogic(
        open val entity: Entity
) : IEntityLogic {
    override fun eliminated() {
        despawn()
        notifyDespawn()
    }

    override fun despawn() {
        entity.state.isSpawned = false
        onDespawn()
        Bomberman.match.removeEntity(entity)
    }

    override fun spawn() {
        spawn(forceSpawn = false, forceCentering = true)
    }

    override fun spawn(coordinates: Coordinates) {
        entity.info.position = coordinates
        spawn()
    }

    override fun spawn(forceSpawn: Boolean, forceCentering: Boolean) {
        if (entity.state.isSpawned) {
            return
        }

        if (forceCentering)
            entity.info.position = Coordinates.roundCoordinates(entity.info.position, spawnOffset())


        Coordinates.getEntitiesOnBlock(entity.info.position).forEach {
            entity.logic.interact(it)
        }

        if (forceSpawn || !Coordinates.isBlockOccupied(entity.info.position)) {
            entity.state.isSpawned = true
            Bomberman.match.addEntity(entity)
            onSpawn()
        }
    }

    override fun notifySpawn() {
        SpawnEntityEventForwarder(-1).invoke(entity.toEntityNetwork())
    }

    override fun notifyDespawn() {
        DespawnEntityEventForwarder().invoke(entity.toEntityNetwork())
    }

    override fun onSpawn() {
        entity.state.state = (State.SPAWNED)
        notifySpawn()
    }

    override fun onDespawn() {
        entity.state.state = (State.DIED)
    }

    override fun onExplosion(explosion: AbstractExplosion?) {}

    override fun onImmuneChangedState() {
        entity.state.state = (if (entity.state.isImmune)
            State.IMMUNE
        else if (entity.state.isSpawned)
            State.SPAWNED
        else State.DIED)
    }

    override fun spawnOffset(): Coordinates =
            Coordinates((PitchPanel.GRID_SIZE - entity.state.size) / 2, (PitchPanel.GRID_SIZE - entity.state.size) / 2)


    override fun mouseInteractions() {
        val mouseControllerManager = Bomberman.match.mouseControllerManager
        mouseControllerManager.entity ?: return

        if (canEntityInteractWithMouseClick()) {
            onMouseClickInteraction()
            return
        }

        if (canEntityInteractWithMouseDrag()) {
            onMouseDragInteraction()
        }
    }

    override fun onTalk(entity: Entity) {
    }

    final override fun talk(entity: Entity) {
        if (Utility.timePassed(entity.state.lastTalkTime) < 500)
            return

        entity.state.lastTalkTime = now()
        onTalk(entity)
    }

    /**
     * Checks if this entity can be interacted with by another entity.
     *
     * @param e The entity attempting to interact.
     * @return `true` if the entity can be interacted with, `false` otherwise.
     */
    override fun canBeInteractedBy(e: Entity?): Boolean {
        return e == null || entity.state.interactionEntities.any { c: Class<out Entity> -> c.isInstance(e) }
    }

    override fun canEntityInteractWithMouseClick(): Boolean {
        val match = Bomberman.match
        val player = match.player ?: return false

        // Check if the player can interact with mouse click and if the mouse is being clicked
        return player.logic.isMouseClickInteractable(entity) && match.mouseControllerManager.isMouseClicked
    }

    override fun canEntityInteractWithMouseDrag(): Boolean {
        val match = Bomberman.match
        val player = match.player ?: return false

        // Check if the player can interact with mouse drag and if the mouse is being dragged
        // TODO CHANGED!!
        return player.logic.isMouseDragInteractable(entity) && match.mouseControllerManager.isMouseDragged
    }

    override fun onMouseClickInteraction() {
        val match = Bomberman.match
        val player = match.player

        val centerCoordinatesOfEntity = Coordinates.roundCoordinates(Coordinates.getCenterCoordinatesOfEntity(player))

        if (entity.info.position.distanceTo(centerCoordinatesOfEntity) <= PitchPanel.GRID_SIZE) {
            eliminated()
        }
    }


    final override fun collide(e: Entity) {
        if (entity.state.collidedEntities.contains(e)) {
            return
        }

        if (e == entity) {
            return
        }

        entity.state.collidedEntities.add(e)
        onCollision(e)

        e.logic.collide(entity)
    }

    override fun onCollision(e: Entity) {

    }

    final override fun unCollide(e: Entity) {
        if (!entity.state.collidedEntities.contains(e)) {
            return
        }

        if (e == entity) {
            return
        }

        entity.state.collidedEntities.remove(e)
        onExitCollision(e)

        e.logic.unCollide(entity)
    }

    override fun onExitCollision(e: Entity) {

    }

    override fun onMouseDragInteraction() {
        val match = Bomberman.match
        val mouseControllerManager = match.mouseControllerManager
        val player: Entity? = match.player

        val playerCenter = Coordinates.getCenterCoordinatesOfEntity(player)
        val roundedEntityCoords = Coordinates.roundCoordinates(entity.info.position)
        val centerCoordinatesOfEntity = Coordinates.roundCoordinates(playerCenter)
        val mouseCoordinates = Coordinates.roundCoordinates(mouseControllerManager.mouseCoords)

        val isDragInterrupted = mouseControllerManager.isMouseDraggedInteractionInterrupted
        if (isDragInterrupted) {
            return
        }

        val isEntityWithinGrid = roundedEntityCoords.distanceTo(centerCoordinatesOfEntity) <= PitchPanel.GRID_SIZE
        val isDragEntered = mouseControllerManager.isMouseDragInteractionEntered

        if (!isEntityWithinGrid && !isDragEntered) {
            return
        }

        val entitiesOnOccupiedBlock = Coordinates.getEntitiesOnBlock(mouseCoordinates)
        val isBlockOccupied = Coordinates.isBlockOccupied(mouseCoordinates)
        val areEntitiesOnBlock = entitiesOnOccupiedBlock.isNotEmpty() && entitiesOnOccupiedBlock.any { e: Entity ->
            e !== this && e !== player
        }

        if (areEntitiesOnBlock) {
            mouseControllerManager.isMouseDraggedInteractionInterrupted = true
            return
        }

        if (!isBlockOccupied && mouseCoordinates.validate(entity.state.size)) {
            mouseControllerManager.isMouseDragInteractionEntered = true
            mouseControllerManager.setMouseDraggedInteractionOccured(true)
            entity.info.position = Coordinates.roundCoordinates(mouseCoordinates, spawnOffset())
        }
    }
}