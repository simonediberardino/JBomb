package game.presentation.ui.viewcontrollers;

import game.utils.file_system.Paths;

public class InventoryElementControllerTime extends InventoryElementController {
    public InventoryElementControllerTime() {
        super(0, Paths.getInventoryPath() + "/time.png");
    }
}
