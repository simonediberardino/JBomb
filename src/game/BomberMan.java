package game;

import game.controller.ControllerManager;
import game.engine.GameTickerObservable;
import game.entity.*;
import game.entity.models.Block;
import game.entity.models.Entity;
import game.entity.models.EntityInteractable;
import game.level.Level;
import game.powerups.PowerUp;
import game.ui.GameFrame;

import java.util.*;

public class BomberMan {
    private static BomberMan instance;
    private GameTickerObservable gameTickerObservable;
    private Set<EntityInteractable> interactiveEntities;
    private long lastGamePauseStateTime = System.currentTimeMillis();
    private Set<Entity> staticEntities;
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
        this.staticEntities = new HashSet<>();
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

    public Set<? extends EntityInteractable> getEntities() {
        return new HashSet<>(interactiveEntities);
    }

    public Set<? extends Entity> getStaticEntities() {
        return new HashSet<>(staticEntities);
    }

    public void removeInteractiveEntity(Entity e){
        interactiveEntities.remove(e);
    }

    public void removeStaticEntities(Entity e){
        staticEntities.remove(e);
    }

    public void addEntity(EntityInteractable entity) {
        interactiveEntities.add(entity);
    }

    public void addStaticEntity(Entity entity) {
        staticEntities.add(entity);
    }

    public void removeEntity(Entity e){
        if(e instanceof Block || e instanceof PowerUp){
            removeStaticEntities(e);
        }else if (e instanceof EntityInteractable){
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
