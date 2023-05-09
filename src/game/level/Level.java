package game.level;

import game.BomberMan;
import game.entity.*;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.enemies.boss.clown.Clown;
import game.entity.enemies.npcs.Orb;
import game.entity.models.Enemy;
import game.models.Coordinates;
import game.models.EnhancedDirection;
import game.utils.Paths;
import game.utils.Utility;

import javax.swing.*;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static game.panels.PitchPanel.GRID_SIZE;


/**
 * The abstract Level class represents the general structure and properties of a game level.
 * It includes methods that allow concrete implementations to define the type of blocks and
 * terrain that the level is composed of, as well as the length of the explosion that occurs
 * when bombs are detonated, and the background image of the level.
 */
public abstract class Level {
    private int id;
    protected int maxBombs = 1;
    protected int explosionLength = 6;

    private Level(){}

    public Level(int id){
        this.id = id;
    }

    public abstract int startEnemiesCount();
    public abstract int getMaxDestroyableBlocks();
    public abstract int getExplosionLength();
    public abstract Class<? extends Enemy>[] availableEnemies();

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
        BomberMan.getInstance().setGameState(true);
        generateStone(jPanel);

        BomberMan.getInstance().setPlayer(new Player(Coordinates.generateRandomCoordinates(Player.SPAWN_OFFSET)));
        BomberMan.getInstance().getPlayer().spawn();
        generateDestroyableBlock();


        spawnEnemies();
    }

    // This method spawns enemies in the game.
    public void spawnEnemies() {
        // Get an array of available enemy classes.
        Class<? extends Enemy>[] availableEnemies = availableEnemies();
        //new Boss(new Coordinates(120,120)).spawn();
        new Clown(new Coordinates(480,500)).spawn();
        // Spawn a number of enemies at the start of the game.
        //new Boss(Coordinates.randomCoordinatesFromPlayer()).spawn();
        for (int i = 0; i < startEnemiesCount(); i++) {
            Orb orb = new Orb(new Coordinates(0,0),EnhancedDirection.RIGHTDOWN);
            orb.spawn();


            // Select a random enemy class from the availableEnemies array.
            Class<? extends Enemy> enemyClass = availableEnemies[new Random().nextInt(availableEnemies.length)];

            // Create an instance of the enemy class using a constructor that takes a Coordinates object as an argument.
            Enemy enemy;
            try {
                enemy = enemyClass.getConstructor(Coordinates.class).newInstance(Coordinates.randomCoordinatesFromPlayer());

                // Spawn the enemy on the game board.
                enemy.spawn();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    // This method returns the ID of an object.
    public int getId() {
        return id;
    }

    // This method returns the maximum number of bombs that a player can have at one time.
    public int getMaxBombs() {
        return maxBombs;
    }

    /**
     * Generates the stone blocks in the game board for level 1.
     *
     * @param jPanel the JPanel where the stone blocks are to be placed.
     */
    public void generateStone(JPanel jPanel) {
        // Set the current x and y coordinates to the top-left corner of the game board.
        int currX = 0;
        int currY = GRID_SIZE;

        // Loop through the game board, adding stone blocks at every other grid position.
        while (currY < jPanel.getPreferredSize().getHeight() - GRID_SIZE) {
            while (currX < jPanel.getPreferredSize().getWidth() - GRID_SIZE && currX + GRID_SIZE * 2 <= jPanel.getPreferredSize().getWidth()) {
                // Move the current x coordinate to the next grid position.
                currX += GRID_SIZE;

                // Create a new stone block at the current coordinates and spawn it on the game board.
                new StoneBlock(new Coordinates(currX, currY)).spawn();

                // Move the current x coordinate to the next grid position.
                currX += GRID_SIZE;
            }
            // Move the current x coordinate back to the left side of the game board.
            currX = 0;

            // Move the current y coordinate down to the next row of grid positions.
            currY += GRID_SIZE * 2;
        }
    }

    // This method generates destroyable blocks in the game board.
    public void generateDestroyableBlock(){
        DestroyableBlock block = new DestroyableBlock(new Coordinates(0,0));

        // Initialize a counter for the number of destroyable blocks spawned.
        int i = 0;

        // Loop until the maximum number of destroyable blocks has been spawned.
        while (i < getMaxDestroyableBlocks()){
            // If the current destroyable block has not been spawned, generate new coordinates for it and spawn it on the game board.
            if (!block.isSpawned()){
                block.setCoords(Coordinates.generateCoordinatesAwayFrom(BomberMan.getInstance().getPlayer().getCoords(), GRID_SIZE*2));
                block.spawn();
            }
            // If the current destroyable block has been spawned, create a new one and increment the spawn counter.
            else {
                block = new DestroyableBlock(new Coordinates(0,0));
                i++;
            }
        }
    }



}
