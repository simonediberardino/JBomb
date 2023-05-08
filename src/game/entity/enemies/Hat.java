package game.entity.enemies;

import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;
import game.models.EnhancedDirection;

import java.awt.image.BufferedImage;

public class Hat extends Orb{
    long time = 0;
    private String basePath = "assets/entities/enemies/clown/hat";

    int lastIndex = 1;
    int maxIndex = 10;
    @Override
    public BufferedImage getImage() {
        if (System.currentTimeMillis() - time > 200) {
            time = System.currentTimeMillis();
            if (lastIndex >=maxIndex) lastIndex = 1;
            else lastIndex+=1;

        }return loadAndSetImage(basePath + lastIndex + ".png");
    }




    public Hat(Coordinates coordinates, Direction direction) {
        super(coordinates, direction);
    }
    public Hat(Coordinates coordinates, EnhancedDirection enhancedDirection) {
        super(coordinates, enhancedDirection);
    }

    @Override
    public int getSize() {
        return SIZE*2;
    }


    @Override
    protected void doInteract(Entity e) {

        if (canInteractWith(e)&&e!=null) {
            attack(e);
        }




    }

    @Override
    public void update(boolean gameState) {
        if (enhancedDirection != null) {
            if (canMove && gameState && isSpawned()) {
                for (Direction d :
                        enhancedDirection.toDirection()
                ) {
                    currDirection = d;
                    if (!moveOrInteract(d)) {
                        enhancedDirection = enhancedDirection.opposite(d);
                    }

                }


            }

        }else if (canMove&&gameState&&isSpawned()){
            if(!moveOrInteract(direction))
                direction = direction.opposite();
        }
    }
}
