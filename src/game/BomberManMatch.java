package game;

import game.controller.ControllerManager;
import game.data.DataInputOutput;
import game.engine.GameTickerObservable;
import game.entity.*;
import game.entity.models.*;
import game.events.GameEvent;
import game.level.Level;
import game.powerups.PowerUp;

import java.util.*;
import java.util.stream.Collectors;

public class BomberManMatch implements OnGameEvent {
    private static BomberManMatch instance;
    private GameTickerObservable gameTickerObservable;
    private long lastGamePauseStateTime = System.currentTimeMillis();
    private Set<Entity> entities;
    private ControllerManager controllerManager;
    private Level currentLevel;
    private Player player;
    private boolean gameState = false;

    public static BomberManMatch getInstance() {
        return instance;
    }

    private BomberManMatch(){}

    public BomberManMatch(Level currentLevel) {
        BomberManMatch.instance = this;

        this.currentLevel = currentLevel;
        this.entities = new TreeSet<>();

        this.controllerManager = new ControllerManager();
        this.gameTickerObservable = new GameTickerObservable();
        controllerManager.addObserver(new GamePausedObserver());
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

    public List<? extends Entity> getEntities() {
        // Creates a new list to avoid concurrent modification exception;
        return new ArrayList<>(entities);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity e){
        getGameTickerObservable().deleteObserver(e);

        entities.removeIf(e1 -> e.getId() == e1.getId());
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
            case VICTORY: DataInputOutput.increaseVictories(); break;
            case ROUND_PASSED: DataInputOutput.increaseRounds(); break;
        }
    }
}
