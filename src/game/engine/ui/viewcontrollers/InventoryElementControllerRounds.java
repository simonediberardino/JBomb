package game.engine.ui.viewcontrollers;

import game.utils.file_system.Paths;

public class InventoryElementControllerRounds extends InventoryElementController {
    public InventoryElementControllerRounds() {
        super(0, Paths.getInventoryPath() + "/round_icon.png");
    }
}
