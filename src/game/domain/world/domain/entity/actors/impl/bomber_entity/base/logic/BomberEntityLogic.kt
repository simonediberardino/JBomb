package game.domain.world.domain.entity.actors.impl.bomber_entity.base.logic

import game.JBomb
import game.domain.level.behavior.PlayerDeathBehavior
import game.domain.tasks.observer.Observable2
import game.domain.world.domain.entity.actors.abstracts.base.Entity
import game.domain.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.domain.world.domain.entity.geo.Coordinates
import game.domain.world.domain.entity.items.BombItem
import game.domain.world.domain.entity.pickups.powerups.base.PowerUp
import game.network.events.forward.CustomUpdateInfoEventForwarder
import game.network.events.forward.UpdateInfoEventForwarder
import game.network.models.HttpMessageTypes
import game.presentation.ui.panels.game.PitchPanel
import game.utils.dev.Log

open class BomberEntityLogic(override val entity: BomberEntity) : CharacterEntityLogic(entity = entity), IBomberEntityLogic {
    override fun doInteract(e: Entity?) {
        e?.logic?.interact(entity)
    }

    override fun onSpawn() {
        super.onSpawn()
        // Give the current entity a BombItem when it is spawned in the match.
        JBomb.match.currentLevel.info.defaultWeapon?.let { JBomb.match.give(entity, it) }
    }

    override fun onDespawn() {
        super.onDespawn()
        if (entity.state.eliminated) {
            PlayerDeathBehavior().invoke()
        }
        entity.state.activePowerUps.clear()
    }

    override fun onAdded() {
        super.onAdded()
        JBomb.match.players.add(entity)
    }

    override fun onRemoved() {
        super.onRemoved()
        JBomb.match.players.removeIf { e -> e.info.id == entity.info.id}
    }

    override fun onMove(coordinates: Coordinates) {
        super.onMove(coordinates)
        // Handle interactions with bombs after the entity has moved.
        handleInteractionWithBombs()
    }

    override fun spawnOffset(): Coordinates = BomberEntity.SPAWN_OFFSET

    // Calculates the minimum distance to any bomb from the current entity. If there are no bombs, returns 0.0.
    private fun minDistanceToBomb(): Double = JBomb.match.bombs.minOfOrNull { bomb ->
        bomb.info.position.distanceTo(entity.info.position)
    } ?: 0.0

    // Handles the interaction of the entity with bombs, determining whether it should be solid based on bomb proximity.
    private fun handleInteractionWithBombs() {
        // If bombs are forced solid or bombs are already solid, no further action is needed.
        if (entity.state.bombsSolid || entity.state.forceBombsSolid) {
            return
        }

        // If the minimum distance to a bomb is greater than half the grid size, make bombs solid.
        if (minDistanceToBomb() > PitchPanel.GRID_SIZE / 2f) {
            entity.state.bombsSolid = true
        }
    }

    // Checks if the entity is interactable with a mouse click based on the entity's class.
    override fun isMouseClickInteractable(e: Entity): Boolean = entity.state.entitiesClassesMouseClick.any {
        it.isInstance(e)
    }

    // Checks if the entity is interactable with a mouse drag based on the entity's class.
    override fun isMouseDragInteractable(e: Entity): Boolean = entity.state.entitiesClassesMouseDrag.any {
        it.isInstance(e)
    }

    // Adds the specified class to the list of entities interactable with a mouse click.
    override fun addClassInteractWithMouseClick(cls: Class<out Entity>) {
        entity.state.entitiesClassesMouseClick.add(cls)
    }

    // Adds the specified class to the list of entities interactable with a mouse drag.
    override fun addClassInteractWithMouseDrag(cls: Class<out Entity>) {
        entity.state.entitiesClassesMouseDrag.add(cls)
    }

    // Removes the specified class from the list of entities interactable with a mouse click.
    override fun removeClassInteractWithMouseClick(cls: Class<out Entity>) {
        entity.state.entitiesClassesMouseClick.remove(cls)
    }

    // Removes the specified class from the list of entities interactable with a mouse drag.
    override fun removeClassInteractWithDrag(cls: Class<out Entity>) {
        entity.state.entitiesClassesMouseDrag.remove(cls)
    }

    // Removes an active power-up from the list of active power-ups.
    override fun removeActivePowerUp(p: PowerUp?) {
        entity.state.activePowerUps.removeIf { e: Class<out PowerUp> -> e.isInstance(p) }
    }

    override fun onPowerupApply(powerUp: PowerUp) {}

    override fun onUpdateHealth(health: Int) {
        super.onUpdateHealth(health)
    }

    override fun observerUpdate(arg: Observable2.ObserverParam) {}
}