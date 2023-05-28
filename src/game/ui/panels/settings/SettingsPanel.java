package game.ui.panels.settings;

import game.data.DataInputOutput;
import game.localization.Localization;
import game.runnables.RunnablePar;
import game.ui.panels.BombermanFrame;
import game.ui.viewelements.settings.SettingsElementView;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import static game.localization.Localization.*;

public class SettingsPanel extends BoxMenuPanel{
    /**
     * Constructs a ProfilePanel object.
     *
     * @param cardLayout the CardLayout of the parent container
     * @param parent     the parent container of this panel
     * @param frame      the BombermanFrame object
     */
    public SettingsPanel(CardLayout cardLayout, JPanel parent, BombermanFrame frame) {
        super(cardLayout, parent, frame, Localization.get(SETTINGS));
    }

    private SettingsElementView createTextFieldElementView(String label, String keyChar, RunnablePar runnablePar) {
        return addTextFieldElementView(label, keyChar, runnablePar, 1);
    }

    private RunnablePar createKeyRunnable(final Consumer<Integer> keySetter) {
        return new RunnablePar() {
            @Override
            public <T> Object execute(T par) {
                char c = par.toString().isBlank() ? ' ' : par.toString().charAt(0);
                keySetter.accept((int) c);
                return null;
            }
        };
    }

    @Override
    protected void addCustomElements() {
        SettingsElementView forwardKey = createTextFieldElementView(
                Localization.get(KEY_FORWARD),
                DataInputOutput.getForwardKeyChar(),
                createKeyRunnable(DataInputOutput::setForwardKey)
        );
        SettingsElementView leftKey = createTextFieldElementView(Localization.get(KEY_LEFT), DataInputOutput.getLeftKeyChar(), createKeyRunnable(DataInputOutput::setLeftKey));
        SettingsElementView backKey = createTextFieldElementView(Localization.get(KEY_BACK), DataInputOutput.getBackKeyChar(), createKeyRunnable(DataInputOutput::setBackKey));
        SettingsElementView rightKey = createTextFieldElementView(Localization.get(KEY_RIGHT), DataInputOutput.getRightKeyChar(), createKeyRunnable(DataInputOutput::setRightKey));
        SettingsElementView bombKey = createTextFieldElementView(Localization.get(KEY_BOMB), DataInputOutput.getBombKeyChar(), createKeyRunnable(DataInputOutput::setBombKey));

    }
}
