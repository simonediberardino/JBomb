package game.domain.world.domain.entity.actors.impl.enemies.boss.clown.logic

import game.domain.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.domain.world.domain.entity.geo.Direction

interface IClownLogic {
    fun spawnOrbs()
    fun spawnEnhancedOrbs()
    fun calculateExplosionOffsets(d: Direction): IntArray
    fun spawnExplosion()
    fun throwHat()
    fun checkAndSpawnExplosion()
    fun checkAndSpawnOrbs()
    fun checkAndThrowHat()
    fun pickupHat(hat: Hat)
}