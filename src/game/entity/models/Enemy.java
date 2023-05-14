package game.entity.models;


import game.BomberManMatch;
import game.Bomberman;
import game.entity.Player;
import game.events.GameEvent;
import game.models.Coordinates;
import game.ui.panels.game.PitchPanel;

import java.util.*;

public abstract class Enemy extends Character{


    public Enemy(){
        super(Coordinates.randomCoordinatesFromPlayer());
    }

    public Enemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    protected void doInteract(Entity e) {
        if (e instanceof BomberEntity) {
            attack(e);
        }
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        Bomberman.getMatch().getGameTickerObservable().register(this);
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        Bomberman.getMatch().getGameTickerObservable().unregister(this);
        Bomberman.getMatch().onGameEvent(GameEvent.KILLED_ENEMY, this);
    }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities(){
        return new HashSet<>(Collections.singletonList(Player.class));
    }

    public final void update(Object gameState) {
        super.update(gameState);

        if((boolean) gameState)
            doUpdate((Boolean) gameState);
    }

    public abstract void doUpdate(boolean gameState);

    public int getSize() {
        return PitchPanel.GRID_SIZE;
    }
}
