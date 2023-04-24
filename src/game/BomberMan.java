package game;

import game.controller.KeyEventObservable;
import game.engine.GameTickerObservable;
import game.entity.*;
import game.level.Level;
import game.ui.GameFrame;

import java.util.*;

public class BomberMan {
    private static BomberMan instance;
    private GameTickerObservable gameTickerObservable;
    private Set<Entity> entities;
    private KeyEventObservable keyEventObservable;
    private Level currentLevel;
    private Player player;
    private GameFrame gameFrame = null;

    public static BomberMan getInstance() {
        return instance;
    }

    private BomberMan(){}

    public BomberMan(Level currentLevel) {
        BomberMan.instance = this;
        this.currentLevel = currentLevel;
        this.entities = new HashSet<>();
        this.keyEventObservable = new KeyEventObservable();
        this.gameTickerObservable = new GameTickerObservable();

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

    public GameFrame getUiHandler() {
        return gameFrame;
    }

    public Set<Entity> getEntities() {

        return entities;
    }
    public void removeEntity(Entity e){
        entities.remove(e);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public KeyEventObservable getKeyEventObservable() {
        return keyEventObservable;
    }

    public GameTickerObservable getGameTickerObservable() {
        return gameTickerObservable;
    }
}
