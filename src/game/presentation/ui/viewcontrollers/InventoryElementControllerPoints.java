package game.presentation.ui.viewcontrollers;

import game.utils.file_system.Paths;

public class InventoryElementControllerPoints extends InventoryElementController {
    public InventoryElementControllerPoints() {
        super(0, Paths.getInventoryPath() + "/points.png");
    }
}
