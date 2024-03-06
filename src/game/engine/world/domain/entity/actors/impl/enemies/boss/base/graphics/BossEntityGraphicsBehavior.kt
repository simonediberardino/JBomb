package game.engine.world.domain.entity.actors.impl.enemies.boss.base.graphics

import game.engine.world.domain.entity.actors.abstracts.base.graphics.DefaultEntityGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.graphics.CharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.abstracts.character.graphics.ICharacterGraphicsBehavior
import game.engine.world.domain.entity.actors.impl.enemies.boss.base.Boss

open class BossEntityGraphicsBehavior(override val entity: Boss) : CharacterGraphicsBehavior(entity = entity), IBossEntityGraphicsBehavior {
    override fun getImageFromRageStatus(): String {
        return entity.image.imagePath
    }
}