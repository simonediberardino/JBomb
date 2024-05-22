package game.domain.world.domain.entity.actors.impl.explosion.handler

import game.audio.AudioManager
import game.audio.SoundModel
import game.domain.world.domain.entity.actors.impl.explosion.abstractexpl.AbstractExplosion

class ExplosionHandler private constructor() {
    companion object {
        val instance: ExplosionHandler by lazy { ExplosionHandler() }
    }

    fun process(sound: SoundModel = SoundModel.EXPLOSION, explosions: () -> List<AbstractExplosion>) {
        AudioManager.instance.play(sound)

        explosions.invoke()
    }
}