package game.entity;

import game.BomberMan;
import game.controller.Command;
import game.models.Coordinates;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static game.models.Direction.*;



public class Player extends Character implements Observer {

    @Override
    public String[] getFrontIcons() {
        return new String[]{
                "assets/player/player_front_0.png",
                "assets/player/player_front_1.png",
                "assets/player/player_front_0.png",
                "assets/player/player_front_2.png"
        };
    }

    @Override
    public String[] getLeftIcons() {
        //TODO
        return new String[]{
                "assets/player/player_right_0.png"
        };
    }

    @Override
    public String[] getBackIcons() {
        //TODO
        return new String[]{
                "assets/player/player_front_1.png",
                "assets/player/player_front_0.png",
                "assets/player/player_front_1.png",
                "assets/player/player_front_2.png"
        };
    }

    @Override
    public String[] getRightIcons() {
        //TODO
        return new String[]{
                "assets/player/player_right_0.png",
                "assets/player/player_right_1.png",
                "assets/player/player_right_2.png",
                "assets/player/player_right_1.png"

        };
    }

    public Player(Coordinates coordinates) {
        super(coordinates);
    }

    public Player() {
        super(new Coordinates(0,0));
    }


    @Override
    public void setAliveState(boolean x) {
        super.setAliveState(x);

        if(!x){
            BomberMan.getInstance().getKeyEventObservable().deleteObserver(this);
        }else{
            BomberMan.getInstance().getKeyEventObservable().deleteObservers();
            BomberMan.getInstance().getKeyEventObservable().addObserver(this);
        }
    }

    @Override
    public void spawn() {
        super.spawn();
        setAliveState(true);
    }

    public void interact(Entity e){
        if (e instanceof Enemy || e instanceof Explosion){
            despawn();
        }
    }

    public void placeBomb(){
        List<Coordinates> coords = getNewCoordinatesOnDirection(
                getLastDirection(),
                getSize(),
                getSize(),
                getSize(),
                true
        );

        Bomb bomb = new Bomb(coords.get(0));
        if (getEntitiesOnBorder(getLastDirection(), bomb.getSize(), STEP_SIZE).isEmpty()){
            bomb.spawn();
        }
    }

    private void handleAction(Command command){
        System.out.println(command);
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
        handleAction((Command) arg);
    }

}
