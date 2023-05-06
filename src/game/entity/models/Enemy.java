package game.entity.models;


import game.BomberMan;
import game.entity.Player;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.models.Direction;
import game.panels.PitchPanel;

import java.util.*;

public abstract class Enemy extends Character{


    protected boolean canMove = true;

    public Enemy(){
        super(null);
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
        BomberMan.getInstance().getGameTickerObservable().addObserver(this);
    }



    @Override
    public float getImageRatio(){
        return 1f;
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
