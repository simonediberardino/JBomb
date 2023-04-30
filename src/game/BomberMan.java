package game;

import game.controller.ControllerManager;
import game.engine.GameTickerObservable;
import game.entity.*;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.entity.models.InteractiveEntities;
import game.level.Level;
import game.ui.GameFrame;
import game.ui.GamePanel;

import javax.swing.Timer;
import java.util.*;

public class BomberMan {
    private static BomberMan instance;
    private GameTickerObservable gameTickerObservable;
    private Set<InteractiveEntities> interactiveEntities;
    private long lastGamePauseStateTime = System.currentTimeMillis();
    private Set<Block> blocks;
    private ControllerManager controllerManager;
    private Level currentLevel;
    private Player player;
    public GameFrame gameFrame = null;
    public boolean gameState = true;

    public static BomberMan getInstance() {
        return instance;
    }

    private BomberMan(){}

    public BomberMan(Level currentLevel) {
        BomberMan.instance = this;

        this.currentLevel = currentLevel;
        this.interactiveEntities = new HashSet<>();
        this.blocks = new HashSet<>();
        this.controllerManager = new ControllerManager();
        this.gameTickerObservable = new GameTickerObservable();
        controllerManager.addObserver(new GamePausedObserver());

        this.start();

    }

    public void start() {
        gameFrame = new GameFrame();
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public Set<? extends InteractiveEntities> getEntities() {
        return new HashSet<>(interactiveEntities);
    }

    public Set<? extends Block> getBlocks() {
        return new HashSet<>(blocks);
    }

    public void removeInteractiveEntity(Entity e){
        interactiveEntities.remove(e);
    }

    public void removeBlock(Entity e){
        blocks.remove(e);
    }

    public void addEntity(InteractiveEntities entity) {
        interactiveEntities.add(entity);
    }

    public void addBlock(Block entity) {
        blocks.add(entity);
    }

    public void removeEntity(Entity e){
        if(e instanceof Block){
            removeBlock(e);
        }else if (e instanceof InteractiveEntities){
            removeInteractiveEntity(e);
        }
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public GameTickerObservable getGameTickerObservable() {
        return gameTickerObservable;
    }


    public void toggleGameState(){
        if(System.currentTimeMillis() - lastGamePauseStateTime < 500) return;
        lastGamePauseStateTime = System.currentTimeMillis();
        if(gameState){
            pauseGame();
        }else{
            resumeGame();
        }
    }

    private void pauseGame() {
        gameTickerObservable.stop();
        gameState = false;
    }

    private void resumeGame(){
        gameTickerObservable.start();
        gameState = true;
    }
}
