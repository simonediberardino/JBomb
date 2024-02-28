package game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.properties

import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityProperties
import game.engine.world.domain.entity.actors.impl.enemies.boss.clown.orb.Orb
import game.engine.world.types.EntityTypes
import game.values.DrawPriority

open class OrbEntityProperties(
        types: EntityTypes,
        obstacles: Set<Class<out Entity?>?> = Orb.DEFAULT.OBSTACLES,
        drawPriority: DrawPriority = Orb.DEFAULT.DRAW_PRIORITY
) : CharacterEntityProperties(
        drawPriority = drawPriority,
        types = types,
        obstacles = obstacles
)