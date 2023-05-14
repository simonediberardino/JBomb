package game.entity;

import game.Bomberman;
import game.controller.Command;
import game.entity.models.*;
import game.events.GameEvent;
import game.models.Coordinates;
import game.ui.panels.menus.GameOverPanel;
import game.utils.Paths;

import java.util.*;

import static game.ui.panels.game.PitchPanel.GRID_SIZE;


public class Player extends BomberEntity {
    public static final Coordinates SPAWN_OFFSET = new Coordinates((GRID_SIZE-SIZE)/2 ,0);
    public Set<Class<? extends Entity>> interactionEntities = new HashSet<>();

    public Player(Coordinates coordinates) {
        super(coordinates);
        heightToHitboxSizeRatio = 0.733f;
    }

    @Override
    protected void doInteract(Entity e) {
        System.out.println(e + " with " + this);
    }

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
    public String[] getBaseSkins() {
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
        Bomberman.getMatch().getControllerManager().register(this);
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();
        Bomberman.getMatch().getControllerManager().unregister(this);
    }

    @Override
    protected void onDie() {
        super.onDie();
        Bomberman.getMatch().onGameEvent(GameEvent.DEATH, null);

/*        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Bomberman.destroyLevel();
            Bomberman.show(GameOverPanel.class);
        }).start();*/

    }

    @Override
    public void handleAction(Command command) {
        if (!Bomberman.getMatch().getGameState()) {
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

    // Handle the command entered by the player;
    @Override
    public void update(Object arg) {
        handleAction((Command) arg);
    }
}
