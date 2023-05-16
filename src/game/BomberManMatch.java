package game;

import game.controller.ControllerManager;
import game.data.DataInputOutput;
import game.engine.GameTickerObservable;
import game.entity.*;
import game.entity.models.*;
import game.events.GameEvent;
import game.level.Level;
import game.powerups.PowerUp;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class BomberManMatch implements OnGameEvent {
    private GameTickerObservable gameTickerObservable;
    private long lastGamePauseStateTime = System.currentTimeMillis();
    private Set<Entity> entities;
    private ControllerManager controllerManager;
    private Level currentLevel;
    private Player player;
    private boolean gameState = false;

    private BomberManMatch(){
        this(null);
    }

    public BomberManMatch(Level currentLevel) {
        this.currentLevel = currentLevel;
        this.entities = new TreeSet<>();

        this.controllerManager = new ControllerManager();
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

    public Set<? extends Entity> getEntities() {
        return new TreeSet<>(entities);

        // Creates a new list to avoid concurrent modification exception;s
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity e) {
        Set<Entity> tempList = new TreeSet<>(entities);
        tempList.removeIf(e1 -> e.getId() == e1.getId());
        entities = tempList;
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
        gameTickerObservable.resume();
        gameState = true;
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
            case DEATH: DataInputOutput.increaseDeaths(); break;
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

        this.gameTickerObservable.unregisterAll();
        this.gameTickerObservable = null;
        this.controllerManager.unregisterAll();
        this.controllerManager = null;

        System.gc();
    }
}
