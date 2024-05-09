package game.domain.world.domain.entity.actors.abstracts.base.logic

import game.Bomberman
import game.Bomberman.match
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.base.IEntityLogic
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.domain.world.domain.entity.actors.impl.models.State
import game.domain.world.domain.entity.geo.Coordinates
import game.network.events.forward.CollideEventForwarder
import game.network.events.forward.DespawnEntityEventForwarder
import game.network.events.forward.SpawnEntityEventForwarder
import game.presentation.ui.panels.game.PitchPanel
import game.utils.Utility
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
        match.removeEntity(entity)
    }

    override fun spawn() {
        spawn(forceSpawn = false, forceCentering = true)
    }

    override fun spawn(coordinates: Coordinates) {
        entity.info.position = coordinates
        spawn()
    }

    final override fun spawn(forceSpawn: Boolean, forceCentering: Boolean) {
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
            match.addEntity(entity)
            entity.state.spawnTime = System.currentTimeMillis()
            onSpawn()
        }
    }

    override fun onAdded() {}
    override fun onRemoved() {
        match.gameTickerObservable?.unregister(entity)
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


    final override fun mouseClickedInteraction() {
        val player = match.player ?: return

        if (!player.logic.isAlive())
            return

        if (player.logic.isMouseClickInteractable(entity)) {
            onMouseClickInteraction()
        }
    }

    final override fun mouseDraggedInteraction() {
        val player = match.player ?: return

        if (!player.logic.isAlive())
            return

        if (player.logic.isMouseDragInteractable(entity)) {
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

    override fun onMouseClickInteraction() {
        val match = Bomberman.match
        val player = match.player

        val centerCoordinatesOfEntity = Coordinates.roundCoordinates(Coordinates.getCenterCoordinatesOfEntity(player))

        if (entity.info.position.distanceTo(centerCoordinatesOfEntity) <= PitchPanel.GRID_SIZE) {
            eliminated()
        }
    }

    final override fun collide(e: Entity) {
        if (entity == e || entity.state.collidedEntities.contains(e)) {
            return
        }

        CollideEventForwarder().invoke(e.toEntityNetwork(), entity.toEntityNetwork())
        entity.state.collidedEntities.add(e)

        e.logic.passiveCollide(entity)
        onCollision(e)
    }

    final override fun passiveCollide(e: Entity) {
        if (entity == e || entity.state.passiveCollidedEntities.contains(e)) {
            return
        }

        entity.state.passiveCollidedEntities.add(e)
        onCollision(e)
    }

    final override fun unCollideAll() {
        try {
            entity.state.collidedEntities.forEach {
                entity.logic.unCollide(it)
                it.logic.unPassivecollide(entity)
            }

            entity.state.passiveCollidedEntities.forEach {
                entity.logic.unPassivecollide(it)
                it.logic.unCollide(entity)
            }
        } catch (_: Exception) {
        }
    }

    override fun onCollision(e: Entity) {

    }

    final override fun unCollide(e: Entity) {
        if (entity == e || !entity.state.collidedEntities.contains(e)) {
            return
        }

        entity.state.collidedEntities.remove(e)
        onExitCollision(e)

        e.logic.unCollide(entity)
    }

    final override fun unPassivecollide(e: Entity) {
        if (entity == e || !entity.state.passiveCollidedEntities.contains(e)) {
            return
        }

        entity.state.passiveCollidedEntities.remove(e)
        onExitCollision(e)
    }

    override fun onExitCollision(e: Entity) {

    }

    override fun onMouseDragInteraction() {
        val player = match.player ?: return

        val mouseControllerManager = match.mouseControllerManager
        val mouseCoordinates = Coordinates.roundCoordinates(mouseControllerManager.mouseCoords)
        val isBlockOccupied = Coordinates.isBlockOccupied(mouseCoordinates)
        val roundedEntityCoords = Coordinates.roundCoordinates(entity.info.position)
        val playerCenter = Coordinates.getCenterCoordinatesOfEntity(player)
        val roundedPlayerCoords = Coordinates.roundCoordinates(playerCenter)

        if (isBlockOccupied)
            return

        val isPlayerNear = roundedEntityCoords.distanceTo(roundedPlayerCoords) <= PitchPanel.GRID_SIZE

        if (!isPlayerNear)
            return

        entity.info.position = Coordinates.roundCoordinates(mouseCoordinates, spawnOffset())
    }
}