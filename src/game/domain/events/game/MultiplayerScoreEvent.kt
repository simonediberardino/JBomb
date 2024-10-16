package game.domain.events.game

import game.JBomb
import game.domain.events.models.GameEvent
import game.domain.world.domain.entity.actors.impl.bomber_entity.base.BomberEntity
import game.localization.Localization
import game.utils.ui.ToastUtils

class MultiplayerScoreEvent(val attacker: BomberEntity, private val score: Int): GameEvent {
    override fun invoke(vararg arg: Any?) {
        attacker.state.score += score

        if (attacker == JBomb.match.player) {
            ScoreGameEvent().invoke(100)
        }
    }
}