package game;

import game.controller.KeyEventObservable;
import game.entity.*;
import game.level.Level;
import game.ui.UIHandler;

import java.util.*;

public class BomberMan {
    private static BomberMan instance;
    private Set<Entity> entities;
    private KeyEventObservable keyEventObservable;
    private Level currentLevel;
    private Player player;
    private UIHandler uiHandler = null;

    public static BomberMan getInstance() {
        return instance;
    }

    private BomberMan(){}

    public BomberMan(Level currentLevel) {
        BomberMan.instance = this;
        this.currentLevel = currentLevel;
        this.entities = new HashSet<>();
        this.keyEventObservable = new KeyEventObservable();
        this.start();
    }

    public void start() {
        uiHandler = new UIHandler();
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

    public UIHandler getUiHandler() {
        return uiHandler;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public KeyEventObservable getKeyEventObservable() {
        return keyEventObservable;
    }
}
