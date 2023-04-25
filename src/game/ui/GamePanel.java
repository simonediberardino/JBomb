package game.ui;

import game.BomberMan;
import game.entity.Character;
import game.entity.Entity;
import game.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The GamePanel class represents the main game panel that displays the game world and entities
 */
public class GamePanel extends JPanel implements Observer {
    public static final Dimension DEFAULT_DIMENSION = new Dimension(770, 630);

    /**
     * Constructs a new GamePanel with the default dimensions and sets it as the observer for the game ticker observable
     */
    public GamePanel() {
        // Convert default dimensions to screen size
        int convertedHeight = Utility.px((int) DEFAULT_DIMENSION.getHeight());
        int convertedWidth = Utility.px((int) DEFAULT_DIMENSION.getWidth());

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

        // Create new HashSet to avoid ConcurrentModificationException
        Set<Entity> newSet = new HashSet<>(BomberMan.getInstance().getEntities());

        // Draw each entity in the new set
        for (Entity entity : newSet) {
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
}