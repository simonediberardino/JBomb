package game.entity;

import game.BomberMan;
import game.controller.Command;
import game.models.Direction;
import game.ui.GridImage;
import game.ui.UIHandler;

import javax.swing.*;

import java.util.Observable;
import java.util.Observer;

import static game.models.Direction.*;
import static game.ui.UIHandler.BLOCK_SIZE;

public class Player extends Character implements Observer {
    public Player(Coordinates coordinates) {
        super(coordinates);
    }

    public Player() {
        super(new Coordinates(0,0));
    }

    @Override
    public Icon[] getIcon() {
        return new GridImage("assets/main_character.png", BLOCK_SIZE).generate();
    }

    @Override
    public void setAliveState(boolean x) {
        super.setAliveState(x);

        if(!x){
            BomberMan.getInstance().getKeyEventObservable().deleteObserver(this);
        }else{
            BomberMan.getInstance().getKeyEventObservable().addObserver(this);
        }
    }

    @Override
    public void spawn() {
        super.spawn();
        setAliveState(true);
    }

    private void placeBomb(){}

    private void handleAction(Command command){
        switch(command){
            case MOVE_UP: move(UP); break;
            case MOVE_DOWN: move(DOWN); break;
            case MOVE_LEFT: move(LEFT); break;
            case MOVE_RIGHT: move(RIGHT); break;
            case PLACE_BOMB: placeBomb(); break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Command: " + arg);
        handleAction((Command) arg);
    }
}
