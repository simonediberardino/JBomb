package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity

class MultiplayerKillsEvent(val attacker: BomberEntity): GameEvent {
    override fun invoke(vararg arg: Any?) {
        attacker.state.kills += 1

        if (attacker == JBomb.match.player) {
            JBomb.match.currentLevel.eventHandler.onKillsGameEventLocal(attacker.state.kills)
        }
    }
}