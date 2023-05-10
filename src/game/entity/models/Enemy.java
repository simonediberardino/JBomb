package game.entity.models;


import game.BomberManMatch;
import game.Bomberman;
import game.entity.Player;
import game.events.GameEvent;
import game.models.Coordinates;
import game.ui.panels.game.PitchPanel;

import java.util.*;

public abstract class Enemy extends Character{
    protected boolean canMove = true;

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
        Bomberman.getMatch().getGameTickerObservable().addObserver(this);
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        Bomberman.getMatch().onGameEvent(GameEvent.KILLED_ENEMY, this);
    }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities(){
        return new HashSet<>(Arrays.asList(Player.class));
    }

    @Override
    public final void update(Observable o, Object gameState) {
        super.update(o, gameState);

        if((boolean) gameState)
            update((Boolean) gameState);
    }

    public abstract void update(boolean gameState);
    public int getSize(){
        return PitchPanel.GRID_SIZE;
    }
}
