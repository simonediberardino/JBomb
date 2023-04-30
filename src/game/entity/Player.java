package game.entity;

import game.BomberMan;
import game.controller.Command;
import game.entity.bomb.Bomb;
import game.entity.models.Character;
import game.entity.models.Enemy;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.ui.Paths;

import java.util.*;

import static game.ui.GamePanel.GRID_SIZE;


public class Player extends Character implements Observer {
    private int placedBombs = 0;
    private long lastPlacedBombTime = 0;
    public static final Coordinates spawnOffset = new Coordinates((GRID_SIZE-SIZE)/2 ,PADDING_HEAD);

    public Player(Coordinates coordinates) {
        super(coordinates);
    }

    public Player() {
        super(new Coordinates(0, 0));
    }


    @Override
    protected String getBasePath() {
        return Paths.getEntitiesFolder() + "/player";
    }

    @Override
    public String[] getFrontIcons() {
        return new String[]{
                getBasePath() + "/player_front_0.png",
                getBasePath() + "/player_front_1.png",
                getBasePath() + "/player_front_0.png",
                getBasePath() + "/player_front_2.png"
        };
    }

    @Override
    public String[] getLeftIcons() {
        return new String[]{
                getBasePath() + "/player_left_0.png",
                getBasePath() + "/player_left_1.png",
                getBasePath() + "/player_left_2.png",
                getBasePath() + "/player_left_1.png"
        };
    }

    @Override
    public String[] getBackIcons() {
        //TODO
        return new String[]{
                getBasePath() + "/player_front_1.png",
                getBasePath() + "/player_front_0.png",
                getBasePath() + "/player_front_1.png",
                getBasePath() + "/player_front_2.png"
        };
    }

    @Override
    public String[] getRightIcons() {
        return new String[]{
                getBasePath() + "/player_right_0.png",
                getBasePath() + "/player_right_1.png",
                getBasePath() + "/player_right_2.png",
                getBasePath() + "/player_right_1.png"

        };
    }

    @Override
    public void despawn() {
        super.despawn();
        isAlive = false;
        BomberMan.getInstance().getControllerManager().deleteObserver(this);
    }

    @Override
    public void spawn() {
        super.spawn();
        isAlive = true;
        BomberMan.getInstance().getControllerManager().addObserver(this);
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    public void interact(Entity e) {
        super.interact(e);

        if(e == null) return;

        if (e instanceof Enemy) {
            despawn();
        }
    }

    @Override
    public float getImageRatio(){
        return 0.73f;
    }

    @Override
    protected void onSpawn() {

    }

    @Override
    protected void onDespawn() {

    }

    public void placeBomb() {
        if(placedBombs >= BomberMan.getInstance().getCurrentLevel().getMaxBombs()){
            return;
        }

        if(System.currentTimeMillis() - lastPlacedBombTime < Bomb.PLACE_INTERVAL){
            return;
        }

        lastPlacedBombTime = System.currentTimeMillis();
        placedBombs++;

        Bomb bomb = new Bomb(
                new Coordinates(
                        ((getCoords().getX() + getSize()/2) / GRID_SIZE * GRID_SIZE + Bomb.spawnOffset.getX())
                        ,(getCoords().getY() + getSize()/2) / GRID_SIZE * GRID_SIZE + (Bomb.spawnOffset.getY())
                )
        );

        bomb.setOnExplodeListener(() -> placedBombs--);
        bomb.spawn();
        bomb.trigger();
    }

    @Override
    public void handleAction(Command command) {
        if (BomberMan.getInstance().gameState) {
            super.handleAction(command);

            switch (command) {
                case PLACE_BOMB:
                    placeBomb();
                    ;
                    break;
            }
        }
    }
    @Override
    public Coordinates getSpawnOffset(){
        return spawnOffset;
    }



    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
        handleAction((Command) arg);

    }

}
