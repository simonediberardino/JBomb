package game.entity;

import game.BomberMan;
import game.models.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static game.ui.UIHandler.BLOCK_SIZE;


public abstract class Character extends Entity{
    private boolean isAlive = true;

    public Character(Coordinates coordinates) {
        super(coordinates);
    }

    public void setAliveState(boolean x){
        isAlive = x;
    }

    public boolean getAliveState(){
        return isAlive;
    }

    private Entity getCollidedEntity(Direction d) {
        ArrayList<Coordinates> newBlocksAfterMove = new ArrayList<>();

        switch(d){
            case RIGHT:
                for (int i = 0; i < BLOCK_SIZE; i++)
                    newBlocksAfterMove.add(new Coordinates(getCoords().getX()+BLOCK_SIZE,getCoords().getY()+i));
                break;
            case LEFT:
                for (int i = 0; i < BLOCK_SIZE; i++)
                    newBlocksAfterMove.add(new Coordinates(getCoords().getX() - 1, getCoords().getY() + i));
                break;
            case UP:
                for (int i = 0; i < BLOCK_SIZE; i++)
                    newBlocksAfterMove.add(new Coordinates(getCoords().getX()+i, getCoords().getY() - 1));
                break;
            case DOWN:
                for (int i = 0; i < BLOCK_SIZE; i++)
                    newBlocksAfterMove.add(new Coordinates(getCoords().getX() + i, getCoords().getY() + BLOCK_SIZE));
                break;
        }


        Entity collided = null;

        for(Entity entity : BomberMan.getInstance().getEntities()){
            if(entity.equals(this)) continue;

            boolean isAtleastAPixelCollided = false;

            for (Coordinates element : newBlocksAfterMove) {
                if (entity.getPositions().contains(element)) {
                    isAtleastAPixelCollided = true;
                    break;
                }
            }

            if(isAtleastAPixelCollided) collided = entity;
        }

        return collided;
    }

    public void move(Direction d) {
        int x = 0, y = 0;
        switch(d) {
            case LEFT: x = -1; y = 0; break;
            case RIGHT: x = 1; y = 0; break;
            case UP: x = 0; y = -1; break;
            case DOWN: x = 0; y = 1; break;
        }

        Entity entity = getCollidedEntity(d);

        if(entity == null){
            setPosition(x + getCoords().getX(), y + getCoords().getY());
        }else if(entity instanceof Block){
            /// interact(borders.get(1).get(i)
            ///blocco presente
            return;
        }
        else if (entity instanceof Character){
            //interact(borders.get(1).get(i)
        }
    }
}
