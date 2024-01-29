package game.engine.world.entity.impl.models

interface Explosive {
    val explosionObstacles: Set<Class<out Entity>>
    fun isObstacleOfExplosion(e: Entity?): Boolean {
        return e == null || explosionObstacles.stream().anyMatch { c: Class<out Entity> -> c.isInstance(e) }
    }

    val explosionInteractionEntities: Set<Class<out Entity>>
    val maxExplosionDistance: Int
}