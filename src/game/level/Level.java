package game.level;

import game.BomberMan;
import game.entity.*;
import game.models.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;




/**
 * The abstract Level class represents the general structure and properties of a game level.
 * It includes methods that allow concrete implementations to define the type of blocks and
 * terrain that the level is composed of, as well as the length of the explosion that occurs
 * when bombs are detonated, and the background image of the level.
 */
public abstract class Level {

    /**
     * Returns the image file name for the stone block.
     *
     * @return the image file name for the stone block
     */
    public abstract String getStoneBlock();

    /**
     * Returns the image file name for the grass block.
     *
     * @return the image file name for the grass block
     */
    public abstract String getGrassBlock();

    /**
     * Returns the image file name for the destroyable block.
     *
     * @return the image file name for the destroyable block
     */
    public abstract String getDestroyableBlock();

    /**
     * Returns the explosion length for bombs in the level.
     *
     * @return the explosion length for bombs in the level
     */
    public abstract int getExplosionLength();

    /**
     * Returns the icon for the level pitch.
     *
     * @return the icon for the level pitch
     */
    public abstract Icon getPitch();

    /**
     * Generates grass blocks on the game panel.
     *
     * @param jPanel the panel on which to generate the grass blocks
     */
    public void generateGrass(JPanel jPanel) {


        //for (int i = 0; i < jPanel.getHeight() / GRID_SIZE; i++)
          //  for (int j = 0; j < jPanel.getWidth() / GRID_SIZE; j++)
           //     new Grass(new Coordinates(i * GRID_SIZE, j * GRID_SIZE)).spawn();
    }

    /**
     * Generates stone blocks on the game panel.
     *
     * @param jPanel the panel on which to generate the stone blocks
     */
    public abstract void generateStone(JPanel jPanel);

    /**
     * Starts the game level by generating the terrain and adding the player character to the game panel.
     *
     * @param jPanel the panel on which to start the game level
     */
    public void start(JPanel jPanel){
        generateGrass(jPanel);
        generateStone(jPanel);
        new Player().spawn();
    }
}
