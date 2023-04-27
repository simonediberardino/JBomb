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
    private Set<Entity> grass;
    private Set<InteractiveEntities> interactiveEntities;
    private Set<Block> blocks;
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
        this.interactiveEntities = new HashSet<>();
        this.blocks = new HashSet<>();
        this.grass = new HashSet<>();
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

    public Set<? extends InteractiveEntities> getEntities() {
        return interactiveEntities;
    }

    public Set<? extends Block> getBlocks() {
        return blocks;
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

    public KeyEventObservable getKeyEventObservable() {
        return keyEventObservable;
    }

    public GameTickerObservable getGameTickerObservable() {
        return gameTickerObservable;
    }
}
