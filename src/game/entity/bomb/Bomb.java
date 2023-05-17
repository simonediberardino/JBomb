package game.entity.bomb;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.Player;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.HardBlock;
import game.entity.models.*;
import game.models.Coordinates;
import game.models.Direction;
import game.utils.Paths;
import game.ui.panels.game.PitchPanel;

import java.awt.image.BufferedImage;
import java.util.*;

public class Bomb extends Block implements Explosive {
    public static final int BOMB_SIZE = PitchPanel.COMMON_DIVISOR * 2;
    public static final long PLACE_INTERVAL = 1000;
    private static final int EXPLODE_TIMER = 5000;
    private Runnable onExplodeCallback;
    private BomberEntity caller;

    public Bomb(BomberEntity entity) {
        super(Coordinates.getCenterCoordinatesOfEntity(entity));
        this.caller = entity;
    }

    @Override
    protected String getBasePath() {
        return Paths.getAssetsFolder() + "/bomb/";
    }

    @Override
    public BufferedImage getImage() {
        final int imagesCount = 3;

        String[] images = new String[imagesCount];

        for (int i = 0; i < images.length; i++) {
            images[i] = String.format("%sbomb_%d.png", getBasePath(), i);
        }

        if (System.currentTimeMillis() - lastImageUpdate < getImageRefreshRate()) {
            return this.image;
        }

        BufferedImage img = loadAndSetImage(images[lastImageIndex]);

        lastImageIndex++;
        if (lastImageIndex >= images.length) {
            lastImageIndex = 0;
        }

        return img;
    }

    public void setOnExplodeListener(Runnable runnable) {
        onExplodeCallback = runnable;
    }

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    @Override
    protected void doInteract(Entity e) {
    }

    public void explode() {
        if (!isSpawned()) {
            return;
        }

        despawn();

        new Explosion(getCoords(), Direction.UP, this);
        new Explosion(getCoords(), Direction.RIGHT, this);
        new Explosion(getCoords(), Direction.DOWN, this);
        new Explosion(getCoords(), Direction.LEFT, this);

        if (onExplodeCallback != null) onExplodeCallback.run();
    }

    public void trigger() {
        TimerTask explodeTask = new TimerTask() {
            public void run() {
                explode();
            }
        };

        Timer timer = new Timer();
        timer.schedule(explodeTask, EXPLODE_TIMER);
    }


    @Override
    public int getSize() {
        return BOMB_SIZE;
    }

    @Override
    public List<Class<? extends Entity>> getExplosionObstacles() {
        return Arrays.asList(HardBlock.class, DestroyableBlock.class);
    }

    @Override
    public boolean isObstacleOfExplosion(Entity e) {
        return (e == null) || (getExplosionObstacles().stream().anyMatch(c -> c.isInstance(e)));
    }

    @Override
    public List<Class<? extends Entity>> getExplosionInteractionEntities() {
        return Arrays.asList(DestroyableBlock.class, Enemy.class, Player.class, Bomb.class);
    }

    @Override
    public boolean canExplosionInteractWith(Entity e) {
        return ((e == null) || (getExplosionInteractionEntities().stream().anyMatch(c -> c.isInstance(e))) && !e.isImmune());
    }

    @Override
    public int getMaxExplosionDistance() {
        return caller != null ? caller.getCurrExplosionLength() : Bomberman.getMatch().getCurrentLevel().getExplosionLength();
    }
    @Override
    public void onMouseClick(){
        explode();
    }

    @Override
    public void destroy(){
        explode();
    }
}

