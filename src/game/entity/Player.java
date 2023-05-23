package game.entity;

import game.Bomberman;
import game.controller.Command;
import game.entity.bomb.Explosion;
import game.entity.models.*;
import game.events.GameEvent;
import game.models.Coordinates;
import game.powerups.PowerUp;
import game.sound.SoundModel;
import game.ui.panels.menus.GameOverPanel;
import game.utils.Paths;

import javax.swing.Timer;
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

        setImageDirection();
        return new String[]{
                getBasePath() + "/player_" + imageDirection.toString().toLowerCase()+"_"+0+".png",
                getBasePath() + "/player_" + imageDirection.toString().toLowerCase()+"_"+1+".png",
                getBasePath() + "/player_" + imageDirection.toString().toLowerCase()+"_"+2+".png",
                getBasePath() + "/player_" + imageDirection.toString().toLowerCase()+"_"+1+".png",

        };
    }


    @Override
    protected void onSpawn() {
        super.onSpawn();
        Bomberman.getMatch().getControllerManager().register(this);
        Bomberman.getBombermanFrame().getMatchPanel().refreshPowerUps(getActivePowerUps());
    }

    @Override
    protected void onEndedDeathAnimation() {
        Timer t = new Timer((int) SHOW_DEATH_PAGE_DELAY_MS, (e) -> showDeathPage());
        t.setRepeats(false);
        t.start();
    }

    private void showDeathPage() {
        Bomberman.destroyLevel();
        Bomberman.show(GameOverPanel.class);
    }

    @Override
    protected void onEliminated() {
        super.onEliminated();
        Bomberman.getMatch().onGameEvent(GameEvent.DEATH, null);
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
        super.update(arg);
        handleAction((Command) arg);
    }

    @Override
    protected SoundModel getDeathSound() {
        return SoundModel.PLAYER_DEATH;
    }

    @Override
    protected Set<Class<? extends Entity>> getBasePassiveInteractionEntities() {
        return new HashSet<>(Arrays.asList(Explosion.class, Enemy.class, PowerUp.class));
    }
}
