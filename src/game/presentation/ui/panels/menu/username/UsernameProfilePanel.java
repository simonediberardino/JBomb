package game.presentation.ui.panels.menu.username;

import game.domain.events.models.RunnablePar;
import game.localization.Localization;
import game.presentation.ui.panels.models.JBombermanBoxContainerPanel;
import game.presentation.ui.viewelements.bombermanpanel.BombermanPanelYellow;
import game.presentation.ui.viewelements.settings.JBombInputField;
import game.utils.Utility;
import game.utils.dev.Log;
import game.values.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;

import static game.localization.Localization.USERNAME;

public class UsernameProfilePanel extends JBombermanBoxContainerPanel {
    private final RunnablePar getUsernameRunnable;
    private final Consumer<String> callback;
    private JBombInputField inputTextField = null;

    public UsernameProfilePanel(RunnablePar getUsernameRunnable, Consumer<String> callback) {
        super(Localization.get(USERNAME), false, new BombermanPanelYellow());
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
        String currentUsername = (String) getUsernameRunnable.execute(null);
        boolean invalidUsername = currentUsername.isEmpty();

        Runnable onClickCallback = invalidUsername ? () -> {
            inputTextField.setText("");
        } : () -> {};

        inputTextField = new JBombInputField("", callback, onClickCallback);
        updateText();
        inputTextField.setPreferredSize(
                new Dimension(calculateContainerWidth() - Dimensions.DEFAULT_X_PADDING,
                        (int) inputTextField.getPreferredSize().getHeight()
                )
        );

        addComponent(inputTextField);
    }

    @Override
    public void repaint() {
        super.repaint();
        updateText();
    }

    private void updateText() {
        if (inputTextField == null) {
           return;
        }

        String currentUsername = (String) getUsernameRunnable.execute(null);
        boolean invalidUsername = currentUsername == null || currentUsername.isEmpty();
        String finalText = invalidUsername ? Localization.get(Localization.INSERT) : currentUsername;

        inputTextField.setText(finalText);
    }
}
