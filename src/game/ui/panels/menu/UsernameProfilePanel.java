package game.ui.panels.menu;

import game.data.DataInputOutput;
import game.events.RunnablePar;
import game.localization.Localization;
import game.ui.panels.models.JBombermanBoxContainerPanel;
import game.ui.viewelements.settings.JBombInputField;
import game.utils.Utility;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static game.localization.Localization.USERNAME;

public class UsernameProfilePanel extends JBombermanBoxContainerPanel {
    private final RunnablePar getUsernameRunnable;
    private final Consumer<String> callback;

    public UsernameProfilePanel(RunnablePar getUsernameRunnable, Consumer<String> callback) {
        super(Localization.get(USERNAME), false);
        this.getUsernameRunnable = getUsernameRunnable;
        this.callback = callback;
        this.initializeLayout();
    }

    @Override
    protected int getBoxPanelWidth() {
        return Utility.px(Dimensions.DEFAULT_MAIN_MENU_BOX_SIZE);
    }

    @Override
    protected void addCustomElements() {
        JTextField j = new JBombInputField((String) getUsernameRunnable.execute(null), callback);
        j.setPreferredSize(new Dimension(getBoxPanelWidth() - Dimensions.DEFAULT_X_PADDING, (int) j.getPreferredSize().getHeight()));
        componentsPanel.add(j);
    }
}
