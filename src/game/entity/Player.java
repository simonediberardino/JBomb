package game.entity;

import game.BomberMan;
import game.controller.Command;
import game.controller.ControllerManager;
import game.entity.models.Character;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;

import java.util.*;

import static game.models.Direction.*;
import static game.ui.GamePanel.GRID_SIZE;


public class Player extends Character implements Observer {
    private int placedBombs = 0;
    private long lastPlacedBombTime = 0;

    @Override
    public String[] getFrontIcons() {
        return new String[]{
                "assets/player/player_front_0.png",
                "assets/player/player_front_1.png",
                "assets/player/player_front_0.png",
                "assets/player/player_front_2.png"
        };
    }

    @Override
    public String[] getLeftIcons() {
        //TODO
        return new String[]{
                "assets/player/player_left_0.png",
                "assets/player/player_left_1.png",
                "assets/player/player_left_2.png",
                "assets/player/player_left_1.png"
        };
    }

    @Override
    public String[] getBackIcons() {
        //TODO
        return new String[]{
                "assets/player/player_front_1.png",
                "assets/player/player_front_0.png",
                "assets/player/player_front_1.png",
                "assets/player/player_front_2.png"
        };
    }

    @Override
    public String[] getRightIcons() {
        //TODO
        return new String[]{
                "assets/player/player_right_0.png",
                "assets/player/player_right_1.png",
                "assets/player/player_right_2.png",
                "assets/player/player_right_1.png"

        };
    }

    public Player(Coordinates coordinates) {
        super(coordinates);
    }

    public Player() {
        super(new Coordinates(0, 0));
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
        BomberMan.getInstance().getControllerManager().deleteObservers();
        BomberMan.getInstance().getControllerManager().addObserver(this);
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    public void interact(Entity e) {
        if (e instanceof Enemy || e instanceof Explosion) {
            despawn();
        }
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

        placedBombs++;

        Bomb bomb = new Bomb(
                new Coordinates(
                        (getCoords().getX() + getSize()/2) / GRID_SIZE * GRID_SIZE + ((GRID_SIZE - Bomb.size) / 2)
                        ,(getCoords().getY() + getSize()/2) / GRID_SIZE * GRID_SIZE + ((GRID_SIZE - Bomb.size) / 2)
                )
        );

        bomb.setOnExplodeListener(() -> placedBombs--);
        bomb.spawn();
        bomb.trigger();
    }

    @Override
    public void handleAction(Command command) {
        super.handleAction(command);

        switch (command) {
            case PLACE_BOMB: placeBomb(); break;
        }
    }



    @Override
    public void update(Observable o, Object arg) {
        handleAction((Command) arg);
    }

}
