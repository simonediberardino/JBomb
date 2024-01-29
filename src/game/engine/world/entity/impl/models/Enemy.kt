package game.engine.world.entity.impl.models

import game.Bomberman
import game.engine.world.entity.impl.player.Player
import game.engine.world.entity.impl.bomb.AbstractExplosion
import game.engine.world.entity.impl.player.BomberEntity
import game.engine.events.game.EnemyDespawnedGameEvent
import game.engine.world.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel

abstract class Enemy : Character {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
        if (Bomberman.getMatch().player != null) {
            info.position = Coordinates.randomCoordinatesFromPlayer(size)
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

    override val interactionEntities: MutableSet<Class<out Entity>>
        get() = hashSetOf(AbstractExplosion::class.java)
}