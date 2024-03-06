package game.engine.ui.viewcontrollers;

import game.utils.file_system.Paths;

public class InventoryElementControllerBombs extends InventoryElementController {
    public InventoryElementControllerBombs() {
        super(0, Paths.getEntitiesFolder() + "/bomb/bomb_0.png");
    }
}
