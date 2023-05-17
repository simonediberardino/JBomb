package game.powerups;

import game.entity.bomb.Bomb;
import game.entity.models.BomberEntity;
import game.models.Coordinates;
import game.utils.Paths;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class RemoteControl extends PowerUp{
    /**
     * Constructs a PowerUp entity with the specified coordinates.
     *
     * @param coordinates the coordinates of the PowerUp entity
     */
    public RemoteControl(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public BufferedImage getImage() {
        return loadAndSetImage(Paths.getPowerUpsFolder() + "/remote_up.png");
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    protected void doApply(BomberEntity entity) {
        entity.addClassInteractWithMouse(Bomb.class);
    }

    @Override
    protected void cancel(BomberEntity entity) {
    }
}
