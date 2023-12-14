package game.events.game;

import game.Bomberman;
import game.events.models.GameEvent;

public class EnemyDespawnedGameEvent implements GameEvent {
    @Override
    public void invoke(Object arg) {
        if (!Bomberman.getMatch().getGameState()) return;

        Bomberman.getMatch().getCurrentLevel().onEnemyDespawned();
    }
}
