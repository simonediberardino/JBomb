package game.presentation.ui.viewcontrollers;

import game.utils.file_system.Paths;

public class InventoryElementControllerLives extends InventoryElementController {
    public InventoryElementControllerLives() {
        super(0, Paths.getPowerUpsFolder() + "/lives_up.png");
    }
}
