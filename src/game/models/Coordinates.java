package game.models;

import game.BomberMan;
import game.entity.models.Entity;
import game.entity.models.InteractiveEntities;
import game.ui.GameFrame;

import java.awt.*;
import java.time.temporal.ValueRange;
import java.util.*;

import static game.ui.GamePanel.GRID_SIZE;

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

    public boolean validate() {
        GameFrame gameFrame = BomberMan
                .getInstance()
                .getGameFrame();

        if(gameFrame == null) return false;

        Dimension gamePanelDimensions = BomberMan
                .getInstance()
                .getGameFrame()
                .getGamePanel()
                .getPanelDimensions();

        ValueRange rangeY = ValueRange.of(0, gamePanelDimensions.height - GRID_SIZE/2);
        ValueRange rangeX = ValueRange.of(0, gamePanelDimensions.width - GRID_SIZE/2);

        return (rangeY.isValidValue(getY()) && rangeX.isValidValue(getX()));
    }

    public static Coordinates RoundedRandomCoords(Coordinates offset){
        Dimension dimensions = BomberMan.getInstance()
                .getGameFrame()
                .getGamePanel()
                .getPanelDimensions();

        Coordinates output = roundCoordinates(new Coordinates( (    (int) (Math.random() * dimensions.getWidth())  ),
                (  (int) (Math.random() *  dimensions.getHeight() ) )), offset);
        return output;
    }
    public static Coordinates roundCoordinates(Coordinates coords){
        return roundCoordinates(coords,new Coordinates(0,0));
    }
    public static Coordinates roundCoordinates(Coordinates coords, Coordinates offset){
        return new Coordinates(   ((coords.getX() / GRID_SIZE) * GRID_SIZE + offset.getX()),


                (    (coords.getY()/ GRID_SIZE) * GRID_SIZE + offset.getY()));
    }

    public static Coordinates generateRandomCoordinates(){
        return generateRandomCoordinates(new Coordinates(0,0));
    }

    public static Coordinates generateCoordinatesAwayFrom(Coordinates other, int offset) {
        Coordinates coord;
        while ((coord = Coordinates.generateRandomCoordinates()).distanceTo(other) < offset);
        return coord;
    }

    public static Coordinates generateRandomCoordinates(Coordinates spawnOffset){
        var blocks = BomberMan.getInstance().getBlocks();
        var entities = BomberMan.getInstance().getEntities();

        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        while (true) {
            Coordinates coords = RoundedRandomCoords(spawnOffset);

            if (!InteractiveEntities.isBlockOccupied(coords)){
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

}
