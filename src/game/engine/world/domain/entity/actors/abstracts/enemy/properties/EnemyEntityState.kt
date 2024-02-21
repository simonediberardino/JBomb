package game.engine.world.domain.entity.actors.abstracts.enemy.properties

import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.domain.entity.actors.impl.bomb.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import game.engine.world.domain.entity.actors.impl.player.Player

class EnemyEntityState : CharacterEntityState(
        size = PitchPanel.GRID_SIZE,
        interactionEntities = hashSetOf(BomberEntity::class.java, AbstractExplosion::class.java) // Or player?
) {
}