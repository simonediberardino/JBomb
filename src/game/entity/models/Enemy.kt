package game.entity.models

import game.Bomberman
import game.entity.player.Player
import game.entity.bomb.AbstractExplosion
import game.entity.player.BomberEntity
import game.events.game.EnemyDespawnedGameEvent
import game.ui.panels.game.PitchPanel

abstract class Enemy(coordinates: Coordinates?) : Character(coordinates) {
    constructor() : this(null) {
        if (Bomberman.getMatch().player != null) {
            coords = Coordinates.randomCoordinatesFromPlayer(size)
        }
    }

    override fun doInteract(e: Entity?) {
        (e as? BomberEntity)?.let { attack(it) }
    }

    override fun onSpawn() {
        super.onSpawn()
        Bomberman.getMatch().increaseEnemiesAlive()
        Bomberman.getMatch().gameTickerObservable?.register(this)
    }

    override fun onDespawn() {
        super.onDespawn()
        val match = Bomberman.getMatch() ?: return
        match.decreaseEnemiesAlive()
        (match.gameTickerObservable ?: return).unregister(this)
        EnemyDespawnedGameEvent().invoke(null)
    }

    override fun getInteractionsEntities(): Set<Class<out Entity>> {
        return HashSet<Class<out Entity>>(listOf(Player::class.java))
    }

    override fun update(arg: Any?) {
        super.update(arg)
        if (arg as Boolean) doUpdate(arg)
    }

    abstract fun doUpdate(gameState: Boolean)

    override val size: Int
        get() = PitchPanel.GRID_SIZE

    override val basePassiveInteractionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf(AbstractExplosion::class.java)
}