package game.level;

import game.Bomberman;
import game.controller.ControllerManager;
import game.controller.MouseControllerManager;
import game.data.DataInputOutput;
import game.entity.*;
import game.entity.blocks.DestroyableBlock;
import game.entity.blocks.StoneBlock;
import game.entity.enemies.boss.Boss;
import game.entity.models.Enemy;
import game.events.GameEvent;
import game.level.world1.*;
import game.level.world2.*;
import game.localization.Localization;
import game.models.Coordinates;
import game.powerups.PowerUp;
import game.powerups.portal.EndLevelPortal;
import game.ui.elements.ToastHandler;
import game.utils.Paths;
import game.utils.Utility;

import javax.swing.*;
import javax.xml.crypto.Data;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static game.localization.Localization.STARTING_LEVEL;
import static game.localization.Localization.WELCOME_TEXT;
import static game.ui.panels.game.PitchPanel.GRID_SIZE;


/**
 * The abstract Level class represents the general structure and properties of a game level.
 * It includes methods that allow concrete implementations to define the type of blocks and
 * terrain that the level is composed of, as well as the length of the explosion that occurs
 * when bombs are detonated, and the background image of the level.
 */
public abstract class Level {
    private static Level currLevel;

    public static final Map<Integer, Class<? extends Level>> ID_TO_FIRST_LEVEL_MAP = new HashMap<>() {{
        put(1, World1Level1.class);
        put(2, World2Level1.class);
    }};

    public static final Map<Integer[], Class<? extends Level>> ID_TO_LEVEL = new HashMap<>() {{
        put(new Integer[]{1, 1}, World1Level1.class);
        put(new Integer[]{1, 2}, World1Level2.class);
        put(new Integer[]{1, 3}, World1Level3.class);
        put(new Integer[]{1, 4}, World1Level4.class);
        put(new Integer[]{1, 5}, World1Level5.class);
        put(new Integer[]{2, 1}, World2Level1.class);
        put(new Integer[]{2, 2}, World2Level2.class);
        put(new Integer[]{2, 3}, World2Level3.class);
        put(new Integer[]{2, 4}, World2Level4.class);
        put(new Integer[]{2, 5}, World2Level5.class);
    }};

    protected int maxBombs = 1;

    public abstract Boss getBoss();
    public abstract int startEnemiesCount();
    public abstract int getMaxDestroyableBlocks();
    public abstract int getExplosionLength();
    public abstract Class<? extends Level> getNextLevel();
    public abstract Class<? extends Enemy>[] availableEnemies();

    /**
     *
     Returns the path to the image file for the stone block.
     @return a string representing the path to the image file.
     */
    public String getStoneBlock() {
        return Paths.getCurrentWorldCommonFolder() + "/stone.png";
    }

    /**

     Returns the path to the image file for the grass block.
     @return a string representing the path to the image file.
     */
    public String getGrassBlock() {
        return Paths.getCurrentWorldCommonFolder() + "/grass.png";
    }
    /**

     Returns the path to the image file for the destroyable block.
     @return the path to the image file for the destroyable block.
     */
    public String getDestroyableBlock() {
        return Paths.getCurrentWorldCommonFolder() + "/destroyable_block.png";
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
            pitch[i] = Utility.loadImage(String.format("%s/border_%d.png", Paths.getCurrentWorldCommonFolder(), i));
        }
        return pitch;
    }

    private void updateLastLevel() {
        if(!(this instanceof WorldSelectorLevel))
            currLevel = this;
    }

    public static Level getCurrLevel() {
        return currLevel;
    }

    /**
     * Starts the game level by generating the terrain and adding the player character to the game panel.
     *
     * @param jPanel the panel on which to start the game level
     */
    public void start(JPanel jPanel) {
        updateLastLevel();
        Bomberman.getMatch().setGameState(true);
        DataInputOutput.resetLivesIfNecessary();
        generateEntities(jPanel);
    }

    public void generateEntities(JPanel jPanel) {
        generateStone(jPanel);
        generatePlayer();
        generateDestroyableBlock();
        spawnBoss();
        spawnEnemies();
    }

    public void generatePlayer(){
        Bomberman.getMatch().setPlayer(new Player(Coordinates.fromRowAndColumnsToCoordinates(new Dimension(5, 2),0,0)));
        Bomberman.getMatch().getPlayer().spawn();
        //TODO mousecontroller manager refresh to be refactored
        //   |   when player is respawned mousecontrollermanager keeps last player's instance commands
        //   V
    }

    public void endLevel() {
        try {
            DataInputOutput.setLastLevel(getNextLevel().getConstructor().newInstance());
            DataInputOutput.increaseRounds();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        DataInputOutput.updateStoredPlayerData();
        Bomberman.getMatch().onGameEvent(GameEvent.ROUND_PASSED, null);
    }

    protected void spawnBoss(){
        Boss boss = getBoss();
        if(boss != null) boss.spawn(true, false);
    }

    // This method spawns enemies in the game.
    protected void spawnEnemies() {
        // Get an array of available enemy classes.
        Class<? extends Enemy>[] availableEnemies = availableEnemies();

        // Spawn a number of enemies at the start of the game.
        for (int i = 0; i < startEnemiesCount(); i++) {
            // Select a random enemy class from the availableEnemies array.
            Class<? extends Enemy> enemyClass = availableEnemies[new Random().nextInt(availableEnemies.length)];

            // Create an instance of the enemy class using a constructor that takes a Coordinates object as an argument.
            Enemy enemy;
            try {
                enemy = enemyClass.getConstructor().newInstance();

                // Spawn the enemy on the game board.
                enemy.spawn(false, false);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    // This method returns the ID of the world.
    public abstract int getWorldId();

    // This method returns the ID of the level.
    public abstract int getLevelId();

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
        while (i < getMaxDestroyableBlocks()) {
            // If the current destroyable block has not been spawned, generate new coordinates for it and spawn it on the game board.
            if (!block.isSpawned()){
                block.setCoords(Coordinates.generateCoordinatesAwayFrom(Bomberman.getMatch().getPlayer().getCoords(), GRID_SIZE*2));
                block.spawn();

                // Force the first spawned block to have the End level portal
                if (i == 0 && !isLastLevelOfWorld()) {
                    block.setPowerUpClass(EndLevelPortal.class);
                } else {
                    block.setPowerUpClass(PowerUp.getRandomPowerUpClass());
                }
            }

            // If the current destroyable block has been spawned, create a new one and increment the spawn counter.
            else {
                block = new DestroyableBlock(new Coordinates(0,0));
                i++;
            }
        }
    }

    public boolean isLastLevelOfWorld(){
        return false;
    }

    @Override
    public String toString() {
        return String.format("Level %d, World %d", getLevelId(), getWorldId());
    }
}
