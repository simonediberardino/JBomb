package game.entity.models;

import game.BomberMan;
import game.engine.GameTickerObserver;
import game.entity.enemies.Orb;
import game.models.Coordinates;
import game.models.Direction;
import game.powerups.PowerUp;
import game.panels.PitchPanel;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static game.panels.PitchPanel.GRID_SIZE;
import static game.utils.Utility.loadImage;


/**
 * Represents an entity in the game world, such as a player, enemy, or obstacle.
 */
public abstract class Entity extends GameTickerObserver {
    private final long id;

    protected BufferedImage image;
    protected int lastImageIndex;
    protected long lastImageUpdate;
    private Coordinates coords;
    private boolean isSpawned = false;
    private boolean isImmune = false;

    public Entity(){
        this(new Coordinates(-1, -1));
    }

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

    public int getImageRefreshRate(){
        return 200;
    }

    public long getId() {
        return id;
    }

    public boolean isImmune() {
        return isImmune;
    }

    public void setImmune(boolean isImmune) {
        this.isImmune = isImmune;
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

    public Coordinates getSpawnOffset(){
        return new Coordinates((PitchPanel.GRID_SIZE-getSize())/2,(PitchPanel.GRID_SIZE-getSize())/2);
    }

    /**
     * Despawns the entity from the game world.
     */
    public final void despawn() {
        setSpawned(false);
        BomberMan.getInstance().removeEntity(this);
        this.onDespawn();
    }

    public final void spawnAtRandomCoordinates() {
        setCoords(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE * 3));
        spawn();
    }

    /**
     * Spawns the entity if it is not already spawned and if there is no other entity at the desired coordinates.
     */
    public final void spawn(){
        spawn(false);
    }

    public final void spawn(boolean forceSpawn) {
        if (isSpawned()) {
            return;
        }

        setCoords(Coordinates.roundCoordinates(getCoords(), getSpawnOffset()));

        if (forceSpawn || !EntityInteractable.isBlockOccupied(coords)) {
            setCoords(Coordinates.roundCoordinates(getCoords(), getSpawnOffset()));
            setSpawned(true);
            BomberMan.getInstance().addEntity(this);
            onSpawn();
        }
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

    public List<Coordinates> getAllCoordinates(){
        List<Coordinates> coordinates = new ArrayList<>();
        int last = 0;
        for (int step = 0; step <= getSize() / PitchPanel.OFFSET_ON_CHECK; step++) {
            for (int i = 0; i <= getSize() / PitchPanel.OFFSET_ON_CHECK; i++) {
                if (i== getSize()/PitchPanel.OFFSET_ON_CHECK) last = PitchPanel.PIXEL_UNIT;

                coordinates.add(new Coordinates(getCoords().getX() + step * PitchPanel.OFFSET_ON_CHECK, getCoords().getY() + i * PitchPanel.OFFSET_ON_CHECK - last));
            }
        }
        return coordinates;
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
        int last = 0;
        for (int step = 0; step <= steps / offset; step++) {
            for (int i = 0; i <= getSize() / offset; i++) {
                if (i== getSize()/offset) last = PitchPanel.PIXEL_UNIT;

                coordinates.add(new Coordinates(getCoords().getX() + getSize()+step * offset, getCoords().getY() + i * offset - last));
            }
        }
        return coordinates;
    }

    private List<Coordinates> getNewCoordinatesOnLeft(int steps, int offset) {
        List<Coordinates> coordinates = new ArrayList<>();
        int first = steps;
        int last = 0;
        for (int step = 0; step <= steps/offset; step++) {
            for (int i = 0; i <= getSize() / offset; i++) {
                if (i== getSize()/offset) last = PitchPanel.PIXEL_UNIT;
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
                if (i== getSize()/offset) last = PitchPanel.PIXEL_UNIT;
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
                if (i== getSize()/offset)
                    last = PitchPanel.PIXEL_UNIT;
                coordinates.add(new Coordinates(getCoords().getX() + i * offset - last, getCoords().getY() + size - 1 + first + step * offset));
            }
            first = 0;
        }
        return coordinates;
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
