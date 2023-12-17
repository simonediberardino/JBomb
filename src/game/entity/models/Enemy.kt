package game.entity.models

import game.Bomberman
import game.entity.Player
import game.entity.bomb.AbstractExplosion
import game.events.game.EnemyDespawnedGameEvent
import game.ui.panels.game.PitchPanel

abstract class Enemy(coordinates: Coordinates?) : Character(coordinates) {
    constructor() : this(null) {
        coords = Coordinates.randomCoordinatesFromPlayer(size)
    }

    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let { attack(it) }
    }

    override fun onSpawn() {
        super.onSpawn()
        Bomberman.getMatch().increaseEnemiesAlive()
        Bomberman.getMatch().gameTickerObservable.register(this)
    }

    override fun onDespawn() {
        super.onDespawn()
        val match = Bomberman.getMatch() ?: return
        match.decreaseEnemiesAlive()
        match.gameTickerObservable.unregister(this)
        EnemyDespawnedGameEvent().invoke(null)
    }

    override fun getInteractionsEntities(): Set<Class<out Entity>> {
        return HashSet<Class<out Entity>>(listOf(Player::class.java))
    }

    override fun update(gameState: Any?) {
        super.update(gameState)
        if (gameState as Boolean) doUpdate(gameState)
    }

    abstract fun doUpdate(gameState: Boolean)
    override fun getSize(): Int {
        return PitchPanel.GRID_SIZE
    }

    override fun getBasePassiveInteractionEntities(): Set<Class<out Entity>> {
        return HashSet<Class<out Entity>>(listOf(AbstractExplosion::class.java))
    }
}