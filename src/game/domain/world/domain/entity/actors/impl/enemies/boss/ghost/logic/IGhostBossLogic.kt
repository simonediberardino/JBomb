package game.domain.world.domain.entity.actors.impl.enemies.boss.ghost.logic

interface IGhostBossLogic {
    fun attackAnimationAndSoundFX()
    fun disappearAndReappear()
    fun spawnGhosts(n: Int)
    fun performLightsAnimation()
}