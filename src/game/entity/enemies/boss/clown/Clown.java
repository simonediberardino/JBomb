package game.entity.enemies.boss.clown;

import game.BomberManMatch;
import game.entity.Player;
import game.entity.bomb.Bomb;
import game.entity.bomb.Explosion;
import game.entity.enemies.npcs.Orb;
import game.entity.enemies.boss.Boss;
import game.entity.models.Entity;
import game.entity.models.Explosive;
import game.models.Coordinates;
import game.models.Direction;
import game.models.EnhancedDirection;
import game.ui.panels.BombermanFrame;
import game.ui.panels.PitchPanel;
import game.utils.Dimensions;
import game.utils.Paths;
import game.utils.Utility;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.IntFunction;

/**

 The Clown class represents a type of Boss entity that implements the Explosive interface.
 It has a boolean property hasHat that determines whether the Clown is wearing a hat or not.
 The Clown entity can spawn orbs, enhanced orbs, explosions and throw its hat in random directions.
 */
public class Clown extends Boss implements Explosive {
    private static final float RATIO_HEIGHT_WITH_HAT = 0.7517f;
    private static final float RATIO_HEIGHT = 0.87f;
    private static final float RATIO_WIDTH = 0.8739f;
    private static final String SKIN_PATH_TEMPLATE = "%s/clown/clown_%s_%s.png";

    /**
     The hasHat property represents whether the Clown entity is wearing a hat or not.
     */
    private boolean hasHat;
    /**

     Constructor for the Clown entity that takes in the entity's starting coordinates and sets its initial hasHat value to true.
     @param coordinates The starting coordinates of the Clown entity.
     */
    private Clown(Coordinates coordinates){
        super(coordinates);
    }

    public Clown(){
        super(null);

        Dimension panelSize = BomberManMatch
                .getInstance()
                .getGameFrame()
                .getPitchPanel()
                .getPreferredSize();

        int y = (int) (panelSize.getHeight() - getSize());
        int x = (int) (panelSize.getWidth() / 2 - getSize() / 2);

        setCoords(new Coordinates(x, y));
        widthToHitboxSizeRatio = RATIO_WIDTH;
        hasHat = true;
    }
    
    /**

     Overrides the getBaseSkins method from the Boss superclass to return an array of skin paths based on the hasHat value.
     @return A String array of skin paths for the Clown entity.
     */
    @Override
    public String[] getBaseSkins() {
        return new String[] { getImageFromRageStatus() };
    }

    /**
     @return A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    public boolean isHatImage(String path) {
        String[] toks = path.split("_");
        if(toks.length <= 1) return false;

        return toks[1].equals("1");
    }
    /**

     Setter method for the hasHat property.
     @param hasHat A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    public void setHasHat(boolean hasHat) {
        this.hasHat = hasHat;
    }
    /**

     Overrides the isObstacleOfExplosion method from the Explosive interface to check if the input entity is null or if it is an obstacle for the Clown entity's explosion.
     @param e The entity to check if it is an obstacle for the Clown entity's explosion.
     @return A boolean value representing whether the input entity is an obstacle for the Clown entity's explosion or not.
     */
    @Override
    public boolean isObstacleOfExplosion(Entity e) {
        return (e == null) || (getExplosionObstacles().stream().anyMatch(c -> c.isInstance(e)));
    }

    /**
     Overrides the getExplosionObstacles method from the Explosive interface to return an empty list.
     @return An empty List object.
     */
    @Override
    public List<Class<? extends Entity>> getExplosionObstacles() {
        return Collections.emptyList();
    }

    /**
     * Returns a list of entity classes that can interact with explosions.
     *
     * @return a list of entity classes that can interact with explosions.
     */
    @Override
    public List<Class<? extends Entity>> getExplosionInteractionEntities() {
        return Arrays.asList(Player.class, Bomb.class);
    }

    /**
     * Determines whether this entity can interact with the given entity in an explosion.
     *
     * @param e the entity to check
     * @return true if this entity can interact with the given entity in an explosion, false otherwise.
     */
    @Override
    public boolean canExplosionInteractWith(Entity e) {
        return e == null || (getExplosionInteractionEntities().stream().anyMatch(c -> c.isInstance(e)));
    }

    /**
     * Returns the maximum distance of an explosion from this entity.
     *
     * @return the maximum distance of an explosion from this entity.
     */
    @Override
    public int getMaxExplosionDistance() {
        return 10;
    }

    /**
     * Returns the chance of this entity shooting a projectile.
     *
     * @return the chance of this entity shooting a projectile.
     */
    private int getShootingChance() {
        return 1;
    }

    /**
     * Spawns orbs in all directions around this entity.
     */
    private void spawnOrbs() {
        for (Direction d: Direction.values()) {
            new Orb(
                    Coordinates.fromDirectionToCoordinateOnEntity(
                            this,
                            d,
                            Orb.SIZE,
                            Orb.SIZE
                    ), d
            ).spawn(true,false);
        }
    }

    /**
     * Spawns enhanced orbs in all enhanced directions around this entity.
     */
    private void spawnEnhancedOrbs() {
        for (EnhancedDirection d: EnhancedDirection.values()) {
            new Orb(Coordinates.fromDirectionToCoordinateOnEntity(this,d,Orb.SIZE),d).spawn(true, false);
        }
    }

    /**
     * Calculates the explosion offsets based on the given direction.
     *
     * @param d the direction
     * @return the explosion offsets as an int array
     */
    private int[] calculateExplosionOffsets(Direction d) {
        int inwardOffset = getSize() / 4;
        int parallelOffset = -Explosion.SIZE / 2;

        switch (d) {
            case RIGHT:
            case LEFT:
                parallelOffset = 0;
                inwardOffset = getSize() / 3 - PitchPanel.GRID_SIZE / 2;
                break;
        }

        return new int[]{ inwardOffset, parallelOffset };
    }

    /**
     * Spawns an explosion in a random direction.
     */
    private void spawnExplosion() {
        Direction[] dirs = Direction.values();
        LinkedList<Direction> directions = new LinkedList<>(Arrays.asList(dirs));
        directions.remove(Direction.UP);
        Direction d = directions.get((int) (Math.random()*directions.size()));

        int[] offsets = calculateExplosionOffsets(d);

        new Explosion(Coordinates.fromDirectionToCoordinateOnEntity(this, d, offsets[0], offsets[1], Explosion.SIZE), d, this);
    }

    /**
     * Throws a hat in a random enhanced direction.
     */
    public void throwHat() {
        EnhancedDirection d = EnhancedDirection.randomDirectionTowardsCenter(this);
        new Hat(Coordinates.fromDirectionToCoordinateOnEntity(this, d, 0), d).spawn(true, false);
        hasHat = false;
    }

    /**
     * Updates this entity's state.
     * @param gamestate the current gamestate
     */
    public void update(boolean gamestate) {
        // Check if the entity should shoot orbs
        Utility.runPercentage(getShootingChance(), () -> {
            spawnEnhancedOrbs();
            spawnOrbs();
        });

        // Check if the entity should shoot an explosion
        Utility.runPercentage(getShootingChance(), this::spawnExplosion);

        if(hasHat){
            Utility.runPercentage(getShootingChance(), this::throwHat);
        }

        super.update(gamestate);
    }


    @Override
    protected void updateRageStatus(int status) {
        if(status == currRageStatus) return;

        currRageStatus = status;
        String imagePath = getImageFromRageStatus();
        loadAndSetImage(imagePath);
    }

    @Override
    protected String getImageFromRageStatus() {
        return String.format(SKIN_PATH_TEMPLATE, Paths.getEnemiesFolder(), hasHat ? 1 : 0, currRageStatus);
    }

    @Override
    public List<Direction> getSupportedDirections() {
        return Arrays.asList(Direction.LEFT, Direction.RIGHT);
    }

    @Override
    protected void onHit(int damage) {
        int hpPercentage = getHpPercentage();
        Map.Entry<Integer, Integer> entry = healthStatusMap.ceilingEntry(hpPercentage);

        if (entry != null) {
            updateRageStatus(entry.getValue());
        }
    }

    @Override
    protected Map<Integer, Integer> healthStatusMap() {
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.put(75, 0);
        map.put(60, 1);
        map.put(50, 2);
        map.put(25, 3);
        return map;
    }

    @Override
    public int getPaddingTop(){
        heightToHitboxSizeRatio = hasHat ? RATIO_HEIGHT_WITH_HAT : RATIO_HEIGHT;
        return super.getPaddingTop();
    }

    @Override
    public float getHeightToHitboxSizeRatio() {
        heightToHitboxSizeRatio = hasHat ? RATIO_HEIGHT_WITH_HAT : RATIO_HEIGHT;
        return heightToHitboxSizeRatio;
    }

    @Override
    public float getHeightToHitboxSizeRatio(String path){
        heightToHitboxSizeRatio = isHatImage(path) ? RATIO_HEIGHT_WITH_HAT : RATIO_HEIGHT;
        return heightToHitboxSizeRatio;
    }


}

