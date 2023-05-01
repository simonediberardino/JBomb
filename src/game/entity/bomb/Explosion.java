package game.entity.bomb;

import game.BomberMan;
import game.entity.Player;
import game.entity.blocks.DestroyableBlock;
import game.entity.enemies.TankEnemy;
import game.entity.models.*;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.GamePanel;
import game.ui.Paths;

import java.awt.image.BufferedImage;

import static game.ui.Utility.loadImage;

/**
 * Represents an explosion that can interact with other entities in the game.
 */
public class Explosion extends InteractiveEntities {
    // The distance from the bomb where the explosion was created.
    public final int distanceFromExplosive;

    // The maximum distance from the bomb that the explosion can travel.
    private final int maxDistance;
    private final int BOMB_STATES = 3;
    private boolean canExpand;

    // The direction of the explosion.
    public final Direction direction;
    private boolean appearing = true;
    private int explosionState = 1;
    private long lastRefresh = 0;
    private final int refreshRate = 100;
    private final Explosive explosive;
    public static final int SIZE = GamePanel.COMMON_DIVISOR*4;

    /**
     * Constructs a new explosion.
     *
     * @param coordinates The starting coordinates of the explosion.
     * @param direction The direction of the explosion.
     */
    public Explosion(Coordinates coordinates, Direction direction,Explosive explosive) {
        this(coordinates, direction, 0, explosive);
    }
    public Explosion(Coordinates coordinates, Direction direction, int distanceFromExplosive, Explosive explosive){
        this(coordinates,direction, distanceFromExplosive,explosive,true);

    }
    /**
     * Constructs a new explosion.
     *
     * @param coordinates The starting coordinates of the explosion.
     * @param direction The direction of the explosion.
     * @param distanceFromExplosive The distance from the bomb where the explosion was created.
     */
    public Explosion(Coordinates coordinates, Direction direction, int distanceFromExplosive, Explosive explosive, boolean canExpand) {
        super(coordinates);

        this.direction = direction;
        this.distanceFromExplosive = distanceFromExplosive;
        this.maxDistance = BomberMan.getInstance().getCurrentLevel().getExplosionLength();
        this.explosive = explosive;
        this.canExpand = canExpand;
        // Add the explosion entity to the game.
        BomberMan.getInstance().addEntity(this);

        // Move or interact with other entities in the game based on the explosion's direction.
        if(getCanExpand())
            moveOrInteract(direction, getSize(), true);
    }

    @Override
    protected String getBasePath() {
        return Paths.getAssetsFolder() + "/bomb/";
    }

    @Override
    protected void onSpawn() {

    }

    @Override
    protected void onDespawn() {

    }

    /**
     * Interacts with another entity in the game.
     *
     * @param e The entity to interact with.
     */
    @Override
    public void interact(Entity e) {
        if (canInteractWith(e)) {
            if (e == null) return;

            if (e instanceof Player) {
                e.despawn();
            } else if (e instanceof Enemy) {
                e.despawn();
            } else if (e instanceof Bomb) {
                ((Bomb) e).explode();
            } else if (e instanceof DestroyableBlock) {
                e.despawn();
            }
        }
    }

    /**
     * Returns the size of the explosion.
     *
     * @return The size of the explosion.
     */
    @Override
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the coordinates of the explosion and creates new explosions based on its distance from the bomb.
     *
     * @param coordinates The new coordinates of the explosion.
     */
    @Override
    public void setCoords(Coordinates coordinates) {
        if (canExpand) {
            super.setCoords(getCoords());

            new Explosion(coordinates, direction, distanceFromExplosive + 1, explosive);
        }
    }

    /**
     * Returns the image of the explosion.
     *
     * @return The image of the explosion.
     */
    @Override
    public BufferedImage getImage() {
        if(distanceFromExplosive == 0){
            return loadImage(String.format("%sflame_central" + getState() + ".png", getBasePath()));
        }

        String imageFileName;
        String isLast = "";
        if (!canExpand) {
            isLast = "_last";
        }
        imageFileName = "flame_" + direction.toString().toLowerCase();

        // Load and set the image of the flame.
        String imagePath = String.format("%s%s%s%s.png", getBasePath(), imageFileName,isLast, getState());
        return loadAndSetImage(imagePath);
    }

    public int getState(){
        if (explosionState == 0 && !appearing){
            despawn();
            appearing = true;
            return 0;
        }

        if (explosionState == BOMB_STATES) appearing = false;

        int appearingConstant = !appearing ? -1 : 1;

        int prevState = explosionState;
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastRefresh >= refreshRate ) {
            explosionState += appearingConstant;
            lastRefresh = currentTime;
        }

        return prevState;
    }

    @Override
    public boolean isObstacle(Entity e) {
        if (explosive instanceof Bomb) {
            return e instanceof Block;
        }

        return false;
    }

    public boolean canInteractWith(Entity e){
        if (explosive instanceof Bomb){
            return e instanceof Player || e instanceof Enemy || e instanceof Block;
        }else if(explosive instanceof TankEnemy){
            return e instanceof Player;
        }

        return false;
    }

    public boolean getCanExpand() {
        if (distanceFromExplosive >= maxDistance) canExpand = false;
        return canExpand;
    }

    public void cantExpandAnymore(){
        Coordinates nextTopLeftCoords = nextCoords(direction,getSize());
        new Explosion(nextTopLeftCoords,direction, distanceFromExplosive +1, getExplosive(),false);
    }

    public Explosive getExplosive(){
        return explosive;
    }




}
