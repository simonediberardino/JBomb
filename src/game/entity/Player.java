package game.entity;

import game.Bomberman;
import game.entity.bomb.AbstractExplosion;
import game.hardwareinput.Command;
import game.entity.models.*;
import game.events.DeathGameEvent;
import game.entity.models.Coordinates;
import game.powerups.PowerUp;
import game.sound.SoundModel;
import game.ui.panels.menus.GameOverPanel;
import game.utils.Paths;

import javax.swing.Timer;
import java.util.*;

import static game.ui.panels.game.PitchPanel.GRID_SIZE;


public class Player extends BomberEntity {
    public static final Coordinates SPAWN_OFFSET = new Coordinates((GRID_SIZE - SIZE)/2 ,GRID_SIZE-SIZE);
    private final Set<Class<? extends Entity>> interactionEntities = new HashSet<>();

     public Player(Coordinates coordinates) {
        super(coordinates);
        this.hitboxSizeToHeightRatio = 0.733f;
        Bomberman.getMatch().getInventoryElementControllerBombs().setNumItems(getCurrentBombs());
    }

    @Override
    protected void doInteract(Entity e) {
    }

    @Override
    public Set<Class<? extends Entity>> getInteractionsEntities() {
        return this.interactionEntities;
    }



    @Override
    protected String getBasePath() {
        return Paths.getEntitiesFolder() + "/player";
    }

    @Override
    public String[] getCharacterOrientedImages() {
        return new String[]{
                String.format("%s/player_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 0),
                String.format("%s/player_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 1),
                String.format("%s/player_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 2),
                String.format("%s/player_%s_%d.png", getBasePath(), imageDirection.toString().toLowerCase(), 1),
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
        Bomberman.showActivity(GameOverPanel.class);
    }

    @Override
    protected void onEliminated() {
        super.onEliminated();
        new DeathGameEvent().invoke(null);
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
        return new HashSet<>(Arrays.asList(AbstractExplosion.class, Enemy.class, PowerUp.class));
    }

    @Override
    protected SoundModel getStepSound() {
        return SoundModel.STEP_SOUND;
    }
}
