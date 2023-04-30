package game.level;

import game.BomberMan;
import game.entity.*;
import game.models.Coordinates;
import game.ui.Paths;
import game.ui.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static game.entity.models.Character.PADDING_HEAD;


/**
 * The abstract Level class represents the general structure and properties of a game level.
 * It includes methods that allow concrete implementations to define the type of blocks and
 * terrain that the level is composed of, as well as the length of the explosion that occurs
 * when bombs are detonated, and the background image of the level.
 */
public abstract class Level {
    private int id;

    private Level(){}

    public Level(int id){
        this.id = id;
    }

    public abstract void spawnEnemies();
    public abstract int startEnemiesCount();
    public abstract int getMaxBombs();
    public abstract int getMaxDestroyableBlocks();

    public abstract int getExplosionLength();

    /**
     * Generates stone blocks on the game panel.
     *
     * @param jPanel the panel on which to generate the stone blocks
     */
    public abstract void generateStone(JPanel jPanel);
    public abstract void generateDestroyableBlock();

    /**
     *
     Returns the path to the image file for the stone block.
     @return a string representing the path to the image file.
     */
    public String getStoneBlock() {
        return Paths.getCurrentLevelFolder() + "/stone.png";
    }

    /**

     Returns the path to the image file for the grass block.
     @return a string representing the path to the image file.
     */
    public String getGrassBlock() {
        return Paths.getCurrentLevelFolder() + "/grass.png";
    }
    /**

     Returns the path to the image file for the destroyable block.
     @return the path to the image file for the destroyable block.
     */
    public String getDestroyableBlock() {
        return Paths.getCurrentLevelFolder() + "/destroyable_block.png";
    }

    /**
     * Returns the Images for the level pitch.
     *
     * @return the Images for the level pitch
     */
    public Image[] getPitch(){
        final int SIDES = 4;
        Image[] pitch = new Image[SIDES];
        for(int i = 0; i < SIDES; i++){
            pitch[i] = Utility.loadImage(String.format("%s/border_%d.png", Paths.getCurrentLevelFolder(), i));
        }
        return pitch;
    }

    /**
     * Starts the game level by generating the terrain and adding the player character to the game panel.
     *
     * @param jPanel the panel on which to start the game level
     */
    public void start(JPanel jPanel){
        generateStone(jPanel);

        generateDestroyableBlock();
        spawnEnemies();
        new Player(Coordinates.generateRandomCoordinates(Player.spawnOffset)).spawn();


    }

    public int getId() {
        return id;
    }
}
