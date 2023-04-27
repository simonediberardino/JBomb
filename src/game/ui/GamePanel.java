package game.ui;

import game.BomberMan;
import game.entity.Block;
import game.entity.Entity;
import game.entity.InteractiveEntities;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static game.ui.Utility.loadImage;

/**
 * The GamePanel class represents the main game panel that displays the game world and entities
 */
public class GamePanel extends JPanel implements Observer {
    public static final Dimension DEFAULT_DIMENSION = new Dimension(750, 630);
    public static final int DEFAULT_PIXEL_UNIT = 5;
    public static final int PIXEL_UNIT = Utility.px(DEFAULT_PIXEL_UNIT);
    public static final int COMMON_DIVISOR = PIXEL_UNIT*2;
    public static final int GRID_SIZE = GamePanel.COMMON_DIVISOR*6;


    /**
     * Constructs a new GamePanel with the default dimensions and sets it as the observer for the game ticker observable
     */
    public GamePanel() {
        // Convert default dimensions to screen size
        int convertedHeight = Utility.px((int) DEFAULT_DIMENSION.getHeight());
        convertedHeight = convertedHeight/(GRID_SIZE *2)*(GRID_SIZE*2)+GRID_SIZE;
        int convertedWidth = Utility.px((int) DEFAULT_DIMENSION.getWidth());
        convertedWidth = convertedWidth/(GRID_SIZE *2)*(GRID_SIZE*2)+GRID_SIZE;

        // Set preferred, maximum, and minimum size to converted dimensions
        setPreferredSize(new Dimension(convertedWidth, convertedHeight));
        setMaximumSize(new Dimension(convertedWidth, convertedHeight));
        setMinimumSize(new Dimension(convertedWidth, convertedHeight));

        // Set this GamePanel as observer for the game ticker observable
        BomberMan.getInstance().getGameTickerObservable().deleteObservers();
        BomberMan.getInstance().getGameTickerObservable().addObserver(this);

        repaint();
    }

    /**
     * Paints the graphics of the entities in the game world
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        paintComponent(g2d);
        Set<? extends InteractiveEntities> setEntities = BomberMan.getInstance().getEntities();
        Set<? extends Block> setBlocks = BomberMan.getInstance().getBlocks();
        // Draw each entity in the new set

        for (Entity block : setBlocks){
            drawEntity(g2d, block);
        }

        for (Entity entity : setEntities) {
            drawEntity(g2d, entity);
        }

    }

    /**
     * Draws an entity on the game panel
     * @param g2d the Graphics2D object to draw with
     * @param e the entity to draw
     */
    private void drawEntity(Graphics2D g2d, Entity e){
        // Draw entity's image at entity's coordinates and size
        g2d.drawImage(e.getImage(), e.getCoords().getX(), e.getCoords().getY(), e.getSize(), e.getSize(), this);
    }

    /**
     * Repaints the game panel when it is updated by the game ticker observable
     * @param o the observable object
     * @param arg the object argument
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(loadImage(BomberMan.getInstance().getCurrentLevel().getGrassBlock()), 0, 0, null);
    }
}