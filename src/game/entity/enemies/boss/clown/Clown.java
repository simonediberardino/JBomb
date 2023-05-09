package game.entity.enemies.boss.clown;

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
import game.ui.panels.PitchPanel;
import game.utils.Paths;
import game.utils.Utility;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**

 The Clown class represents a type of Boss entity that implements the Explosive interface.
 It has a boolean property hasHat that determines whether the Clown is wearing a hat or not.
 The Clown entity can spawn orbs, enhanced orbs, explosions and throw its hat in random directions.
 */
public class Clown extends Boss implements Explosive {

    /**
     The hasHat property represents whether the Clown entity is wearing a hat or not.
     */
    private boolean hasHat;
    /**

     Constructor for the Clown entity that takes in the entity's starting coordinates and sets its initial hasHat value to true.
     @param coordinates The starting coordinates of the Clown entity.
     */
    public Clown(Coordinates coordinates){
        super(coordinates);
        widthToHitboxSizeRatio = 0.8739f;
        hasHat = true;
    }
    /**

     Overrides the getImage method from the Boss superclass to change the heightToHitboxSizeRatio property based on the hasHat value.

     @return A BufferedImage representing the Clown entity's current image.
     */
    @Override
    public BufferedImage getImage() {
        BufferedImage res = super.getImage();

        if (hasHat) heightToHitboxSizeRatio = 0.7517f;
        else heightToHitboxSizeRatio = 0.87f;

        return res;
    }

    /**

     Overrides the getBaseSkins method from the Boss superclass to return an array of skin paths based on the hasHat value.
     @return A String array of skin paths for the Clown entity.
     */
    @Override
    public String[] getBaseSkins() {
        if (hasHat) {
            return new String[]{Paths.getEnemiesFolder() + "/clown/clown_with_hat.png"};
        }
        return new String[]{Paths.getEnemiesFolder() + "/clown/clown.png"};
    }
    /**

     Getter method for the hasHat property.
     @return A boolean value representing whether the Clown entity is wearing a hat or not.
     */
    public boolean isHasHat() {
        return hasHat;
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
            new Orb(Coordinates.fromDirectionToCoordinateOnEntity(this,d, Orb.SIZE,Orb.SIZE),d).spawn(true,false);
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
    private void throwHat() {
        EnhancedDirection d = EnhancedDirection.values()[(int)(Math.random()*EnhancedDirection.values().length)];
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

    /**
     * Chooses a new direction for the agent to move in, and sends the corresponding command to the game engine.
     *
     * @param forceChange If true, the agent will be forced to change direction even if it just changed directions.
     *                    If false, there is a chance the agent will keep its current direction.
     * @return new direction
     */
    @Override
    public Direction chooseDirection(boolean forceChange) {
        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Get a list of all the available directions the agent can move in
        List<Direction> availableDirections = Arrays.asList(Direction.values().clone());

        // If forceChange is true, remove the current direction from the list of available directions

        // If it hasn't been long enough since the last direction update, keep moving in the same direction, unless last move was blocked
        if (currentTime - lastDirectionUpdate < DIRECTION_REFRESH_RATE && !forceChange) {
            return currDirection;
        }

        if (availableDirections.isEmpty()) {
            return currDirection;
        }

        // Choose a new direction randomly, or keep the current direction with a certain probability
        Direction newDirection = null;
        if (Math.random() * 100 > CHANGE_DIRECTION_RATE) {
            newDirection = currDirection;
        }

        // If a new direction hasn't been chosen, choose one randomly from the available options
        if (newDirection == null) {
            newDirection = availableDirections.get((int) (Math.random() * availableDirections.size()));
        }

        // Send the command corresponding to the new direction to the game engine
        return newDirection;
    }

}

