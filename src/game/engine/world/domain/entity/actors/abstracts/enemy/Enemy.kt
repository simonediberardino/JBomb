package game.engine.world.domain.entity.actors.abstracts.enemy

import game.Bomberman
import game.engine.world.domain.entity.actors.impl.player.Player
import game.engine.world.domain.entity.actors.impl.bomb.AbstractExplosion
import game.engine.world.domain.entity.actors.impl.player.BomberEntity
import game.engine.events.game.EnemyDespawnedGameEvent
import game.engine.world.domain.entity.geo.Coordinates
import game.engine.ui.panels.game.PitchPanel
import game.engine.world.domain.entity.actors.abstracts.base.Entity
import game.engine.world.domain.entity.actors.abstracts.character.Character
import game.engine.world.domain.entity.actors.abstracts.character.logic.CharacterEntityLogic
import game.engine.world.domain.entity.actors.abstracts.character.logic.ICharacterEntityLogic
import game.engine.world.domain.entity.actors.abstracts.character.properties.CharacterEntityState
import game.engine.world.domain.entity.actors.abstracts.enemy.logic.EnemyEntityLogic
import game.engine.world.domain.entity.actors.abstracts.enemy.properties.EnemyEntityState
import game.engine.world.domain.entity.actors.abstracts.entity_interactable.logic.IEntityInteractableLogic

abstract class Enemy : Character {
    constructor(id: Long) : super(id)
    constructor(coordinates: Coordinates?) : super(coordinates)

    constructor() : this(null) {
        if (Bomberman.getMatch().player != null) {
            info.position = Coordinates.randomCoordinatesFromPlayer(size)
        }
    }

    override val state: EnemyEntityState = EnemyEntityState()
    abstract override val logic: EnemyEntityLogic

    override fun update(arg: Any?) {
        super.update(arg)
        if (arg as Boolean) logic.onGameTick(arg)
    }
}