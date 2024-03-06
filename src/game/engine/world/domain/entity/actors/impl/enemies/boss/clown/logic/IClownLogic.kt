package game.engine.world.domain.entity.actors.impl.enemies.boss.clown.logic

import game.engine.sound.AudioManager
import game.engine.sound.SoundModel
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.hat.Hat
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.engine.world.domain.entity.actors.impl.enemies.npcs.clown_nose.ClownNose
import game.engine.world.domain.entity.actors.impl.explosion.ConfettiExplosion
import game.engine.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.world.domain.entity.geo.Direction
import game.engine.world.domain.entity.geo.EnhancedDirection
import game.utils.Utility
import java.util.*

interface IClownLogic {
    fun spawnOrbs()
    fun spawnEnhancedOrbs()
    fun calculateExplosionOffsets(d: Direction): IntArray
    fun spawnExplosion()
    fun throwHat()
    fun checkAndSpawnExplosion()
    fun checkAndSpawnOrbs()
    fun checkAndThrowHat()

}