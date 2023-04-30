package game.models;

import game.BomberMan;
import game.entity.models.Entity;
import game.entity.models.InteractiveEntities;
import game.ui.GameFrame;
import game.ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static game.entity.models.Character.PADDING_HEAD;
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
    public static Coordinates generateRandomCoordinates(){
        return generateRandomCoordinates(new Coordinates(0,0));
    }
    public static Coordinates roundToGridSize(Coordinates offset){
        Dimension dimensions = BomberMan.getInstance()
                .getGameFrame()
                .getGamePanel()
                .getPanelDimensions();


        return new Coordinates((int) (Math.random() * (int) (dimensions.getWidth() / GRID_SIZE)) * GRID_SIZE + offset.getX(),
                (int) (Math.random() * (int) (dimensions.getHeight() / GRID_SIZE)) * GRID_SIZE + offset.getY());
    }

    public static Coordinates generateRandomCoordinates(Coordinates spawnOffset){


        var blocks = BomberMan.getInstance().getBlocks();
        var entities = BomberMan.getInstance().getEntities();

        var all = new HashSet<Entity>();
        all.addAll(blocks);
        all.addAll(entities);

        while (true) {
            Coordinates coords = roundToGridSize(spawnOffset);

            if (all.stream().noneMatch(entity -> InteractiveEntities.doesCollideWith(coords, entity))){
                return coords;
            }
        }
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
