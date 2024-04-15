package game.presentation.ui.viewcontrollers;

import game.utils.file_system.Paths;

public class InventoryElementControllerHp extends InventoryElementController {
    public InventoryElementControllerHp() {
        super(0, Paths.getPowerUpsFolder() + "/lives_up.png");
    }
}
