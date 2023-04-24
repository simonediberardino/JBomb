package game.entity;


import game.models.Coordinates;

public class Enemy extends Character {
    @Override
    public String[] getFrontIcons() {
        return new String[]{
                "assets/enemy.png",
        };
    }

    @Override
    public String[] getLeftIcons() {
        return new String[0];
    }

    @Override
    public String[] getBackIcons() {
        return new String[0];
    }

    @Override
    public String[] getRightIcons() {
        return new String[0];
    }

    public Enemy(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public void interact(Entity e) {
        if (e instanceof Player){
            e.despawn();
        }
    }

}
