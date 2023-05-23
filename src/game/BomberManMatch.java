package game;

import game.controller.ControllerManager;
import game.controller.MouseControllerManager;
import game.data.DataInputOutput;
import game.engine.GameTickerObservable;
import game.entity.*;
import game.entity.models.*;
import game.events.GameEvent;
import game.level.Level;
import game.utils.Utility;

import java.util.*;

public class BomberManMatch implements OnGameEvent {
    private GameTickerObservable gameTickerObservable;
    private long lastGamePauseStateTime = System.currentTimeMillis();
    private final Set<Entity> entities;
    public ControllerManager controllerManager;
    public MouseControllerManager mouseControllerManager;
    private Level currentLevel;
    private Player player;
    private boolean gameState = false;
    private int enemiesAlive = 0;

    private BomberManMatch(){
        this(null);
    }

    public BomberManMatch(Level currentLevel) {
        this.currentLevel = currentLevel;
        this.entities = new TreeSet<>();

        this.controllerManager = new ControllerManager();
        this.mouseControllerManager = new MouseControllerManager();
        this.gameTickerObservable = new GameTickerObservable();
        this.controllerManager.register(new GamePausedObserver());
        ControllerManager.setDefaultCommandDelay();
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

    public Set<?extends Entity> getEntities(){
        synchronized (entities) {
            if(entities.isEmpty()) return new TreeSet<>();
            return new TreeSet<>(entities);
        }
    }

    public void addEntity(Entity entity) {
        synchronized (entities){
            entities.add(entity);
        }
    }

    public void removeEntity(Entity e) {
        synchronized (entities){
            entities.removeIf(e1 -> e.getId() == e1.getId());
        }
    }

    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    public MouseControllerManager getMouseControllerManager(){
        return mouseControllerManager;
    }

    public GameTickerObservable getGameTickerObservable() {
        return gameTickerObservable;
    }

    public void toggleGameState(){
        if(Utility.timePassed(lastGamePauseStateTime) < 500) return;

        lastGamePauseStateTime = System.currentTimeMillis();

        if(gameState) pauseGame(); else resumeGame();
    }

    private void pauseGame() {
        gameTickerObservable.stop();
        gameState = false;
    }

    private void resumeGame(){
        gameTickerObservable.resume();
        gameState = true;
    }

    public int getEnemiesAlive() {
        return enemiesAlive;
    }

    public void decreaseEnemiesAlive(){
        enemiesAlive--;
    }

    public void increaseEnemiesAlive(){
        enemiesAlive++;
    }

    public boolean getGameState() {
        return gameState;
    }

    public void setGameState(boolean gameState) {
        this.gameState = gameState;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent, Object arg) {
        switch(gameEvent) {
            case DEATH: DataInputOutput.increaseDeaths(); DataInputOutput.decreaseLives(); break;
            case KILLED_ENEMY: DataInputOutput.increaseKills(); break;
            case SCORE: DataInputOutput.increaseScore((Integer) arg); break;
            case DEFEAT: DataInputOutput.increaseLost(); break;
            case ROUND_PASSED: DataInputOutput.increaseRounds(); break;
        }
    }

    public void destroy() {
        pauseGame();

        Set<? extends Entity> list = getEntities();
        for (Entity e: list) {
            e.despawn();
        }

        this.player = null;
        this.currentLevel = null;
        this.entities.clear();
        this.enemiesAlive = 0;
        this.gameTickerObservable.unregisterAll();
        this.gameTickerObservable = null;
        this.controllerManager.unregisterAll();
        this.controllerManager = null;

        System.gc();
    }
}
