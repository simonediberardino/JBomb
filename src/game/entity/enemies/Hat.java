package game.entity.enemies;

import game.entity.models.Entity;
import game.models.Coordinates;
import game.models.Direction;
import game.models.EnhancedDirection;
import game.utils.Paths;

import java.awt.image.BufferedImage;

public class Hat extends Orb{
    long time = 0;
    private String basePath = "assets/entities/enemies/clown/hat";

    int lastIndex = 1;
    int maxIndex = 10;

    @Override
    protected String getBasePath() {
        return Paths.getAssetsFolder() + "/clown/hat";
    }

    @Override
    public String[] getFrontIcons() {
        return new String[]{
                getBasePath() + "1.png",
                getBasePath() + "2.png",
                getBasePath() + "3.png",
                getBasePath() + "4.png",
                getBasePath() + "5.png",
                getBasePath() + "6.png",
                getBasePath() + "7.png",
                getBasePath() + "8.png",
                getBasePath() + "9.png",
                getBasePath() + "10.png",
        };
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
        if (enhancedDirection == null) {
            if (canMove && gameState && isSpawned()){
                if(!moveOrInteract(direction))
                    direction = direction.opposite();
            }
            return;
        }

        if (canMove && gameState && isSpawned()) {
            for (Direction d : enhancedDirection.toDirection()) {
                currDirection = d;
                if (!moveOrInteract(d)) {
                    enhancedDirection = enhancedDirection.opposite(d);
                }
            }
        }
    }
}
