package game.ui.panels.menu;

import game.localization.Localization;
import game.utils.Utility;

import static game.localization.Localization.AVATAR;

public class AvatarMenuPanel extends JBombermanBoxContainerPanel {
    public AvatarMenuPanel() {
        super(Localization.get(AVATAR), false);
        initializeLayout();
    }

    @Override
    int getBoxPanelWidth() {
        return Utility.px(500);
    }

    @Override
    protected void addCustomElements() {

    }
}
