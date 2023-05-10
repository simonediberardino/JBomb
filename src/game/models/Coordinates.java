package game.models;

import game.BomberManMatch;
import game.Bomberman;
import game.entity.models.Entity;
import game.entity.models.EntityInteractable;

import java.awt.*;
import java.time.temporal.ValueRange;
import java.util.*;

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

    public static Coordinates generateCoordinatesAwayFrom(Coordinates other, int offset) {
        Coordinates coord;
        while ((coord = Coordinates.generateRandomCoordinates()).distanceTo(other) < offset);
        return coord;
    }

    public static Coordinates randomCoordinatesFromPlayer() {
        return Coordinates.generateCoordinatesAwayFrom(BomberManMatch.getInstance().getPlayer().getCoords(), GRID_SIZE * 3);
    }

    public static Coordinates generateRandomCoordinates(Coordinates spawnOffset){
        var blocks = BomberManMatch.getInstance().getStaticEntities();
        var entities = BomberManMatch.getInstance().getEntities();

        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        while (true) {
            Coordinates coords = roundedRandomCoords(spawnOffset);

            if (!EntityInteractable.isBlockOccupied(coords)){
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




}
