package game.events.game

import game.Bomberman
import game.events.models.GameEvent


class KilledEnemyEvent : GameEvent {
    override fun invoke(arg: Any?) {
        Bomberman.getMatch().currentLevel.onKilledEnemy()
    }
}