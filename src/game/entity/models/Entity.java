package game.entity.models;

import game.BomberMan;
import game.entity.Player;
import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.models.Direction;
import game.ui.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.List;

import static game.ui.Utility.loadImage;


/**
 * Represents an entity in the game world, such as a player, enemy, or obstacle.
 */
public abstract class Entity {
    protected static final int IMAGE_REFRESH_RATE = 200;
    protected BufferedImage image;
    protected int lastImageIndex;
    protected long lastImageUpdate;
    private Coordinates coords;
    private boolean isSpawned = false;
    private final long id;

    /**
     * Constructs an entity with the given coordinates.
     *
     * @param coordinates the coordinates of the entity
     */
    public Entity(Coordinates coordinates){
        this.id = UUID.randomUUID().getMostSignificantBits();
        this.coords = coordinates;
    }

    protected String getBasePath(){ return ""; }
    protected void onSpawn(){}
    protected void onDespawn(){}

    /**
     * Performs an interaction between this entity and another entity.
     *
     * @param e the other entity to interact with
     */
    protected abstract void doInteract(Entity e);

    public abstract int getSize();

    /**
     * Returns the image of the entity.
     *
     * @return the image of the entity
     */
    public abstract BufferedImage getImage();

    /**
     * Returns the size of the entity in pixels.
     *
     * @return the size of the entity
     */
    public float getImageRatio(){
        return 1;
    }

    public long getId() {
        return id;
    }

    /**
     * Loads the image at the given file path and sets it as the image of this entity.
     *
     * @param imagePath the file path of the image to load
     * @return the loaded image
     */
    public BufferedImage loadAndSetImage(String imagePath) {
        this.lastImageUpdate = System.currentTimeMillis();
        this.image = loadImage(imagePath);
        return this.image;
    }

    /**
     * Sets the coordinates of the entity to the given coordinates.
     *
     * @param coordinates the new coordinates of the entity
     */
    public void setCoords(Coordinates coordinates) {
        this.coords = coordinates;
    }

    /**
     * Returns the coordinates of the entity.
     *
     * @return the coordinates of the entity
     */
    public Coordinates getCoords() {
        return coords;
    }

    /**
     * Returns true if the entity has been spawned in the game world, false otherwise.
     *
     * @return true if the entity has been spawned, false otherwise
     */
    public boolean isSpawned() {
        return isSpawned;
    }

    /**
     * Sets whether the entity has been spawned in the game world.
     *
     * @param s true if the entity has been spawned, false otherwise
     */
    protected void setSpawned(boolean s) {
        isSpawned = s;
    }

    /**
     * Returns a list of coordinates that the entity occupies in the game world.
     *
     * @return a list of coordinates that the entity occupies
     */
    public List<Coordinates> getPositions(){
        List<Coordinates> result = new ArrayList<>();

        int startX = getCoords().getX();
        int endX = getCoords().getX() + getSize() - 1;

        int startY = getCoords().getY();
        int endY = getCoords().getY() + getSize() - 1;

        for (int x = startX; x <= endX; x++)
            for (int y = startY; y <= endY; y++)
                result.add(new Coordinates(x, y));

        return result;
    }

    public Coordinates getSpawnOffset(){
        return new Coordinates(0,0);
    }

    /**
     * Despawns the entity from the game world.
     */
    public void despawn() {
        setSpawned(false);
        BomberMan.getInstance().removeEntity(this);
        this.onDespawn();
    }

    /**
     * Spawns the entity if it is not already spawned and if there is no other entity at the desired coordinates.
     */
    public void spawn() {
        if (isSpawned()) {
            return;
        }

        // EDIT
        List<Coordinates> desiredPosition = getPositions();
        boolean canSpawn =
                desiredPosition.parallelStream().noneMatch(coordinates -> BomberMan.getInstance().getEntities().contains(coordinates))
                && desiredPosition.parallelStream().noneMatch(coordinates -> BomberMan.getInstance().getBlocks().contains(coordinates));
        if(canSpawn) {
                setCoords(coords);
                setSpawned(true);
                if (this instanceof InteractiveEntities) {
                    BomberMan.getInstance().addEntity((InteractiveEntities) this);
                }
                else if (this instanceof Block) {
                    BomberMan.getInstance().addBlock((Block) this);
                }
        }

        this.onSpawn();
    }


    public Coordinates getNewTopLeftCoordinatesOnDirection(Direction d, int distance){
        int sign = 0;

        switch (d){
            case UP:case LEFT: sign = -1; break;
            case DOWN:case RIGHT: sign = 1; break;
        }

        switch (d){
            case LEFT:case RIGHT:  return new Coordinates(getCoords().getX() + distance*sign, getCoords().getY());
            case DOWN:case UP: return new Coordinates(getCoords().getX() , getCoords().getY()+ distance*sign);
        }

        return null;
    }


    public List<Coordinates> getNewCoordinatesOnDirection(Direction d, int steps, int offset){
        List<Coordinates> desiredCoords = new ArrayList<>();

        switch (d) {
            case RIGHT: return getNewCoordinatesOnRight(steps, offset, getSize());
            case LEFT: return getNewCoordinatesOnLeft(steps, offset);
            case UP: return getNewCoordinatesOnUp(steps, offset);
            case DOWN: return getNewCoordinatesOnDown(steps, offset, getSize());
        }

        return desiredCoords;
    }

    private List<Coordinates> getNewCoordinatesOnRight(int steps, int offset, int size) {
        List<Coordinates> coordinates = new ArrayList<>();
        int first = steps;
        int last = 0;
        for (int step = 0; step <= steps / offset; step++) {
            for (int i = 0; i <= getSize() / offset; i++) {
                if (i== getSize()/offset) last = GamePanel.PIXEL_UNIT;

                coordinates.add(new Coordinates(getCoords().getX() + size - 1 + first + step * offset, getCoords().getY() + i * offset - last));
            }
            first = 0;
        }
        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnLeft(int steps, int offset) {
        List<Coordinates> coordinates = new ArrayList<>();
        int first = steps;
        int last = 0;
        for (int step = 0; step <= steps/offset; step++) {
            for (int i = 0; i <= getSize() / offset; i++) {
                if (i== getSize()/offset) last = GamePanel.PIXEL_UNIT;
                coordinates.add(new Coordinates(getCoords().getX() - first - step * offset, getCoords().getY() + i * offset - last));
            }first =0;
        }
        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnUp(int steps, int offset) {
        List<Coordinates> coordinates = new ArrayList<>();
        int first = steps;
        int last = 0;
        for (int step = 0; step <= steps/offset; step++) {
            for (int i = 0; i <= getSize() / offset; i++) {
                if (i== getSize()/offset) last = GamePanel.PIXEL_UNIT;
                coordinates.add(new Coordinates(getCoords().getX() + i * offset - last, getCoords().getY() - first - step * offset));
            }first = 0;
        }
        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnDown(int steps, int offset, int size) {
        List<Coordinates> coordinates = new ArrayList<>();
        int first = steps;
        int last = 0;
        for (int step = 0; step <= steps / offset; step++) {
            for (int i = 0; i <= getSize() / offset; i++) {
                if (i== getSize()/offset) last = GamePanel.PIXEL_UNIT;

                coordinates.add(new Coordinates(getCoords().getX() + i * offset -last, getCoords().getY() + size - 1 + first + step * offset));
            }first =0;
        }
        return coordinates;
    }

    protected boolean isInside(Entity e) {
        int centerX = this.getCoords().getX() + getSize()/2;
        int centerY = this.getCoords().getY() + getSize()/2;

        boolean isValidX = centerX >= e.getCoords().getX() && centerX <= e.getCoords().getX() + e.getSize();
        boolean isValidY = centerY >= e.getCoords().getY() && centerY <= e.getCoords().getY() + e.getSize();

        return isValidX && isValidY;
    }







    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
