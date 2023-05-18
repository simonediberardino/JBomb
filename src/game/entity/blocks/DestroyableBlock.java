package game.entity.blocks;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.Player;
import game.entity.bomb.Explosion;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.models.Coordinates;
import game.powerups.PowerUp;
import game.powerups.portal.EndLevelPortal;
import game.ui.panels.game.PitchPanel;
import game.utils.Utility;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


public class DestroyableBlock extends Block {
    private static final int POWER_UP_SPAWN_CHANGE = 33;
    private Class<? extends PowerUp> powerUpClass;

    public DestroyableBlock(Coordinates coordinates, Class<PowerUp> powerUpClass){
        super(coordinates);
        this.powerUpClass = powerUpClass;

    }

    public DestroyableBlock(Coordinates coordinates) {
        this(coordinates, null);
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    protected void doInteract(Entity e) {
    }

    @Override
    public BufferedImage getImage(){
        return loadAndSetImage(Bomberman.getMatch().getCurrentLevel().getDestroyableBlock());
    }

    @Override
    protected void onDespawn() {
        super.onDespawn();

        if (powerUpClass == null) {
            return;
        }

        int spawnPercentage = powerUpClass == EndLevelPortal.class ? 100 : POWER_UP_SPAWN_CHANGE;

        Utility.runPercentage(spawnPercentage, () -> {
            PowerUp powerUp;
            try {
                powerUp = powerUpClass.getConstructor(Coordinates.class).newInstance(getCoords());
                powerUp.spawn(true, true);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

    }

    public Class<? extends PowerUp> getPowerUpClass() {
        return powerUpClass;
    }

    public void setPowerUpClass(Class<? extends PowerUp> powerUpClass) {
        this.powerUpClass = powerUpClass;
    }

    @Override
    public void onMouseClick() {
        Player player = Bomberman.getMatch().getPlayer();
        Coordinates centerCoordinatesOfEntity = Coordinates.getCenterCoordinatesOfEntity(player);

        // If the player is near the block -> Destroy;
        if (!(getCoords().distanceTo(Coordinates.roundCoordinates(centerCoordinatesOfEntity)) <= PitchPanel.GRID_SIZE)) {
            return;
        }

        despawn();
    }

    @Override
    public void setPassiveInteractionEntities() {
        passiveInteractionEntities = new HashSet<>(Collections.singletonList(Explosion.class));
    }
}
