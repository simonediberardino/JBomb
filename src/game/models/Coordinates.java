package game.models;

import game.Bomberman;
import game.entity.models.Entity;
import game.entity.models.EntityInteractable;
import game.ui.panels.game.PitchPanel;
import game.utils.Utility;

import java.awt.*;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static game.ui.panels.game.PitchPanel.GRID_SIZE;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     Check whether a coordinate of an Entity is inside the pitch or not;
     @return true if valid, false otherwise;
     */
    public boolean validate(Entity e) {
        Dimension gamePanelDimensions = Bomberman
                .getBombermanFrame()
                .getPitchPanel()
                .getPanelDimensions();


        ValueRange rangeY = ValueRange.of(0, gamePanelDimensions.height - e.getSize());
        ValueRange rangeX = ValueRange.of(0, gamePanelDimensions.width - e.getSize());

        return (rangeY.isValidValue(getY()) && rangeX.isValidValue(getX()));
    }

    public static Coordinates roundedRandomCoords(Coordinates offset){
        Dimension dimensions = Bomberman.getBombermanFrame().getPitchPanel()
                .getPanelDimensions();

        return roundCoordinates(new Coordinates( (    (int) (Math.random() * dimensions.getWidth())  ),
                (  (int) (Math.random() *  dimensions.getHeight() ) )), offset);
    }

    public static Coordinates roundCoordinates(Coordinates coords){
        return roundCoordinates(coords,new Coordinates(0,0));
    }
    public static Coordinates roundCoordinates(Coordinates coords, Coordinates offset){
        return new Coordinates(((coords.getX() / GRID_SIZE) * GRID_SIZE + offset.getX()), ((coords.getY()/ GRID_SIZE) * GRID_SIZE + offset.getY()));
    }

    public static Coordinates generateRandomCoordinates(){
        return generateRandomCoordinates(new Coordinates(0,0));
    }

    public static Coordinates generateCoordinatesAwayFromPlayer() {
        return generateCoordinatesAwayFrom(Bomberman.getMatch().getPlayer().getCoords(), GRID_SIZE * 3);
    }

    public static Coordinates generateCoordinatesAwayFrom(Coordinates other, int offset) {
        Coordinates coord;
        while ((coord = Coordinates.generateRandomCoordinates()).distanceTo(other) < offset);
        return coord;
    }

    public static Coordinates randomCoordinatesFromPlayer() {
        return Coordinates.generateCoordinatesAwayFrom(Bomberman.getMatch().getPlayer().getCoords(), GRID_SIZE * 3);
    }

    public static Coordinates generateRandomCoordinates(Coordinates spawnOffset){
        while (true) {
            Coordinates coords = roundedRandomCoords(spawnOffset);

            if (!Coordinates.isBlockOccupied(coords)){
                return coords;
            }
        }
    }

    public double distanceTo(Coordinates other) {
        return Math.sqrt(Math.pow(Math.abs(this.x - other.x), 2) + Math.pow(Math.abs(this.y - other.y), 2));
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getX() == that.getX() && getY() == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public static Coordinates fromDirectionToCoordinateOnEntity(Entity entity, EnhancedDirection direction, int symmetricOffset){
        switch (direction){
            case LEFTUP: return new Coordinates(entity.getCoords().getX(),entity.getCoords().getY());
            case LEFTDOWN: return new Coordinates(entity.getCoords().getX(),entity.getCoords().getY()+ entity.getSize()-symmetricOffset);
            case RIGHTUP: return new Coordinates(entity.getCoords().getX()+ entity.getSize()-symmetricOffset, entity.getCoords().getY());
            case RIGHTDOWN: return new Coordinates(entity.getSize() + entity.getCoords().getX()-symmetricOffset, entity.getCoords().getY()+ entity.getSize()-symmetricOffset);

        }
        return null;
    }
    public static Coordinates fromDirectionToCoordinateOnEntity(Entity entity, Direction direction,int classSize,int symmetricOffset){



        return fromDirectionToCoordinateOnEntity(entity,direction,0,-classSize/2,symmetricOffset);
    }

    public static Coordinates fromDirectionToCoordinateOnEntity(Entity entity, Direction direction, int inwardOffset, int parallelOffset, int symmetricOffset){
        switch (direction){
            case LEFT:return new Coordinates(entity.getCoords().getX()+inwardOffset,entity.getCoords().getY()+ entity.getSize()/2+parallelOffset);

            case RIGHT:return new Coordinates(entity.getCoords().getX() + entity.getSize()-inwardOffset-symmetricOffset,entity.getCoords().getY()+ entity.getSize()/2+parallelOffset);

            case UP: return new Coordinates(entity.getCoords().getX()+ entity.getSize()/2+parallelOffset,entity.getCoords().getY()+inwardOffset);

            case DOWN: return new Coordinates(entity.getCoords().getX()+ entity.getSize()/2+parallelOffset,entity.getCoords().getY() + entity.getSize()-inwardOffset-symmetricOffset);
        }
        return null;
    }

    public ArrayList<Direction> fromCoordinatesToDirection(Coordinates entityCoords){
        ArrayList<Direction> output = new ArrayList<>();
        entityCoords = roundCoordinates(entityCoords);
        Coordinates mouseCoords = roundCoordinates(this);
        if(mouseCoords.getX()> entityCoords.getX()) output.add(Direction.RIGHT);
        if(mouseCoords.getX()< entityCoords.getX()) output.add(Direction.LEFT);
        if(mouseCoords.getY()>entityCoords.getY()) output.add(Direction.DOWN);
        if(mouseCoords.getY()<entityCoords.getY()) output.add(Direction.UP);
        return output;
    }


    public static Coordinates fromRowAndColumnsToCoordinates(Dimension d){
        return fromRowAndColumnsToCoordinates(d,0,0);
    }

    public static Coordinates fromRowAndColumnsToCoordinates(Dimension d, int offsetX, int offsetY){
        if(offsetX>= GRID_SIZE||offsetY>=GRID_SIZE){
            return null;
        }

        if ((d.getWidth()>= PitchPanel.DIMENSION.getWidth()/ GRID_SIZE)){
            return null;
        }

        if(d.getHeight()>= PitchPanel.DIMENSION.getHeight()/GRID_SIZE){
            return null;
        }

        return new Coordinates((int)d.getWidth()*GRID_SIZE+offsetX, (int)d.getHeight()*GRID_SIZE+offsetY);
    }

    public static Coordinates getCenterCoordinatesOfEntity(Entity e){
        return new Coordinates(e.getCoords().getX()+e.getSize()/2,e.getCoords().getY()+e.getSize()/2);
    }
    public static Entity getEntityOnCoordinates(Coordinates desiredCoords){
        List<Entity> entities = Coordinates.getEntitiesOnCoordinates(desiredCoords);
        if (!entities.isEmpty()) return entities.get(0);
        return null;
    }

    public static List<Entity> getEntitiesOnCoordinates(List<Coordinates> desiredCoords){
        List<Entity> entityLinkedList = new LinkedList<>();

        var entities = Bomberman.getMatch().getEntities();

        // Check for each entity if it occupies the specified coordinates
        entities.forEach(e -> {
            for (Coordinates coord : desiredCoords) {
                int entityBottomRightX = e.getCoords().getX() + e.getSize() - 1;
                int entityBottomRightY = e.getCoords().getY() + e.getSize() - 1;

                if (coord.getX() >= e.getCoords().getX()
                        && coord.getX() <= entityBottomRightX
                        && coord.getY() >= e.getCoords().getY()
                        && coord.getY() <= entityBottomRightY
                ) {
                    entityLinkedList.add(e);
                }
            }
        });

        return entityLinkedList;
    }

    /**
     * Gets a list of entities that occupy the specified coordinate.
     * @param nextOccupiedCoords the coordinate to check for occupied entities
     * @return a list of entities that occupy the specified coordinate
     */
    public static List<Entity> getEntitiesOnCoordinates(Coordinates nextOccupiedCoords) {
        // Get all the blocks and entities in the game
        var entities = Bomberman.getMatch().getEntities();

        // Use Java stream to filter entities that collide with the specified coordinate
        return entities.parallelStream().filter(e -> doesCollideWith(nextOccupiedCoords, e)).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Checks if the given coordinates collide with the given entity.
     *
     * @param nextOccupiedCoords the coordinates to check for collision
     * @param e     the entity to check for collision with
     * @return true if the given coordinates collide with the given entity, false otherwise
     */
    public static boolean doesCollideWith(Coordinates nextOccupiedCoords, Entity e) {
        return doesCollideWith(nextOccupiedCoords,e.getCoords(), e.getSize());
    }

    public static boolean doesCollideWith(Coordinates nextOccupiedCoords, Coordinates entityCoords) {
        return doesCollideWith(nextOccupiedCoords, entityCoords, GRID_SIZE);
    }

    private static boolean doesCollideWith(Coordinates nextOccupiedCoords, Coordinates entityCoords,int size) {
        // Get the coordinates of the bottom-right corner of the entity
        int entityBottomRightX = entityCoords.getX() + size - 1;
        int entityBottomRightY = entityCoords.getY() + size - 1;

        // Check if the given coordinates collide with the entity
        return (nextOccupiedCoords.getX() >= entityCoords.getX()
                && nextOccupiedCoords.getX() <= entityBottomRightX
                && nextOccupiedCoords.getY() >= entityCoords.getY()
                && nextOccupiedCoords.getY() <= entityBottomRightY);
    }
    public static boolean isBlockOccupied(Coordinates nextOccupiedCoords){
        return isBlockOccupied(nextOccupiedCoords, GRID_SIZE);
    }

    public static boolean isBlockOccupied(Coordinates nextOccupiedCoords,int size){
        HashSet<Coordinates> fourCorners = new HashSet<>(Arrays.asList
                (Coordinates.roundCoordinates(nextOccupiedCoords),
                        Coordinates.roundCoordinates(new Coordinates( nextOccupiedCoords.getX(), nextOccupiedCoords.getY()+size-1)),
                        Coordinates.roundCoordinates(new Coordinates(nextOccupiedCoords.getX()+size-1, nextOccupiedCoords.getY()+size-1)),
                        Coordinates.roundCoordinates(new Coordinates(nextOccupiedCoords.getX()+size-1, nextOccupiedCoords.getY()))));
        // Get all the blocks and entities in the game
        var entities = Bomberman.getMatch().getEntities();
        return entities.parallelStream().anyMatch(e -> fourCorners.stream().anyMatch(coords-> doesCollideWith(coords,e)));
    }



}
