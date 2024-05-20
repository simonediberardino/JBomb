package game.presentation.ui.panels.menu;

import game.domain.events.models.RunnablePar;
import game.localization.Localization;
import game.presentation.ui.panels.models.JBombermanBoxContainerPanel;
import game.presentation.ui.viewelements.settings.JBombInputField;
import game.utils.Utility;
import game.utils.dev.Log;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static game.localization.Localization.USERNAME;

public class UsernameProfilePanel extends JBombermanBoxContainerPanel {
    private final RunnablePar getUsernameRunnable;
    private final Consumer<String> callback;
    private JBombInputField inputTextField = null;

    public UsernameProfilePanel(RunnablePar getUsernameRunnable, Consumer<String> callback) {
        super(Localization.get(USERNAME), false);
        this.getUsernameRunnable = getUsernameRunnable;
        this.callback = callback;
        this.initializeLayout();
    }

    @Override
    protected int getDefaultBoxPanelWidth() {
        return Utility.INSTANCE.px(Dimensions.DEFAULT_MAIN_MENU_BOX_SIZE);
    }

    @Override
    protected void addCustomElements() {
        inputTextField = new JBombInputField((String) getUsernameRunnable.execute(null), callback);
        inputTextField.setPreferredSize(new Dimension(calculateContainerWidth() - Dimensions.DEFAULT_X_PADDING, (int) inputTextField.getPreferredSize().getHeight()));
        addComponent(inputTextField);
    }

    @Override
    public void repaint() {
        super.repaint();
        if (inputTextField != null) {
            inputTextField.setText((String) getUsernameRunnable.execute(null));
        }
    }
}
