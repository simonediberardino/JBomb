package game.entity.models;


import game.BomberMan;
import game.entity.Player;
import game.entity.bomb.Explosion;
import game.models.Coordinates;
import game.models.Direction;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Enemy extends Character implements ICPU, Observer {
    protected final int changeDirectionChangeRate = 15; // percentage
    protected int directionRefreshRate = 500;
    protected boolean canMove = true;

    public Enemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void interact(Entity e) {
        if (canInteractWith(e)) {
            if(e instanceof Explosion){
                despawn();
            }
            if (isObstacle(e)) {
                changeDirection();
            } else if (e instanceof Player) {
                e.despawn();
            }
        }
    }

    public boolean isObstacle(Entity e){
        return e instanceof Block || e == null || e instanceof Enemy;
    }

    @Override
    public boolean canInteractWith(Entity e){
        return e instanceof Player || e instanceof Block|| e == null || e instanceof Explosion||e instanceof Enemy;
    }

    @Override
    protected void onSpawn() {
        BomberMan.getInstance().getGameTickerObservable().addObserver(this);
    }

    @Override
    protected void onDespawn(){
        BomberMan.getInstance().getGameTickerObservable().deleteObserver(this);
    }

    @Override
    public float getImageRatio(){
        return 0.73f;
    }

    /**
     * Chooses a new direction for the agent to move in, and sends the corresponding command to the game engine.
     *
     * @param forceChange If true, the agent will be forced to change direction even if it just changed directions.
     *                    If false, there is a chance the agent will keep its current direction.
     */
    @Override
    public void chooseDirection(boolean forceChange) {
        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // If it hasn't been long enough since the last direction update, keep moving in the same direction, unless last move was blocked
        if(currentTime - lastDirectionUpdate < directionRefreshRate && !forceChange) {
            handleAction(currDirection.toCommand());
            return;
        }

        // Get a list of all the available directions the agent can move in
        List<Direction> availableDirections = getAvailableDirections();

        // If forceChange is true, remove the current direction from the list of available directions
        if(forceChange) {
            availableDirections.remove(currDirection);
        }

        // If there are no available directions, choose a new one randomly
        if(availableDirections.isEmpty()) {
            if(forceChange){
                // If forceChange is true, consider all possible directions except the current one
                availableDirections.addAll(Arrays.asList(Direction.values())
                        .parallelStream()
                        .filter(e -> e != currDirection)
                        .collect(Collectors.toList()));
            }else{
                // If forceChange is false, keep moving in the same direction
                availableDirections.add(currDirection);
            }
        }

        // Choose a new direction randomly, or keep the current direction with a certain probability
        Direction newDirection = null;
        if(!forceChange && Math.random() * 100 > changeDirectionChangeRate) {
            newDirection = currDirection;
        }

        // If a new direction hasn't been chosen, choose one randomly from the available options
        if(newDirection == null) {
            newDirection = availableDirections.get((int)(Math.random() * availableDirections.size()));
        }

        // Send the command corresponding to the new direction to the game engine
        move(newDirection);
    }

    @Override
    public void changeDirection() {
        chooseDirection(true);
    }



    @Override
    public void update(Observable o, Object arg) {
        if(canMove)
        chooseDirection(false);
    }
    public void stopMove(){
        canMove = false;
    }
    public void freeMove(){
        canMove = true;
    }
}
