package game.entity;

import game.BomberMan;
import game.controller.Command;
import game.entity.models.*;
import game.models.Coordinates;
import game.utils.Paths;

import java.util.*;

import static game.panels.PitchPanel.GRID_SIZE;


public class Player extends BomberEntity {
    public static final Coordinates SPAWN_OFFSET = new Coordinates((GRID_SIZE-SIZE)/2 ,0);
    public Set<Class<? extends Entity>> interactionEntities = new HashSet<>();

    public Player(Coordinates coordinates) {
        super(coordinates);
        heightToHitboxSizeRatio = 0.733f;
    }

    @Override
    protected void doInteract(Entity e) { }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities() {
        return this.interactionEntities;
    }

    private Player() {
        super(null);
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
    protected void onSpawn() {
        super.onSpawn();
        isAlive = true;
        BomberMan.getInstance().getControllerManager().addObserver(this);
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        isAlive = false;
        BomberMan.getInstance().getControllerManager().deleteObserver(this);
    }



    @Override
    public void handleAction(Command command) {
        if (!BomberMan.getInstance().getGameState()) {
            return;
        }
        super.handleAction(command);

        switch (command) {
            case PLACE_BOMB: placeBomb(); break;
        }
    }

    @Override
    public Coordinates getSpawnOffset(){
        return SPAWN_OFFSET;
    }

    @Override
    public void update(Observable o, Object arg) {
        handleAction((Command) arg);
    }
}
